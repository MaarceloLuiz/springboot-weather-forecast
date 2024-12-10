package com.marceloluiz.weatherforecast.controller;

import com.marceloluiz.weatherforecast.services.WeatherService;
import io.swagger.client.ApiException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WeatherController {
    @Autowired
    private final WeatherService weatherService;

    @GetMapping("/forecast")
    public Object getForecast(@RequestParam(defaultValue = "Rio de Janeiro") String city, @RequestParam(defaultValue = "3") int days) throws ApiException {
        return weatherService.getForecast(city, days);
    }
}
