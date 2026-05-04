package org.example.infrastructure.locationprovider.universallocationprovider;

import org.example.models.Coordinates;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeocodingResponseParser {

    private static final Logger log = LoggerFactory.getLogger(GeocodingResponseParser.class);

    public Coordinates parse(String jsonBody, String city) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonResponse = (JSONObject) parser.parse(jsonBody);
            JSONArray results = (JSONArray) jsonResponse.get("results");

            if (results == null || results.isEmpty()) {
                log.warn("Geocoding API returned empty results for city: {}", city);
                throw new IllegalArgumentException("City not found: " + city);
            }

            JSONObject firstResult = (JSONObject) results.get(0);
            Number lat = (Number) firstResult.get("latitude");
            Number lon = (Number) firstResult.get("longitude");

            log.debug("Resolved {} to lat: {}, lon: {}", city, lat.doubleValue(), lon.doubleValue());

            return new Coordinates(lat.doubleValue(), lon.doubleValue());

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to parse geocoding response for city: {}", city, e);
            throw new RuntimeException("Unable to parse geocoding response for city: " + city, e);
        }
    }
}