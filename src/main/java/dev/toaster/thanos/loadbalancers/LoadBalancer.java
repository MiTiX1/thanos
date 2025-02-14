package dev.toaster.thanos.loadbalancers;

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

    public boolean registerServer(String id, Server server) {
        if (this.servers.containsKey(id)) {
            System.out.println("Server is already registered");
            return false;
        }
        this.servers.put(server.toString(), server);
        return true;
    }

    private Server selectServer() {
        return this.loadBalancingStrategy.selectServer(this.servers.values().stream().toList());
    }

    public Map<String, Server> getServers() {
        return this.servers;
    }
}
