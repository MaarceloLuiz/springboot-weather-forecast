package com.marceloluiz.weatherforecast.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.marceloluiz.weatherforecast.config.WeatherProperties;
import com.marceloluiz.weatherforecast.services.exceptions.WeatherApiException;
import com.marceloluiz.weatherforecast.model.WeatherData;
import com.marceloluiz.weatherforecast.model.WeatherForecast;
import com.marceloluiz.weatherforecast.services.exceptions.WeatherInvalidResponseException;
import io.swagger.client.ApiException;
import io.swagger.client.JSON;
import io.swagger.client.api.ApisApi;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class WeatherService {
    private final ApisApi apisApi;
    private final WeatherProperties properties;
    private final JSON jsonParser = new JSON();

    public WeatherForecast getWeatherForecast() {
        Object rawJson = getRawForecastData();
        String json = jsonParser.getGson().toJson(rawJson);

        return parseForecastData(json);
    }

    private Object getRawForecastData() {
        try{
            return apisApi.forecastWeather(properties.getCity(), properties.getDays(), null, null, null, null, null, null, null);
        } catch (ApiException e){
            throw new WeatherApiException("Failed to connect to API", e);
        }
    }

    private WeatherForecast parseForecastData(String json){
        try{
            JsonElement element = jsonParser.getGson().fromJson(json, JsonElement.class);
            JsonObject root = element.getAsJsonObject();
            JsonArray forecastDays = root.getAsJsonObject("forecast").getAsJsonArray("forecastday");

            String name = root.getAsJsonObject("location").get("name").getAsString();
            List<WeatherData> weatherData = new ArrayList<>();

            for(JsonElement forecastDayNode : forecastDays){
                JsonObject forecastDayJson = forecastDayNode.getAsJsonObject();
                JsonObject dayDetailsJson = forecastDayJson.getAsJsonObject("day");
                JsonObject conditionsDetailsJson = dayDetailsJson.getAsJsonObject("condition");

                String date = forecastDayJson.get("date").getAsString();
                double minTemperature = dayDetailsJson.get("mintemp_c").getAsDouble();
                double maxTemperature = dayDetailsJson.get("maxtemp_c").getAsDouble();
                double maxWind = dayDetailsJson.get("maxwind_kph").getAsDouble();
                String condition = conditionsDetailsJson.get("text").getAsString();
                String conditionImgUrl = conditionsDetailsJson.get("icon").getAsString();

                weatherData.add(WeatherData.valueOf(name, date, minTemperature, maxTemperature, maxWind, condition, conditionImgUrl));
            }

            return WeatherForecast.from(weatherData);
        }catch (JsonSyntaxException | NullPointerException e){
            throw new WeatherInvalidResponseException("Failed to fetch forecast from JSON", e);
        }
    }
}
