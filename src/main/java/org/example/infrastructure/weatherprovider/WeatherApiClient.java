package org.example.infrastructure.weatherprovider;

public interface WeatherApiClient {
    double getCurrentTemperature(double latitude, double longitude);
}