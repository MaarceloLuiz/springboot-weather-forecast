package com.marceloluiz.weatherforecast.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
public class FileProperties {
    private final String path = "README.md";
}
