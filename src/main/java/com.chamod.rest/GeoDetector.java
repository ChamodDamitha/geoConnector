package com.chamod.rest;

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
     * @param userEmail the user email of the user who is providing the coordinates
     * @return an arrayList containing the latitude and longitude of the requested point
     */
    GeoCoordinate getCoordinate(String userEmail);

    /**
     * provide the coordinates by a specific user for a requested user
     * @param userEmail  the user email of the user who is providing the coordinates
     * @param latitude
     * @param longitude
     */
    void setCoordinate(String userEmail, double latitude, double longitude);

    /**
     * Check whether the requested user is a friend of the other user
     * @param requestUserEmail the user email of the user who is requesting for coordinates
     * @param responseUserEmail the user email of the user who is providing the coordinates
     * @return
     */
    boolean areFriends(String requestUserEmail, String responseUserEmail);

    /**
     * Make two users as friends
     * @param requestUserEmail the user email of the user who is requesting for being friends
     * @param responseUserEmail the user email of the user who is responding for being friends
     * @return
     */
    boolean makeFriends(String requestUserEmail, String responseUserEmail);

    /**
     * Remove friends
     * @param requestUserEmail the user email of the user who is requesting for removing friendship
     * @param responseUserEmail the user email of the user who is responding for removing friendship
     * @return
     */
    boolean removeFriends(String requestUserEmail, String responseUserEmail);

    boolean sendFriendRequest(String requestUserEmail, String responseUserEmail);
    boolean acceptFriendRequest(String requestUserEmail, String responseUserEmail);

    ArrayList<String> getFriendRequests(String userEmail);

    ArrayList<User> getFriends(String userEmail);

    boolean makeUser(String email, String name);

}
