package org.example.infrastructure.locationprovider.universallocationprovider;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class CityRequestExtractor {

    private static final Logger log = LoggerFactory.getLogger(CityRequestExtractor.class);
    private static final String DEFAULT_CITY = "Wroclaw";

    public String extract(APIGatewayProxyRequestEvent request) {
        Map<String, String> queryParams = request.getQueryStringParameters();

        if (queryParams != null && queryParams.containsKey("city")) {
            return queryParams.get("city");
        }

        log.warn("City parameter is missing in the request. Falling back to default: {}", DEFAULT_CITY);
        return DEFAULT_CITY;
    }
}