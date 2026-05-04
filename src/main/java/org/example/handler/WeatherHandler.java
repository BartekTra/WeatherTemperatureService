package org.example.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.example.domain.TemperatureClassifier;
import org.example.domain.WeatherService;
import org.example.infrastructure.locationprovider.LocationProvider;
import org.example.infrastructure.locationprovider.wroclawlocationprovider.WroclawLocationProvider;
import org.example.infrastructure.weatherprovider.openmeteoprovider.OpenMeteoClient;
import org.example.models.Coordinates;
import org.example.models.ResponseBuilder;
import org.example.models.WeatherResult;

import java.net.http.HttpClient;

public class WeatherHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final WeatherService weatherService;
    private final LocationProvider locationProvider;

    public WeatherHandler() {
        this(
                new WeatherService(OpenMeteoClient.createDefault(HttpClient.newHttpClient()), new TemperatureClassifier()),
                new WroclawLocationProvider()
        );
    }

    public WeatherHandler(WeatherService weatherService, LocationProvider locationProvider) {
        this.weatherService = weatherService;
        this.locationProvider = locationProvider;
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        try{
            Coordinates coords = locationProvider.getCoordinates(request);
            WeatherResult result = weatherService.getCurrentTemperature(coords.latitude(), coords.longitude());
            return ResponseBuilder.buildResponse(result);
        }catch (RuntimeException e){
            return ResponseBuilder.buildErrorResponse(e.getMessage());
        }
    }

}