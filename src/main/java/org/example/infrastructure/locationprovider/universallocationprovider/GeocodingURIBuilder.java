package org.example.infrastructure.locationprovider.universallocationprovider;

import org.apache.hc.core5.net.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;

public class GeocodingURIBuilder {

    private static final String GEOCODING_API_URL = System.getenv("GEOCODING_OPEN_METEO_URL_ENV");

    public URI buildURI(String city) {
        try {
            return new URIBuilder(GEOCODING_API_URL)
                    .addParameter("name", city)
                    .addParameter("count", "1") // We only want first result of the search
                    .addParameter("language", "en")
                    .addParameter("format", "json")
                    .build();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Failed to build Geocoding API URL address", e);
        }
    }
}