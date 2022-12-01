package com.testspring.enseirb.demo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Position {
    private final double latitude;
    private final double longitude;

    @JsonCreator
    public Position(@JsonProperty("latitude") double latitude, @JsonProperty("longitude") double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String toJSON() {
        return String.format("{\"latitude\": %f, \"longitude\": %f}", latitude, longitude);
    }

    @Override
    public String toString() {
        return String.format("Position [latitude=%s, longitude=%s]", latitude, longitude);
    }
}
