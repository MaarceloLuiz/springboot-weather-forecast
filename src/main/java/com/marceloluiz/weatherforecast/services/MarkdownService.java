package com.marceloluiz.weatherforecast.services;

import com.marceloluiz.weatherforecast.model.WeatherData;
import com.marceloluiz.weatherforecast.model.WeatherForecast;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class MarkdownService {
    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT);

    public String generateWeatherMarkdown(WeatherForecast forecast){
        StringBuilder markdown = new StringBuilder();
        markdown.append("<!-- FORECAST-TABLE-START -->\n\n");

        generateWeatherMarkdownTitle(markdown, forecast.getForecast().getFirst());

        markdown.append("## ")
                .append("Weather Forecast for Next ")
                .append(forecast.getForecast().size())
                .append(" Days\n\n");

        markdown.append("<table>\n");
        markdown.append("<tr><th>Date</th><th>Weather</th><th>Condition</th><th>Moon Phase</th><th>Moon</th><th>Temperature</th><th>Wind</th></tr>\n");

        forecast.getForecast().forEach(weatherData -> {
            String temperature = weatherData.getMinTemperature() + " - " + weatherData.getMaxTemperature();

            markdown.append("<tr>")
                    .append("<td>").append(weatherData.getDate()).append("</td>")
                    .append("<td><img src=\"https:").append(weatherData.getConditionImgUrl()).append("\" alt=\"Weather Condition Icon\" style=\"width:64px; height:64px;\"/></td>")
                    .append("<td>").append(weatherData.getCondition()).append("</td>")
                    .append("<td>").append(weatherData.getMoonPhase()).append("</td>")
                    .append("<td><img src=\"").append(getImgUrl(weatherData.getMoonPhase())).append("\" alt=\"Moon Phase Icon\" style=\"width:50px; height:50px;\"/></td>")
                    .append("<td>").append(temperature).append(" °C</td>")
                    .append("<td>").append(weatherData.getMaxWind()).append(" kph</td>")
                    .append("</tr>\n");
        });
        markdown.append("</table>\n\n");
        markdown.append("*Updated at: ")
                .append(generateTimestamp())
                .append("*\n\n");

        markdown.append("<!-- FORECAST-TABLE-END -->\n\n");

        return markdown.toString();
    }

    private void generateWeatherMarkdownTitle(StringBuilder markdown, WeatherData weatherData){
        markdown.append("## Today's Weather\n\n");

        markdown.append("<div align=\"center\">\n\n");

        markdown.append("`")
                .append(weatherData.getName())
                .append(" - ").append(weatherData.getDate())
                .append("`\n\n");

        markdown.append("<table style=\"border-collapse: collapse; width: auto; margin: auto;\">\n");
        markdown.append("<tr>\n");

        markdown.append("<td align=\"center\" style=\"border: none; padding: 10px;\">\n")
                .append("<img src=\"https:").append(weatherData.getConditionImgUrl()).append("\" alt=\"Weather Condition Icon\" style=\"width:50px; height:50px;\"/>\n\n")
                .append(weatherData.getCondition())
                .append("\n\n")
                .append("</td>\n");

        markdown.append("<td align=\"center\" style=\"border: none; padding: 10px;\">\n")
                .append("<img src=\"").append(getImgUrl(weatherData.getMoonPhase())).append("\" alt=\"Moon Phase Icon\" style=\"width:50px; height:50px;\"/>\n\n")
                .append(weatherData.getMoonPhase())
                .append("\n\n")
                .append("</td>\n");

        markdown.append("</tr>\n");
        markdown.append("</table>\n");
        markdown.append("</div>\n\n");
    }

    private String getImgUrl(String imgName){
        return getRepoUrl() + "/" + imgName + ".png";
    }

    private String getRepoUrl(){
        String repoUrl = System.getenv("REPO_URL");
        if(repoUrl == null || repoUrl.isEmpty()){
            throw new IllegalStateException("REPO_URL environment variable not available");
        }
        return repoUrl;
    }

    private String generateTimestamp(){
        return ZonedDateTime.now().format(FORMATTER);
    }

    //will finish later
    public String generateAppInfoMarkdown(){
        StringBuilder markdown = new StringBuilder();

        return markdown.toString();
    }
}
