package com.chamod.rest;


import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by chamod on 7/29/17.
 */

@Path("/geo")
public class GeoConnectorService {
    private GeoDetector geoDetector;

    int[] arr = new int[23];

    public GeoConnectorService() {
        this.geoDetector = new InMemoryGeoDetector();
    }

    @GET
    @Path("/globalKey")
    @Produces("text/json")
    public Response getGlobalKey(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId","cds");
        jsonObject.put("globalKey",23432);

        return Response.status(200).entity(jsonObject.toJSONString()).build();
    }


    @GET
    @Path("/secretKey")
    @Produces("text/json")
    public Response getSecretKey(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId",12);
        jsonObject.put("secretKey",43535345);

        return Response.status(200).entity(jsonObject.toJSONString()).build();
    }

    @GET
    @Path("/geoCoordinates")
    @Produces("text/json")
    public Response getGeoCoordinates(@QueryParam("requestUserId") int requestUserId,
                                      @QueryParam("respondUserId") int respondUserId){
        GeoCoordinate coordinate = geoDetector.getCoordinate(requestUserId, respondUserId);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", respondUserId);
        if(coordinate != null) {
            jsonObject.put("available", true);
            jsonObject.put("latitude", coordinate.getLatitude());
            jsonObject.put("longitude", coordinate.getLongitude());
            jsonObject.put("distance", geoDetector.getPathDistance(0, 0,
                    coordinate.getLatitude(), coordinate.getLongitude()));
            jsonObject.put("timestamp", coordinate.getTimestamp());
            jsonObject.put("arr[0]", arr[0]);
        }
        else{
            jsonObject.put("available", false);
        }

        return Response.status(200).entity(jsonObject.toJSONString()).build();
    }


    @POST
    @Path("/geoCoordinates")
    @Produces("text/json")
    public Response setCoordinates(@QueryParam("requestUserId") int requestUserId,
                                   @QueryParam("respondUserId") int respondUserId,
                                   @QueryParam("latitude") double latitude,
                                   @QueryParam("longitude") double longitude){

        geoDetector.setCoordinate(requestUserId, respondUserId, latitude, longitude);

        GeoCoordinate coordinate = geoDetector.getCoordinate(requestUserId, respondUserId);

        arr[0] = 234;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        jsonObject.put("latitude", coordinate.getLatitude());
        jsonObject.put("longitude", coordinate.getLongitude());
        jsonObject.put("arr[0]", arr[0]);


        return Response.status(200).entity(jsonObject.toJSONString()).build();
    }


}
