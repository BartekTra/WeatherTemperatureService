package org.example.models;

public enum TemperatureCategory {
    FREEZING("Freezing"),
    COLD("Cold"),
    MILD("Mild"),
    WARM("Warm"),
    HOT("Hot");

    private final String displayName;

    TemperatureCategory(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName(){
        return displayName;
    }
}
