package com.marceloluiz.weatherforecast.controllers;

import com.marceloluiz.weatherforecast.services.WeatherService;
import io.swagger.client.ApiException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping("/forecast")
    public ResponseEntity<?> getForecast() throws ApiException {
        return ResponseEntity.ok(weatherService.getForecast());
    }
}
