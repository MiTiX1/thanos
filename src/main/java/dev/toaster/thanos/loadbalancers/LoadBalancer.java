package dev.toaster.thanos.loadbalancers;

import dev.toaster.thanos.requests.Request;
import dev.toaster.thanos.servers.Server;

import java.util.HashMap;
import java.util.Map;

public class LoadBalancer {
    private final Map<String, Server> servers;
    private final LoadBalancingStrategy loadBalancingStrategy;

    public LoadBalancer(Map<String, Server> servers, LoadBalancingStrategy loadBalancingStrategy) {
        this.servers = servers;
        this.loadBalancingStrategy = loadBalancingStrategy;
    }

    public LoadBalancer(LoadBalancingStrategy loadBalancingStrategy) {
        this(new HashMap<>(), loadBalancingStrategy);
    }

    public LoadBalancer(Map<String, Server> servers) {
        this(servers, new RoundRobinStrategy());
    }

    public LoadBalancer() {
        this(new HashMap<>(), new RoundRobinStrategy());
    }

    public void registerServer(Server server) {
        if (this.servers.containsKey(server.toString())) {
            System.out.println("Server is already registered");
            return;
        }
        this.servers.put(server.toString(), server);
    }

    public void registerServer(String id, Server server) {
        if (this.servers.containsKey(id)) {
            System.out.println("Server is already registered");
            return;
        }
        this.servers.put(server.toString(), server);
    }

    private Server selectServer() {
        return this.loadBalancingStrategy.selectServer(this.servers.values().stream().toList());
    }

    public void forwardRequest(Request request) {
        Server server = this.selectServer();

    }
}
