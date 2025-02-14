package dev.toaster.thanos.services;

import dev.toaster.thanos.dtos.DeregisterServerDTO;
import dev.toaster.thanos.dtos.RegisterServerDTO;
import dev.toaster.thanos.loadbalancers.LoadBalancer;
import dev.toaster.thanos.servers.Server;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ServersService {
    private final LoadBalancer loadBalancer = new LoadBalancer();

    public boolean registerServer(RegisterServerDTO registerServerDTO) {
        Server server = new Server(registerServerDTO.getHostname(), registerServerDTO.getPort());
        String id = registerServerDTO.getId();
        if (id == null || id.isEmpty()) {
            id = server.toString();
        }
        return this.loadBalancer.registerServer(id, server);
    }

    public Server deregisterServer(DeregisterServerDTO deregisterServerDTO) {
        return this.loadBalancer.getServers().remove(deregisterServerDTO.getId());
    }

    public Map<String, Server> getServers() {
        return this.loadBalancer.getServers();
    }
}
