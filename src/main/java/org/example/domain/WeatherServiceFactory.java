package org.example.domain;

import org.example.infrastructure.weatherprovider.openmeteoprovider.OpenMeteoClient;

import java.net.http.HttpClient;

public class WeatherServiceFactory {
    public static WeatherService createDefault() {

        return new WeatherService(
                OpenMeteoClient.createDefault(HttpClient.newHttpClient()),
                new TemperatureClassifier()
        );

    }
}
