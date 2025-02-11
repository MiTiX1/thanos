package dev.toaster.thanos.dtos;

public class RegisterServerDTO {
    private String id;
    private String hostname;
    private int port;

    public RegisterServerDTO() {}

    public RegisterServerDTO(String id, String hostname, int port) {
        this.id = id;
        this.hostname = hostname;
        this.port = port;
    }

    public RegisterServerDTO(String hostname, int port) {
        this(null, hostname, port);
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
}
