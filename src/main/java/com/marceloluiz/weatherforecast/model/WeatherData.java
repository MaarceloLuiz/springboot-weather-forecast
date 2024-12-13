package com.marceloluiz.weatherforecast.model;

import lombok.Data;

@Data
public class WeatherData {
    private String name;
    private String date;
    private double minTemperature;
    private double maxTemperature;
    private double maxWind;
    private String condition;
    private String conditionImgUrl;

    public static WeatherData valueOf(String name, String date, double minTemperature, double maxTemperature, double maxWind, String condition, String conditionImgUrl){
        return new WeatherData(name, date, minTemperature, maxTemperature, maxWind, condition, conditionImgUrl);
    }

    public WeatherData(String name, String date, double minTemperature, double maxTemperature, double maxWind, String condition, String conditionImgUrl) {
        this.name = name;
        this.date = date;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.maxWind = maxWind;
        this.condition = condition;
        this.conditionImgUrl = conditionImgUrl;
    }
}
