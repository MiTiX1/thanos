package dev.toaster.thanos.dtos;

import java.net.MalformedURLException;
import java.net.URL;

public record RegisterServerDTO(String id, String url) {
    public URL getURL() throws MalformedURLException {
        return new URL(url);
    }
}
