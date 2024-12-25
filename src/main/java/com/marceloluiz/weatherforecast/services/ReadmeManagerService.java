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
            String readmeHowToUse = fileService.readHowToUseReadme();

            WeatherForecast forecast = weatherService.getWeatherForecast();
            String newReadme = markdownService.generateWeatherMarkdown(forecast) + readmeHowToUse;

            fileService.writeToReadme(newReadme);
        }catch (IllegalStateException e){
            throw new FileServiceException("Required placeholders not found in README.", e);
        }catch (Exception e){
            throw new FileServiceException("Failed to update file", e);
        }
    }
}
