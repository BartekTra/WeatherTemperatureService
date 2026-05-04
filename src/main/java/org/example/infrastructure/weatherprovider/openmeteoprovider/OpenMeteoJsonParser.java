package org.example.infrastructure.weatherprovider.openmeteoprovider;

import org.example.infrastructure.weatherprovider.openmeteoprovider.exception.WeatherApiException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OpenMeteoJsonParser {

    private static final Logger log = LoggerFactory.getLogger(OpenMeteoJsonParser.class);

    public double parseJsonResponse(String response) {
        try{
            JSONParser parser = new JSONParser();
            JSONObject dataObj = (JSONObject) parser.parse(response);

            JSONObject currentObj = (JSONObject) dataObj.get("current");
            if (currentObj == null) {
                log.error("Missing 'current' field in API response: {}", response);
                throw new WeatherApiException("Missing 'current' field in API response");
            }

            Number temp = (Number) currentObj.get("temperature_2m");
            if (temp == null) {
                log.error("Missing 'temperature_2m' field in API response: {}", response);
                throw new WeatherApiException("Missing 'temperature_2m' field in API response");
            }

            log.debug("Parsed temperature: {}", temp.doubleValue());
            return temp.doubleValue();
            
        } catch (ParseException | NullPointerException e){
            throw new WeatherApiException(e.getMessage(), e);
        }
    }
}
