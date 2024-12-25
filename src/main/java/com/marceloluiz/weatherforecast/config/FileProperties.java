package com.marceloluiz.weatherforecast.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class FileProperties {
    private final String primaryReadmePath  = "README.md";
    private final String howToUseReadmePath  = "README_how_to_use.md";
}
