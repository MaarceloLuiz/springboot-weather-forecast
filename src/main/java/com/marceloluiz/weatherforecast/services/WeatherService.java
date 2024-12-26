package com.marceloluiz.weatherforecast.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.marceloluiz.weatherforecast.config.WeatherProperties;
import com.marceloluiz.weatherforecast.model.HourlyWeatherData;
import com.marceloluiz.weatherforecast.services.exceptions.WeatherApiException;
import com.marceloluiz.weatherforecast.model.WeatherData;
import com.marceloluiz.weatherforecast.model.WeatherForecast;
import com.marceloluiz.weatherforecast.services.exceptions.WeatherInvalidResponseException;
import io.swagger.client.ApiException;
import io.swagger.client.JSON;
import io.swagger.client.api.ApisApi;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class WeatherService {
    private final ApisApi apisApi;
    private final WeatherProperties properties;
    private final JSON jsonParser = new JSON();

    public WeatherForecast<WeatherData> getWeatherForecast() {
        Object rawJson = getRawForecastData();
        String json = jsonParser.getGson().toJson(rawJson);

        return parseForecastData(json);
    }

    public WeatherForecast<HourlyWeatherData> getHourlyWeatherForecast() {
        Object rawJson = getRawForecastData();
        String json = jsonParser.getGson().toJson(rawJson);

        return parseHourlyForecastData(json);
    }

    private Object getRawForecastData() {
        try{
            return apisApi.forecastWeather(properties.getCity(), properties.getDays(), null, null, null, null, null, null, null);
        } catch (ApiException e){
            throw new WeatherApiException("Failed to connect to API", e);
        }
    }

    private WeatherForecast<WeatherData> parseForecastData(String json){
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
                JsonObject forecastAstro = forecastDayJson.getAsJsonObject("astro");

                String date = forecastDayJson.get("date").getAsString();
                double minTemperature = dayDetailsJson.get("mintemp_c").getAsDouble();
                double maxTemperature = dayDetailsJson.get("maxtemp_c").getAsDouble();
                double maxWind = dayDetailsJson.get("maxwind_kph").getAsDouble();
                String condition = conditionsDetailsJson.get("text").getAsString();
                String conditionImgUrl = conditionsDetailsJson.get("icon").getAsString();
                String moonPhase = forecastAstro.get("moon_phase").getAsString();
                String moonImg = "assets/img/" + moonPhase + ".png";

                weatherData.add(WeatherData.valueOf(name, date, minTemperature, maxTemperature, maxWind, condition, conditionImgUrl, moonPhase, moonImg));
            }

            return WeatherForecast.from(weatherData);
        }catch (JsonSyntaxException | NullPointerException e){
            throw new WeatherInvalidResponseException("Failed to fetch forecast from JSON", e);
        }
    }

    private WeatherForecast<HourlyWeatherData> parseHourlyForecastData(String json){
        try{
            JsonElement element = jsonParser.getGson().fromJson(json, JsonElement.class);
            JsonObject root = element.getAsJsonObject();
            JsonArray forecastDays = root.getAsJsonObject("forecast").getAsJsonArray("forecastday");

            String name = root.getAsJsonObject("location").get("name").getAsString();

            List<HourlyWeatherData> weatherData = new ArrayList<>();
            String todayDate = LocalDate.now().toString();

            List<JsonObject> todayHours = getTodayHours(forecastDays, todayDate);

            for(JsonObject todayHour : todayHours) {
                JsonObject conditionsDetails = todayHour.getAsJsonObject("condition");

                String hour = todayHour.get("time").getAsString().split(" ")[1];
                double temperature = todayHour.get("temp_c").getAsDouble();
                double wind = todayHour.get("wind_kph").getAsDouble();
                String condition = conditionsDetails.get("text").getAsString();
                String conditionImgUrl = conditionsDetails.get("icon").getAsString();

                weatherData.add(HourlyWeatherData.valueOf(name, todayDate, hour, temperature, wind, condition, conditionImgUrl));
            }

            return WeatherForecast.from(weatherData);

        }catch (JsonSyntaxException | NullPointerException e){
            throw new WeatherInvalidResponseException("Failed to fetch hourly forecast from JSON", e);
        }
    }

    private static List<JsonObject> getTodayHours(JsonArray forecastDays, String todayDate) {
        List<JsonObject> todayHours = new ArrayList<>();

        for(JsonElement forecastDayNode : forecastDays){
            JsonObject forecastDay = forecastDayNode.getAsJsonObject();
            String date = forecastDay.get("date").getAsString();

            if(todayDate.equals(date)){
                JsonArray hourDetails = forecastDay.getAsJsonArray("hour");
                hourDetails.forEach(hour -> todayHours.add(hour.getAsJsonObject()));
            }
        }
        return todayHours;
    }

}
