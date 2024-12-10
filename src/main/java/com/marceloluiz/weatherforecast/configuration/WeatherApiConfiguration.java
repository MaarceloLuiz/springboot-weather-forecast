package com.marceloluiz.weatherforecast.configuration;

import io.swagger.client.ApiClient;
import io.swagger.client.api.ApisApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeatherApiConfiguration {
    private static final String API_BASE_PATH = "https://api.weatherapi.com/v1/";
    private static final String API_KEY = "";

    @Bean
    public ApisApi apisApi(){
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(API_BASE_PATH);
        apiClient.addDefaultHeader("key", API_KEY);

        return new ApisApi(apiClient);
    }
}
