package com.marceloluiz.weatherforecast.controllers;

import com.marceloluiz.weatherforecast.model.HourlyWeatherData;
import com.marceloluiz.weatherforecast.model.WeatherData;
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

    @GetMapping("/forecast")
    public ResponseEntity<WeatherForecast<WeatherData>> getWeatherForecast() throws ApiException {
        return ResponseEntity.ok(weatherService.getWeatherForecast());
    }

    @GetMapping("/hourly-forecast")
    public ResponseEntity<WeatherForecast<HourlyWeatherData>> getHourlyWeatherForecast() throws ApiException {
        return ResponseEntity.ok(weatherService.getHourlyWeatherForecast());
    }
}
