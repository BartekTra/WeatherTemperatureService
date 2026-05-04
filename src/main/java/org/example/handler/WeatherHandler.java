package org.example.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.example.domain.WeatherService;
import org.example.domain.WeatherServiceFactory;
import org.example.infrastructure.locationprovider.LocationProvider;
import org.example.infrastructure.locationprovider.universallocationprovider.*;
import org.example.models.Location;
import org.example.models.ResponseBuilder;
import org.example.models.WeatherResult;


public class WeatherHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final WeatherService weatherService;
    private final LocationProvider locationProvider;

    public WeatherHandler() {
        this(
                WeatherServiceFactory.createDefault(),
                CityLocationProviderFactory.createDefault()
        );
    }

    public WeatherHandler(WeatherService weatherService, LocationProvider locationProvider) {
        this.weatherService = weatherService;
        this.locationProvider = locationProvider;
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        try{
            Location location = locationProvider.getCoordinates(request);

            WeatherResult result = weatherService.getCurrentTemperature(
                    location.coordinates().latitude(),
                    location.coordinates().longitude()
            );

            return ResponseBuilder.buildResponse(location.cityName(), result);
        }catch (RuntimeException e){
            return ResponseBuilder.buildErrorResponse(e.getMessage());
        }
    }

}