package com.marceloluiz.weatherforecast.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class WeatherForecast<T extends BaseWeatherData> {
    private List<T> forecast = new ArrayList<>();

    public static <T extends BaseWeatherData> WeatherForecast<T> from(List<T> forecast){
        return new WeatherForecast<>(forecast);
    }

    private WeatherForecast(List<T> forecast) {
        this.forecast = forecast;
    }
}
