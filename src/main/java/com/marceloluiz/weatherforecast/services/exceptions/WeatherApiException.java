package com.marceloluiz.weatherforecast.services.exceptions;

public class WeatherApiException extends RuntimeException{
    public WeatherApiException(String message) {
        super(message);
    }

    public WeatherApiException(String message, Throwable cause) {
        super(message, cause);
    }

}
