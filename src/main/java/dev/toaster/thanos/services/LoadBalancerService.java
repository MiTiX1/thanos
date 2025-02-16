package dev.toaster.thanos.services;

import dev.toaster.thanos.dtos.DeregisterServerDTO;
import dev.toaster.thanos.dtos.RegisterServerDTO;
import dev.toaster.thanos.loadbalancers.LoadBalancingStrategy;
import dev.toaster.thanos.loadbalancers.RoundRobinStrategy;
import dev.toaster.thanos.servers.Server;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class LoadBalancerService {
    private final ConcurrentHashMap<String, Server> registeredServers = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Server> healthyServers = new ConcurrentHashMap<>();
    private final LoadBalancingStrategy loadBalancingStrategy = new RoundRobinStrategy();

    private Server selectNextServer() {
        return this.healthyServers.isEmpty() ?
                null
                : this.loadBalancingStrategy.selectServer(this.healthyServers.values().stream().toList());
    }

    public ResponseEntity<Object> forwardRequest(HttpServletRequest request) throws IOException, URISyntaxException {
        URI targetUrl = this.buildUrl(request);
        if (targetUrl == null) {
            return ResponseEntity.status(500).body("Could not find target URL");
        }

        HttpMethod method = HttpMethod.valueOf(request.getMethod());
        HttpHeaders headers = this.extractHeaders(request);
        String body = this.extractBody(request);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                targetUrl,
                method,
                entity,
                Object.class
        );
    }

    private URI buildUrl(HttpServletRequest request) {
        Server server = this.selectNextServer();
        if (server == null) {
            return null;
        }
        try {
            String targetPath = new URL(request.getRequestURL().toString()).getPath();
            String queryParams = request.getQueryString();
            return queryParams == null ?
                    server.getUrl().toURI().resolve(targetPath) :
                    server.getUrl().toURI().resolve(targetPath + "?" + queryParams);
        } catch (MalformedURLException | URISyntaxException e) {
            System.out.println(e);
            return null;
        }
    }

    private HttpHeaders extractHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        HttpHeaders headers = new HttpHeaders();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            headers.add(header, request.getHeader(header));
        }
        return headers;
    }

    private String extractBody(HttpServletRequest request) {
        if (HttpMethod.valueOf(request.getMethod()) != HttpMethod.GET) {
            try {
                return request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return null;
    }

    @Scheduled(fixedRate = 10000)
    public void performHealthCheck() {
        for (Server server : this.registeredServers.values()) {
            if (this.isServerAlive(server)) {
                server.updateLastHealthCheck();
                this.healthyServers.put(server.getId(), server);
            } else {
                this.healthyServers.remove(server.getId());
            }
        }
    }

    private boolean isServerAlive(Server server) {
        try {
            URL url = server.getUrl();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.connect();
            return connection.getResponseCode() == 200;
        } catch (IOException e) {
            return false;
        }
    }

    public ResponseEntity<String> registerServer(RegisterServerDTO registerServerDTO) {
        try {
            URL url = registerServerDTO.getURL();
            Server server = new Server(registerServerDTO.id(), url);
            this.registeredServers.put(server.getId(), server);
            return ResponseEntity.ok("Registered server: " + server.getId());
        } catch (MalformedURLException e) {
            return ResponseEntity.status(400).body("Wrong URL schema");
        }
    }

    public ResponseEntity<String> deregisterServer(DeregisterServerDTO deregisterServerDTO) {
        Server server = this.registeredServers.remove(deregisterServerDTO.id());
        if (server == null) {
            return ResponseEntity.status(400).body("Server is not registered: " + deregisterServerDTO.id());
        }
        this.healthyServers.remove(server.getId());
        return ResponseEntity.ok("Server removed: " + server.getId());
    }

    public ConcurrentHashMap<String, Server> getRegisteredServers() {
        return registeredServers;
    }

    public ConcurrentHashMap<String, Server> getHealthyServers() {
        return healthyServers;
    }
}
