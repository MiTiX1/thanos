package dev.toaster.thanos.servers;

public record Server(String hostname, int port) {

    @Override
    public String toString() {
        return this.hostname + ":" + this.port;
    }
}
