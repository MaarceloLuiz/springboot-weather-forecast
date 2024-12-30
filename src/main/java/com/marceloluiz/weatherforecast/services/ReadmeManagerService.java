package com.marceloluiz.weatherforecast.services;

import com.marceloluiz.weatherforecast.config.FileProperties;
import com.marceloluiz.weatherforecast.model.HourlyWeatherData;
import com.marceloluiz.weatherforecast.model.WeatherData;
import com.marceloluiz.weatherforecast.model.WeatherForecast;
import com.marceloluiz.weatherforecast.services.exceptions.FileServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReadmeManagerService {
    private final WeatherService weatherService;
    private final MarkdownService markdownService;
    private final FileService fileService;
    private final FileProperties fileProperties;

    public void updateReadme(String tableType){
        try{
            String readme = fileService.readMarkdown(fileProperties.getReadmePath());

            WeatherForecast<WeatherData> forecast = weatherService.getWeatherForecast();
            WeatherForecast<HourlyWeatherData> hourlyForecast = weatherService.getHourlyWeatherForecast();

            String hourlyWeatherMarkdown = "";
            String multiDayWeatherMarkdown = "";

            if ("hourly".equalsIgnoreCase(tableType) || "both".equalsIgnoreCase(tableType)) {
                hourlyWeatherMarkdown = markdownService.generateHourlyWeatherMarkdown(forecast, hourlyForecast);
            }

            if ("multi-day".equalsIgnoreCase(tableType) || "both".equalsIgnoreCase(tableType)) {
                multiDayWeatherMarkdown = markdownService.generateWeatherMarkdown(forecast);
            }

            readme = insertTable(readme, "<!-- HOURLY-START -->", "<!-- HOURLY-END -->", hourlyWeatherMarkdown);
            readme = insertTable(readme, "<!-- MULTI-DAY-START -->", "<!-- MULTI-DAY-END -->", multiDayWeatherMarkdown);

            fileService.writeToMarkdown(readme, fileProperties.getReadmePath());
        }catch (IllegalStateException e){
            throw new FileServiceException("Required placeholders not found in README.", e);
        }catch (Exception e){
            throw new FileServiceException("Failed to update file", e);
        }
    }

    private String insertTable(String original, String startPlaceholder, String endPlaceholder, String content){
        if (!original.contains(startPlaceholder) || !original.contains(endPlaceholder)) {
            throw new IllegalStateException("Placeholders not found in README.md");
        }

        int startIndex = original.indexOf(startPlaceholder);
        int endIndex = original.indexOf(endPlaceholder);

        if (startIndex == -1 || endIndex == -1 || startIndex >= endIndex) {
            throw new IllegalStateException("Placeholders not found or invalid in the content.");
        }

        startIndex += startPlaceholder.length();
        String before = original.substring(0, startIndex);
        String after = original.substring(endIndex);

        return before + "\n" + content + "\n" + after;
    }
}
