package com.marceloluiz.weatherforecast.services;

import com.marceloluiz.weatherforecast.model.HourlyWeatherData;
import com.marceloluiz.weatherforecast.model.WeatherData;
import com.marceloluiz.weatherforecast.model.WeatherForecast;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class MarkdownService {
    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT);

    public String generateHourlyWeatherMarkdown(WeatherForecast<WeatherData> forecast, WeatherForecast<HourlyWeatherData> hourlyForecast){
        StringBuilder markdown = new StringBuilder();

        markdown.append("## Today's Weather\n\n");
        generateWeatherMarkdownTitle(markdown, forecast.getForecast().getFirst());

        markdown.append("<table>\n")
                .append("<tr>")
                .append("<th>Hour</th>\n");
        hourlyForecast.getForecast().forEach(hourlyData -> markdown.append("<td>")
                                                                    .append(hourlyData.getHour())
                                                                    .append("</td>"));
        markdown.append("</tr>");

        markdown.append("<tr>")
                .append("<th>Weather</th>\n");
        hourlyForecast.getForecast().forEach(hourlyData -> markdown.append("<td style=\"padding: 0;\"><img src=\"https:")
                                                                    .append(hourlyData.getConditionImgUrl())
                                                                    .append("\" alt=\"Weather Condition Icon\" style=\"width:50px; height:50px;\"/></td>"));
        markdown.append("</tr>");

        markdown.append("<tr>")
                .append("<th>Condition</th>\n");
        hourlyForecast.getForecast().forEach(hourlyData -> markdown.append("<td>")
                                                                    .append(hourlyData.getCondition())
                                                                    .append("</td>"));
        markdown.append("</tr>");

        markdown.append("<tr>")
                .append("<th>Temperature</th>\n");
        hourlyForecast.getForecast().forEach(hourlyData -> markdown.append("<td>")
                                                                    .append(hourlyData.getTemperature())
                                                                    .append(" °C</td>"));
        markdown.append("</tr>");

        markdown.append("<tr>")
                .append("<th>Wind</th>\n");
        hourlyForecast.getForecast().forEach(hourlyData -> markdown.append("<td>")
                                                                    .append(hourlyData.getWind())
                                                                    .append(" kph</td>"));
        markdown.append("</tr>")
                .append("</table>\n\n");

        generateUpdatedAt(markdown);

        return markdown.toString();
    }

    public String generateWeatherMarkdown(WeatherForecast<WeatherData> forecast){
        StringBuilder markdown = new StringBuilder();
        markdown.append("## ")
                .append(forecast.getForecast().size())
                .append(" Days ")
                .append("Weather Forecast\n\n");

        generateWeatherMarkdownTitle(markdown, forecast.getForecast().getFirst());

        markdown.append("<table>\n")
                .append("<tr>")
                .append("<th>Date</th>\n");
        forecast.getForecast().forEach(forecastDay -> markdown.append("<td>")
                .append(forecastDay.getDate())
                .append("</td>"));
        markdown.append("</tr>");

        markdown.append("<tr>")
                .append("<th>Weather</th>\n");
        forecast.getForecast().forEach(forecastDay -> markdown.append("<td style=\"padding: 0;\"><img src=\"https:")
                .append(forecastDay.getConditionImgUrl())
                .append("\" alt=\"Weather Condition Icon\" style=\"width:50px; height:50px;\"/></td>"));
        markdown.append("</tr>");

        markdown.append("<tr>")
                .append("<th>Condition</th>\n");
        forecast.getForecast().forEach(forecastDay -> markdown.append("<td width=\"200px\">")
                .append(forecastDay.getCondition())
                .append("</td>"));
        markdown.append("</tr>");

        markdown.append("<tr>")
                .append("<th>Moon Phase</th>\n");
        forecast.getForecast().forEach(forecastDay -> markdown.append("<td>")
                .append(forecastDay.getMoonPhase())
                .append("</td>"));
        markdown.append("</tr>");

        markdown.append("<tr>")
                .append("<th>Moon</th>\n");
        forecast.getForecast().forEach(forecastDay -> markdown.append("<td style=\"padding: 10;\"><img src=\"")
                .append(getImgUrl(forecastDay.getMoonPhase()))
                .append("\" alt=\"Moon Phase Icon\" style=\"width:40px; height:40px;\"/></td>"));
        markdown.append("</tr>");

        markdown.append("<tr>")
                .append("<th>Temperature</th>\n");
        forecast.getForecast().forEach(forecastDay ->{
            String temperature = forecastDay.getMinTemperature() + " - " + forecastDay.getMaxTemperature();
            markdown.append("<td>")
                    .append(temperature)
                    .append(" °C</td>");
        });
        markdown.append("</tr>");

        markdown.append("<tr>")
                .append("<th>Wind</th>\n");
        forecast.getForecast().forEach(forecastDay -> markdown.append("<td>")
                .append(forecastDay.getMaxWind())
                .append(" kph</td>"));
        markdown.append("</tr>")
                .append("</table>\n\n");

        generateUpdatedAt(markdown);

        return markdown.toString();
    }

    private void generateWeatherMarkdownTitle(StringBuilder markdown, WeatherData weatherData){
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

    private void generateUpdatedAt(StringBuilder markdown){
        markdown.append("*Updated at: ")
                .append(generateTimestamp())
                .append(" by [MaarceloLuiz/springboot-weather-forecast](https://github.com/MaarceloLuiz/springboot-weather-forecast)")
                .append("*\n\n");
    }

    private String generateTimestamp(){
        return ZonedDateTime.now().format(FORMATTER);
    }
}
