package dev.toaster.thanos;

import dev.toaster.thanos.loadbalancers.LoadBalancer;
import dev.toaster.thanos.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
//        LoadBalancer loadBalancer = new LoadBalancer();
//        loadBalancer.registerServer(new Server("127.0.0.1", 8080));
//        loadBalancer.registerServer(new Server("127.0.0.1", 8081));e

        SpringApplication.run(Main.class, args);
    }
}