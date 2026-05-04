package org.example.domain;

import org.example.models.TemperatureCategory;

public class TemperatureClassifier {

    public TemperatureCategory classifyTemperature(double temperature){
        if(temperature < 0){
            return TemperatureCategory.FREEZING;
        } else if (temperature <= 10){
            return TemperatureCategory.COLD;
        } else if (temperature <= 20){
            return TemperatureCategory.MILD;
        } else if (temperature <= 30){
            return TemperatureCategory.WARM;
        } else {
            return TemperatureCategory.HOT;
        }

    }
}
