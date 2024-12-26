package com.marceloluiz.weatherforecast.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class WeatherData extends BaseWeatherData{
    private String name;
    private String date;
    private double minTemperature;
    private double maxTemperature;
    private double maxWind;
    private String condition;
    private String conditionImgUrl;
    private String moonPhase;
    private String moonImg;

    public static WeatherData valueOf(String name, String date, double minTemperature, double maxTemperature, double maxWind, String condition, String conditionImgUrl, String moonPhase, String moonImg){
        return new WeatherData(name, date, minTemperature, maxTemperature, maxWind, condition, conditionImgUrl, moonPhase, moonImg);
    }

    private WeatherData(String name, String date, double minTemperature, double maxTemperature, double maxWind, String condition, String conditionImgUrl, String moonPhase, String moonImg) {
        this.name = name;
        this.date = date;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.maxWind = maxWind;
        this.condition = condition;
        this.conditionImgUrl = conditionImgUrl;
        this.moonPhase = moonPhase;
        this.moonImg = moonImg;
    }
}
