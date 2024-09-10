package com.ar.mikellbobadilla.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Component
public class ConfigFilePropertySource implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment env, SpringApplication app) {
        Map<String, Object> properties = new HashMap<>();

        env.getSystemEnvironment().forEach((key, value) -> {
            if (key.endsWith("_FILE") && value != null) {
                /* Remove the '_FILE' */
                String newKey = key.substring(0, key.length() - 5);
                try {
                    String content = Files.readString(Paths.get(value.toString()));
                    properties.put(newKey, content.trim());
                } catch (IOException exc) {
                    throw new RuntimeException(exc);
                }
            }
        });

        env.getPropertySources().addFirst(new MapPropertySource("fileProperties", properties));
    }
}
