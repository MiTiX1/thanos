package dev.toaster.thanos.config;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

public class ConfigLoader {
    private static final String CONFIG_FILE_PATH = "config.yml";

    public static ConfigProperties loadConfig() {
        Yaml yaml = new Yaml();

        InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream(CONFIG_FILE_PATH);
        if (inputStream == null) {
            System.out.println("Config file not found");
        }

        return yaml.loadAs(inputStream, ConfigProperties.class);
    }
}
