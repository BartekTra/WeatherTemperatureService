package org.example.infrastructure.locationprovider.wroclawlocationprovider;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.example.infrastructure.locationprovider.LocationProvider;
import org.example.models.Coordinates;
import org.example.models.Location;

public class WroclawLocationProvider implements LocationProvider {

    private final String city = "Wroclaw";
    private final double wroclawLatitude = 51.1;
    private final double wroclawLongitude = 17.0333;

    @Override
    public Location getCoordinates(APIGatewayProxyRequestEvent request) {
        Coordinates coords = new Coordinates(wroclawLatitude, wroclawLongitude);
        return new Location(city, coords);
    }
}