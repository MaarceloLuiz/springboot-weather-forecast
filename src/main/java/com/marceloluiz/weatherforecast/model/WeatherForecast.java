package com.marceloluiz.weatherforecast.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class WeatherForecast {
    private List<WeatherData> forecast = new ArrayList<>();

    public static WeatherForecast from(List<WeatherData> forecast){
        return new WeatherForecast(forecast);
    }

    private WeatherForecast(List<WeatherData> forecast) {
        this.forecast = forecast;
    }
}
