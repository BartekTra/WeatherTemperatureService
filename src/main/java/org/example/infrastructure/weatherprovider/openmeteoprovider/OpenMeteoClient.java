package org.example.infrastructure.weatherprovider.openmeteoprovider;

import org.example.infrastructure.weatherprovider.WeatherApiClient;
import org.example.infrastructure.weatherprovider.openmeteoprovider.exception.WeatherApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;

public class OpenMeteoClient implements WeatherApiClient {

    private static final Logger log = LoggerFactory.getLogger(OpenMeteoClient.class);

    private static final double MIN_LATITUDE = -90.0;
    private static final double MAX_LATITUDE = 90.0;
    private static final double MIN_LONGITUDE = -180.0;
    private static final double MAX_LONGITUDE = 180.0;

    private final HttpClient httpClient;
    private final OpenMeteoURIBuilder uriBuilder;
    private final OpenMeteoJsonParser jsonParser;

    public OpenMeteoClient(HttpClient httpClient, OpenMeteoURIBuilder uriBuilder, OpenMeteoJsonParser jsonParser) {
        this.httpClient = httpClient;
        this.uriBuilder = uriBuilder;
        this.jsonParser = jsonParser;
    }

    public static OpenMeteoClient createDefault(HttpClient httpClient) {
        return new OpenMeteoClient(httpClient, new OpenMeteoURIBuilder(), new OpenMeteoJsonParser());
    }

    @Override
    public double getCurrentTemperature(double latitude, double longitude) {
        validateCoordinates(latitude, longitude);

        URI uri = uriBuilder.buildURI(latitude, longitude);
        log.debug("Sending request to: {}", uri);

        HttpResponse<String> response = sendRequest(uri);
        log.debug("Received HTTP {} from weather API", response.statusCode());

        validateResponse(response);
        return parseResponse(response.body());
    }

    private void validateCoordinates(double latitude, double longitude) {
        if (latitude < MIN_LATITUDE || latitude > MAX_LATITUDE) {
            log.warn("Invalid latitude provided: {}", latitude);
            throw new IllegalArgumentException(
                    "Latitude must be between -90 and 90, but was: " + latitude
            );
        }
        if (longitude < MIN_LONGITUDE || longitude > MAX_LONGITUDE) {
            log.warn("Invalid longitude provided: {}", longitude);
            throw new IllegalArgumentException(
                    "Longitude must be between -180 and 180, but was: " + longitude
            );
        }
    }

    private HttpResponse<String> sendRequest(URI uri) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException e) {
            log.error("Network error while calling weather API at: {}", uri, e);
            throw new WeatherApiException("Network error while fetching weather data", e);
        } catch (InterruptedException e) {
            log.error("Request to weather API was interrupted", e);
            Thread.currentThread().interrupt();
            throw new WeatherApiException("Request interrupted while fetching weather data", e);
        }
    }

    private void validateResponse(HttpResponse<String> response) {
        if (response.statusCode() != 200) {
            log.error("Weather API returned error. Status: {}, Body: {}", response.statusCode(), response.body());
            throw new WeatherApiException(
                    "HTTP Error: " + response.statusCode() + " Response: " + response.body()
            );
        }
    }

    private double parseResponse(String body) {
        try {
            return jsonParser.parseJsonResponse(body);
        } catch (Exception e) {
            log.error("Failed to parse weather API response. Body: {}", body, e);
            throw new WeatherApiException("Failed to parse weather API response", e);
        }
    }
}