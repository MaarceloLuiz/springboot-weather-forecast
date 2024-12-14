package com.marceloluiz.weatherforecast.services;

import com.marceloluiz.weatherforecast.model.WeatherForecast;
import org.springframework.stereotype.Service;

@Service
public class MarkdownService {
    public String generateWeatherMarkdown(WeatherForecast forecast){
        StringBuilder markdown = new StringBuilder();

        markdown.append("## ")
                .append(forecast.getForecast().getFirst().getName())
                .append("'s Weather Forecast for Next ")
                .append(forecast.getForecast().size())
                .append(" Days\n\n");

        markdown.append("<table>\n");
        markdown.append("<tr><th>Date</th><th>Weather</th><th>Condition</th><th>Temperature</th><th>Wind</th></tr>\n");

        forecast.getForecast().forEach(weatherData -> {
            String temperature = weatherData.getMinTemperature() + " - " + weatherData.getMaxTemperature();

            markdown.append("<tr>")
                    .append("<td>").append(weatherData.getDate()).append("</td>")
                    .append("<td><img src=\"https:").append(weatherData.getConditionImgUrl()).append("\" alt=\"Weather Condition Icon\"/></td>")
                    .append("<td>").append(weatherData.getCondition()).append("</td>")
                    .append("<td>").append(temperature).append(" °C</td>")
                    .append("<td>").append(weatherData.getMaxWind()).append(" kph</td>")
                    .append("</tr>\n");
        });
        markdown.append("</table>\n");

        return markdown.toString();
    }

    //will finish later
    public String generateAppInfoMarkdown(){
        StringBuilder markdown = new StringBuilder();

        return markdown.toString();
    }
}
