package org.example.infrastructure.locationprovider.universallocationprovider;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.example.infrastructure.locationprovider.LocationProvider;
import org.example.models.Coordinates;
import org.example.models.Location;

public class CityLocationProvider implements LocationProvider {

    private final CityRequestExtractor cityRequestExtractor;
    private final GeocodingApiClient geocodingApiClient;
    private final GeocodingResponseParser geocodingResponseParser;

    public CityLocationProvider(
            CityRequestExtractor cityRequestExtractor,
            GeocodingApiClient geocodingApiClient,
            GeocodingResponseParser geocodingResponseParser
    ) {
        this.cityRequestExtractor = cityRequestExtractor;
        this.geocodingApiClient = geocodingApiClient;
        this.geocodingResponseParser = geocodingResponseParser;
    }

    @Override
    public Location getCoordinates(APIGatewayProxyRequestEvent request) {
        String city = cityRequestExtractor.extract(request);
        String rawResponse = geocodingApiClient.fetchRawResponse(city);
        Coordinates coords = geocodingResponseParser.parse(rawResponse, city);
        return new Location(city, coords);
    }
}