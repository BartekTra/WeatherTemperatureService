package org.example.infrastructure.locationprovider.universallocationprovider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GeocodingApiClient {

    private static final Logger log = LoggerFactory.getLogger(GeocodingApiClient.class);
    private static final String GEOCODING_API_URL = System.getenv("GEOCODING_OPEN_METEO_URL_ENV");
    private final GeocodingURIBuilder uriBuilder;

    private final HttpClient httpClient;

    public GeocodingApiClient(HttpClient httpClient, GeocodingURIBuilder uriBuilder) {
        this.httpClient = httpClient;
        this.uriBuilder = uriBuilder;
    }

    public String fetchRawResponse(String city) {
        log.debug("Fetching coordinates for city: {}", city);
        try {
            URI uri = uriBuilder.buildURI(city);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                log.error("Geocoding API error. Status: {}, Body: {}", response.statusCode(), response.body());
                throw new RuntimeException("Failed to fetch location data for city: " + city);
            }

            return response.body();

        } catch (Exception e) {
            log.error("Error during geocoding HTTP call for city: {}", city, e);
            throw new RuntimeException("Unable to fetch geocoding response for city: " + city, e);
        }
    }
}