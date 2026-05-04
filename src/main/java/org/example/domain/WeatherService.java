package org.example.domain;

import org.example.infrastructure.weatherprovider.WeatherApiClient;
import org.example.models.TemperatureCategory;
import org.example.models.WeatherResult;

public class WeatherService {

    private final WeatherApiClient weatherApiClient;
    private final TemperatureClassifier temperatureClassifier;

    public WeatherService(WeatherApiClient weatherApiClient, TemperatureClassifier temperatureClassifier) {
        this.weatherApiClient = weatherApiClient;
        this.temperatureClassifier = temperatureClassifier;
    }

    public WeatherResult getCurrentTemperature(double latitude, double longitude){
        double temperature = weatherApiClient.getCurrentTemperature(latitude, longitude);
        TemperatureCategory temperatureClass = temperatureClassifier.classifyTemperature(temperature);

        return new WeatherResult(temperature, temperatureClass);
    }

}
