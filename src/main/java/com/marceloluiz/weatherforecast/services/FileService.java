package com.marceloluiz.weatherforecast.services;

import com.marceloluiz.weatherforecast.config.FileProperties;
import com.marceloluiz.weatherforecast.services.exceptions.FileServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Service
@AllArgsConstructor
public class FileService {
    private FileProperties fileProperties;

    public void writeToReadme(String markdown){
        String filePath = fileProperties.getPath();
        try{
            Files.write(Path.of(filePath), markdown.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
        }catch (IOException e){
            throw new FileServiceException("Error to write file", e);
        }
    }

    public String readReadme(){
        String filePath = fileProperties.getPath();
        try{
            return Files.readString(Path.of(filePath));
        } catch (IOException e){
            throw new FileServiceException("Error to read file", e);
        }
    }
}
