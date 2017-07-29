package com.chamod.rest;


import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * Created by chamod on 7/29/17.
 */

@Path("/geo")
public class GeoConnectorService {
    private GeoDetector geoDetector;

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
    public Response getGeoCoordinates(){
        GeoCoordinate coordinate = geoDetector.getCoordinate(12, 23);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", 23);
        if(coordinate != null) {
            jsonObject.put("available", true);
            jsonObject.put("latitude", coordinate.getLatitude());
            jsonObject.put("longitude", coordinate.getLongitude());
            jsonObject.put("distance", geoDetector.getPathDistance(0, 0,
                    coordinate.getLatitude(), coordinate.getLongitude()));
            jsonObject.put("timestamp", coordinate.getTimestamp());
        }
        else{
            jsonObject.put("available", false);
        }

        return Response.status(200).entity(jsonObject.toJSONString()).build();
    }


    @POST
    @Path("/geoCoordinates")
    public Response setCoordinates(){

        return Response.status(200).build();
    }


}
