package com.example.projectactivity;

import org.json.JSONException;

public interface RequestHandler {
    public void processResponse(String response) throws JSONException;
}
