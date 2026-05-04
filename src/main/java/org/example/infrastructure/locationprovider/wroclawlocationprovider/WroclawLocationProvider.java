package org.example.infrastructure.locationprovider.wroclawlocationprovider;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.example.infrastructure.locationprovider.LocationProvider;
import org.example.models.Coordinates;

public class WroclawLocationProvider implements LocationProvider {

    private final double wroclawLatitude = 51.1;
    private final double wroclawLongitude = 17.0333;

    @Override
    public Coordinates getCoordinates(APIGatewayProxyRequestEvent request) {
        return new Coordinates(wroclawLatitude, wroclawLongitude);
    }
}