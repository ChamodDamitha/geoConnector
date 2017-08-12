package com.chamod.rest;

import netscape.javascript.JSObject;
import org.json.simple.JSONObject;

/**
 * Created by chamod on 8/12/17.
 */
public class User {
    private String email;
    private String name;

    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public JSONObject toJSON(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", email);
        jsonObject.put("name", name);
        return jsonObject;
    }
}
