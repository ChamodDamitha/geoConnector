package com.chamod.rest;

import java.util.ArrayList;

/**
 * Created by chamod on 7/29/17.
 */
public class InMemoryGeoDetector implements GeoDetector{

    private CoordinateMatrix coordinateMatrix;

    public InMemoryGeoDetector() {
        coordinateMatrix = new CoordinateMatrix();
    }

    public double getPathDistance(double lat1, double long1, double lat2, double long2) {
        return 0;
    }

    public GeoCoordinate getCoordinate(int requestUserId, int responseUserId) {
        return coordinateMatrix.getCoordinates(requestUserId, responseUserId);
    }

    public void setCoordinate(int requestUserId, int responseUserId, double latitude, double longitude) {
        coordinateMatrix.addCoordinate(requestUserId, responseUserId, latitude, longitude);
    }
}
