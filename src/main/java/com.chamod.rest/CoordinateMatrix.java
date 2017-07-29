package com.chamod.rest;

import java.util.Date;

/**
 * Created by chamod on 7/29/17.
 */
public class CoordinateMatrix {
    private final int MAX_NO_OF_USERS = 100;

    private double[][] latitudes;
    private double[][] longitudes;
    private String[][] timestamps;

    public CoordinateMatrix() {
        latitudes = new double[MAX_NO_OF_USERS][MAX_NO_OF_USERS];
        longitudes = new double[MAX_NO_OF_USERS][MAX_NO_OF_USERS];
        timestamps = new String[MAX_NO_OF_USERS][MAX_NO_OF_USERS];
    }

    public void addCoordinate(int requestUserId, int respondUserId, double latitude, double longitude){
        latitudes[requestUserId][respondUserId] = latitude;
        longitudes[requestUserId][respondUserId] = longitude;
        timestamps[requestUserId][respondUserId] = new Date().toString();
    }

    public GeoCoordinate getCoordinates(int requestUserId, int respondUserId){
        return new GeoCoordinate(latitudes[requestUserId][respondUserId], longitudes[requestUserId][respondUserId],
                timestamps[requestUserId][respondUserId]);
    }
}
