package com.chamod.rest;


import org.json.simple.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created by chamod on 7/29/17.
 */

@Path("/geo")
public class GeoConnectorService {

    @GET
    @Produces("text/json")
    public Response getKey(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId","123");
        jsonObject.put("key","abcdef");

        return Response.status(200).entity(jsonObject.toJSONString()).build();
    }

}
