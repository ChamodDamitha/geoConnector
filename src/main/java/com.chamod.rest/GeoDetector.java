package com.chamod.rest;

import org.json.simple.JSONObject;

import java.util.ArrayList;

/**
 * Created by chamod on 7/29/17.
 */
public interface GeoDetector {
    /**
     * calculate path distance between 2 points on earth
     * @param lat1 latitude of 1st point
     * @param long1 longitude of 1st point
     * @param lat2 latitude of 2nd point
     * @param long2 longitude of 2nd point
     * @return distance in a double
     */
    double getPathDistance(double lat1, double long1, double lat2, double long2);

    /**
     * Find the geo coordinate of a user
     * @param requestUserId the user id of the user who is requesting for coordinates
     * @param responseUserId the user id of the user who is providing the coordinates
     * @return an arrayList containing the latitude and longitude of the requested point
     */
    GeoCoordinate getCoordinate(int requestUserId, int responseUserId);

    /**
     * provide the coordinates by a specific user for a requested user
     * @param requestUserId the user id of the user who is requesting for coordinates
     * @param responseUserId  the user id of the user who is providing the coordinates
     * @param latitude
     * @param longitude
     */
    void setCoordinate(int requestUserId, int responseUserId, double latitude, double longitude);
}
