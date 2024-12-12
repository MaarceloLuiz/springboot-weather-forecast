package com.marceloluiz.weatherforecast.controllers;

import com.marceloluiz.weatherforecast.model.WeatherForecast;
import com.marceloluiz.weatherforecast.services.WeatherService;
import io.swagger.client.ApiException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/weather")
@AllArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping("/forecast/raw")
    public ResponseEntity<?> getRawForecastData() throws ApiException {
        return ResponseEntity.ok(weatherService.getRawForecastData());
    }

    @GetMapping("/forecast")
    public ResponseEntity<WeatherForecast> getWeatherForecast() throws ApiException {
        return ResponseEntity.ok(weatherService.getWeatherForecast());
    }
}
