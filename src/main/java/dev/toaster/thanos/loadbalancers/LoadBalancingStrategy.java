package dev.toaster.thanos.loadbalancers;

import dev.toaster.thanos.servers.Server;

import java.util.List;

public interface LoadBalancingStrategy {
    Server selectServer(List<Server> servers);
}
