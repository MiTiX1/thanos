package dev.toaster.thanos.loadbalancers;

import dev.toaster.thanos.servers.Server;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinStrategy implements LoadBalancingStrategy{
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Server selectServer(List<Server> servers) {
        return servers.get(this.counter.getAndIncrement() % servers.size());
    }
}
