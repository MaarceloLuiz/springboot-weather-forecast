package com.marceloluiz.weatherforecast.controllers;

import com.marceloluiz.weatherforecast.services.WeatherService;
import io.swagger.client.ApiException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping("/forecast")
    public Object getForecast() throws ApiException {
        return weatherService.getForecast();
    }
}
