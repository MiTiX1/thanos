package dev.toaster.thanos.config;

import java.util.List;

public class ConfigProperties {
    private List<ServerConfig> servers;
    private LoadBalancingConfig loadBalancing;

    public static class ServerConfig {
        private String id;
        private String url;

        public String getId() {
            return id;
        }

        public String getUrl() {
            return url;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class LoadBalancingConfig {
        private String strategy;

        public String getStrategy() {
            return strategy;
        }

        public void setStrategy(String strategy) {
            this.strategy = strategy;
        }
    }

    public List<ServerConfig> getServers() {
        return servers;
    }

    public LoadBalancingConfig getLoadBalancing() {
        return loadBalancing;
    }

    public void setServers(List<ServerConfig> servers) {
        this.servers = servers;
    }

    public void setLoadBalancing(LoadBalancingConfig loadBalancing) {
        this.loadBalancing = loadBalancing;
    }
}
