package com.marceloluiz.weatherforecast.services;

import com.marceloluiz.weatherforecast.services.exceptions.FileServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@AllArgsConstructor
public class FileService {
    public void writeToMarkdown(String markdown, String readmePath){
        try{
            Files.writeString(Path.of(readmePath), markdown);
        }catch (IOException e){
            throw new FileServiceException("Error to write file", e);
        }
    }

    public String readMarkdown(String readmePath){
        try{
            return Files.readString(Path.of(readmePath));
        } catch (IOException e){
            throw new FileServiceException("Error to read file", e);
        }
    }
}
