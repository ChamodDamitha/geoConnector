package com.chamod.rest;

/**
 * Created by chamod on 7/29/17.
 */
public class GeoCoordinate {
    private double latitude;
    private double longitude;
    private String timestamp;

    public GeoCoordinate(double latitude, double longitude, String timestamp) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
