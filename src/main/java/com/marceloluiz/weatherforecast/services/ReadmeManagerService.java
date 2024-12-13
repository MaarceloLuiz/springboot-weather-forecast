package com.marceloluiz.weatherforecast.services;

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
            WeatherForecast forecast = weatherService.getWeatherForecast();
            fileService.writeToReadme(markdownService.generateWeatherMarkdown(forecast));
        }catch (Exception e){
            throw new FileServiceException("Failed to update file", e);
        }
    }
}
