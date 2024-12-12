package com.marceloluiz.weatherforecast.config;

import io.swagger.client.ApiClient;
import io.swagger.client.api.ApisApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeatherApiConfiguration {
    private static final String API_BASE_PATH = "https://api.weatherapi.com/v1";

    @Value("${WEATHER_API_KEY}")
    private String apiKey;

    @Bean
    public ApisApi apisApi(){
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(API_BASE_PATH);
        apiClient.addDefaultHeader("key", apiKey);

        return new ApisApi(apiClient);
    }
}
