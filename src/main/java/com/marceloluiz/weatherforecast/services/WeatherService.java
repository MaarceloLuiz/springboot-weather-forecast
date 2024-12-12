package com.marceloluiz.weatherforecast.services;

import com.marceloluiz.weatherforecast.config.WeatherProperties;
import io.swagger.client.ApiException;
import io.swagger.client.api.ApisApi;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WeatherService {
    private final ApisApi apisApi;
    private final WeatherProperties properties;

    public Object getForecast() throws ApiException {
        return apisApi.forecastWeather(properties.getCity(), properties.getDays(), null, null, null, null, null, null, null);
    }
}
