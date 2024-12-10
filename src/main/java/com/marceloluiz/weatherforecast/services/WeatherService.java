package com.marceloluiz.weatherforecast.services;

import io.swagger.client.ApiException;
import io.swagger.client.api.ApisApi;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WeatherService {
    @Autowired
    private final ApisApi apisApi;

    public Object getForecast(String city, int days) throws ApiException {
        return apisApi.forecastWeather(city, days, null, null, null, null, null, null, null);
    }
}
