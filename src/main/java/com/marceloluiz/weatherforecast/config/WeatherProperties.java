package com.marceloluiz.weatherforecast.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class WeatherProperties {
    @Value("${FORECAST_CITY}") // Default city
    private String city;

    @Value("${FORECAST_DAYS}") // Default forecast days
    private int days;
}
