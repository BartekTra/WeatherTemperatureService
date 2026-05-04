package org.example.infrastructure.locationprovider;


import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.example.models.Location;

public interface LocationProvider {
    Location getCoordinates(APIGatewayProxyRequestEvent request);
}