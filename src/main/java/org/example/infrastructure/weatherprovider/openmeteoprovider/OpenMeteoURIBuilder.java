package org.example.infrastructure.weatherprovider.openmeteoprovider;

import org.apache.hc.core5.net.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;

public class OpenMeteoURIBuilder {

    private static final String OPEN_METEO_URL = System.getenv("OPEN_METEO_URL_ENV");

    public URI buildURI(double latitude, double longitude) {
        try {
            return new URIBuilder(OPEN_METEO_URL)
                    .addParameter("latitude", String.valueOf(latitude))
                    .addParameter("longitude", String.valueOf(longitude))
                    .addParameter("current", "temperature_2m")
                    .addParameter("timezone", "auto")
                    .build();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Failed to build Open-Meteo URL address", e);
        }
    }
}
