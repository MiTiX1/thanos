package dev.toaster.thanos.servers;

import java.net.URL;
import java.time.Instant;

public class Server {
    private final String id;
    private final String hostname;
    private final int port;
    private final URL url;
    private Instant lastHealthCheck;

    public Server(String id, URL url) {
        this.id = id;
        this.url = url;
        this.hostname = this.url.getHost();
        this.port = this.url.getPort();
    }

    public void updateLastHealthCheck() {
        this.lastHealthCheck = Instant.now();
    }

    public String getId() {
        return id;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    public URL getUrl() {
        return url;
    }

    public Instant getLastHealthCheck() {
        return lastHealthCheck;
    }

    @Override
    public String toString() {
        return this.url.toString();
    }
}
