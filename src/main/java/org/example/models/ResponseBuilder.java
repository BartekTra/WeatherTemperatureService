package org.example.models;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class ResponseBuilder {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private ResponseBuilder() {
        throw new UnsupportedOperationException("This is a utility class and should not be instantiated");
    }

    public static APIGatewayProxyResponseEvent buildResponse(WeatherResult result) {
        Map<String, Object> responseBody = Map.of(
                "temperature", result.temperature(),
                "temperature_class", result.category().getDisplayName()
        );
        return buildApiResponse(200, responseBody);
    }

    public static APIGatewayProxyResponseEvent buildErrorResponse(String errorMessage) {
        Map<String, Object> responseBody = Map.of(
                "error", "Unable to call Weather Service",
                "details", errorMessage
        );
        return buildApiResponse(500, responseBody);
    }

    private static APIGatewayProxyResponseEvent buildApiResponse(int statusCode, Map<String, Object> body) {
        try {
            APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
            response.setStatusCode(statusCode);
            response.setHeaders(Map.of("Content-Type", "application/json"));
            response.setBody(OBJECT_MAPPER.writeValueAsString(body));
            return response;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize response to JSON", e);
        }
    }
}