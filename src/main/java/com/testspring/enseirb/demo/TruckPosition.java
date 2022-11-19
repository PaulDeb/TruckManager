package com.testspring.enseirb.demo;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TruckPosition {
    private int truckId;
    private Instant ts;
    private Position position;

    @JsonCreator
    public TruckPosition(
        /*
         * Message format:
         *  {
         *      "truckId": 1,
         *      "ts": 123456789,
         *      "position": {
         *          "latitude": 48.123,
         *          "longitude": -1.123
         *      }
         *  }
         */
        @JsonProperty("truckId") int truckId,
        @JsonProperty("ts") Instant ts,
        @JsonProperty("position") Position position
    ) {
        this.truckId = truckId;
        this.ts = ts;
        this.position = position;
    }

    public int getTruckId() {
        return truckId;
    }

    public void setTruckId(int truckId) {
        this.truckId = truckId;
    }

    public Instant getTs() {
        return ts;
    }

    public void setTs(Instant ts) {
        this.ts = ts;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "TruckPosition{" +
                "truckId:'" + truckId + '\'' +
                ", ts:" + ts +
                ", position: { latitude:" + position.getLatitude() + ", longitude:" + position.getLongitude() + "}" +
                '}';
    }

    public String toJson() {
        return "{" +
                "\"truckId\":\"" + truckId + "\"," +
                "\"ts\":" + ts.toEpochMilli() + "," +
                "\"position\": { \"latitude\":" + position.getLatitude() + ", \"longitude\":" + position.getLongitude() + "}" +
                '}';
    }
}