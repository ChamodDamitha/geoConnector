package com.chamod.rest;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * Created by chamod on 7/29/17.
 */

@Path("/geo")
public class GeoConnectorService {
    private GeoDetector geoDetector;

    public GeoConnectorService() {
        this.geoDetector = new DBGeoDetector();
    }


    @GET
    @Path("/geoCoordinates")
    @Produces("text/json")
    public Response getGeoCoordinates(@QueryParam("requestUserEmail") String requestUserEmail,
                                      @QueryParam("responseUserEmail") String responseUserEmail) {

        JSONObject jsonObject = new JSONObject();

        boolean areFirends = geoDetector.areFriends(requestUserEmail, responseUserEmail);
        if (!areFirends) {
            jsonObject.put("available", false);
            jsonObject.put("msg", "Requested User is not a friend...!");
        } else {
            GeoCoordinate coordinate = geoDetector.getCoordinate(responseUserEmail);

            jsonObject.put("email", responseUserEmail);
            if (coordinate != null) {
                jsonObject.put("available", true);
                jsonObject.put("latitude", coordinate.getLatitude());
                jsonObject.put("longitude", coordinate.getLongitude());
                jsonObject.put("distance", geoDetector.getPathDistance(0, 0,
                        coordinate.getLatitude(), coordinate.getLongitude()));
                jsonObject.put("timestamp", coordinate.getTimestamp());
            } else {
                jsonObject.put("available", false);
                jsonObject.put("msg", "Location details are not available...!");
            }

        }
        return Response.status(200).entity(jsonObject.toJSONString()).build();
    }

    @POST
    @Path("/geoCoordinates")
    @Produces("text/json")
    public Response setGeoCoordinates(@QueryParam("userEmail") String userEmail,
                                      @QueryParam("latitude") double latitude,
                                      @QueryParam("longitude") double longitude) {

        geoDetector.setCoordinate(userEmail, latitude, longitude);

        GeoCoordinate coordinate = geoDetector.getCoordinate(userEmail);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        jsonObject.put("latitude", coordinate.getLatitude());
        jsonObject.put("longitude", coordinate.getLongitude());

        return Response.status(200).entity(jsonObject.toJSONString()).build();
    }

    @POST
    @Path("/sendFriendRequest")
    @Produces("text/json")
    public Response sendFriendRequest(@QueryParam("requestUserEmail") String requestUserEmail,
                                      @QueryParam("responseUserEmail") String responseUserEmail) {

        JSONObject jsonObject = new JSONObject();
        boolean success = geoDetector.sendFriendRequest(requestUserEmail, responseUserEmail);
        if (success) {
            jsonObject.put("success", true);
            jsonObject.put("msg", "Friend request sent");
        } else {
            jsonObject.put("success", false);
            jsonObject.put("msg", "Friend request was not sent");
        }

        return Response.status(200).entity(jsonObject.toJSONString()).build();
    }

    @POST
    @Path("/acceptFriendRequest")
    @Produces("text/json")
    public Response acceptFriendRequest(@QueryParam("requestUserEmail") String requestUserEmail,
                                        @QueryParam("responseUserEmail") String responseUserEmail) {

        JSONObject jsonObject = new JSONObject();
        boolean success = geoDetector.acceptFriendRequest(requestUserEmail, responseUserEmail);
        if (success) {
            jsonObject.put("success", true);
            jsonObject.put("msg", "Friend request accepted");
        } else {
            jsonObject.put("success", false);
            jsonObject.put("msg", "Friend request was not accepted");
        }

        return Response.status(200).entity(jsonObject.toJSONString()).build();
    }

    @GET
    @Path("/friendRequests")
    @Produces("text/json")
    public Response getFriendRequests(@QueryParam("userEmail") String userEmail) {

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArrayFriends = new JSONArray();

        ArrayList<String> friendRequests = geoDetector.getFriendRequests(userEmail);
        for (String email : friendRequests) {
            jsonArrayFriends.add(email);
        }

        jsonObject.put("email", userEmail);
        jsonObject.put("friendRequests", jsonArrayFriends);

        return Response.status(200).entity(jsonObject.toJSONString()).build();
    }

    @GET
    @Path("/friends")
    @Produces("text/json")
    public Response getFriends(@QueryParam("userEmail") String userEmail) {

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArrayFriends = new JSONArray();

        ArrayList<User> friends = geoDetector.getFriends(userEmail);
        for (User u : friends) {
            jsonArrayFriends.add(u.toJSON());
        }

        jsonObject.put("email", userEmail);
        jsonObject.put("friends", jsonArrayFriends);

        return Response.status(200).entity(jsonObject.toJSONString()).build();
    }

    @POST
    @Path("/users")
    @Produces("text/json")
    public Response makeUser(@QueryParam("email") String email, @QueryParam("name") String name) {

        JSONObject jsonObject = new JSONObject();

        boolean success = geoDetector.makeUser(email, name);

        if (success) {
            jsonObject.put("success", true);
            jsonObject.put("msg", "User was created");
        } else {
            jsonObject.put("success", false);
            jsonObject.put("msg", "User was not created");

        }
        return Response.status(200).entity(jsonObject.toJSONString()).build();
    }


}
