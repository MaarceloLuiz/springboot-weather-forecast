package com.marceloluiz.weatherforecast.services.exceptions;

public class WeatherInvalidResponseException extends RuntimeException {
    public WeatherInvalidResponseException(String message) {
        super(message);
    }

    public WeatherInvalidResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
