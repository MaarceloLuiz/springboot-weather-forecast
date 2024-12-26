package com.marceloluiz.weatherforecast.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class HourlyWeatherData extends BaseWeatherData{
    private String name;
    private String date;
    private String hour;
    private double temperature;
    private double wind;
    private String condition;
    private String conditionImgUrl;

    public static HourlyWeatherData valueOf(String name, String date, String hour, double temperature, double wind, String condition, String conditionImgUrl){
        return new HourlyWeatherData(name, date, hour, temperature, wind, condition, conditionImgUrl);
    }

    public HourlyWeatherData(String name, String date, String hour, double temperature, double wind, String condition, String conditionImgUrl) {
        this.name = name;
        this.date = date;
        this.hour = hour;
        this.temperature = temperature;
        this.wind = wind;
        this.condition = condition;
        this.conditionImgUrl = conditionImgUrl;
    }
}
