package org.example.infrastructure.locationprovider;


import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.example.models.Coordinates;

public interface LocationProvider {
    Coordinates getCoordinates(APIGatewayProxyRequestEvent request);
}