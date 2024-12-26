package com.marceloluiz.weatherforecast.services;

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

    public void updateReadme(){
        try{
            String readmeHowToUse = fileService.readHowToUseReadme();

            WeatherForecast<WeatherData> forecast = weatherService.getWeatherForecast();
            WeatherForecast<HourlyWeatherData> hourlyForecast = weatherService.getHourlyWeatherForecast();

            String newReadme = markdownService.generateHourlyWeatherMarkdown(forecast, hourlyForecast)
                    + markdownService.generateWeatherMarkdown(forecast)
                    + readmeHowToUse;

            fileService.writeToReadme(newReadme);
        }catch (IllegalStateException e){
            throw new FileServiceException("Required placeholders not found in README.", e);
        }catch (Exception e){
            throw new FileServiceException("Failed to update file", e);
        }
    }
}
