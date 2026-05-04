package org.example.infrastructure.locationprovider.universallocationprovider;

import java.net.http.HttpClient;

public class CityLocationProviderFactory {

    public static CityLocationProvider createDefault() {
        return new CityLocationProvider(
                new CityRequestExtractor(),
                new GeocodingApiClient(HttpClient.newHttpClient(), new GeocodingURIBuilder()),
                new GeocodingResponseParser()
        );
    }
}