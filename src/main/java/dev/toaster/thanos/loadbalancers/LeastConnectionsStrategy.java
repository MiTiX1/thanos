package dev.toaster.thanos.loadbalancers;

import dev.toaster.thanos.servers.Server;

import java.util.List;

public class LeastConnectionsStrategy implements LoadBalancingStrategy {

    @Override
    public Server selectServer(List<Server> servers) {
        return servers.stream()
                .min((s1, s2) -> Integer.compare(s1.getActiveConnections(), s2.getActiveConnections()))
                .orElse(null);
    }
}
