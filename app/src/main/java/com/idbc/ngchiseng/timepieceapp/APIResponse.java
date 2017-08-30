package com.idbc.ngchiseng.timepieceapp;

import org.json.JSONArray;
import org.json.JSONObject;

/*
 @reference[https://github.com/sahidr/CanopyVerde/blob/API/app/src/main/java/com/idbcgroup/canopyverde/APIResponse.java]
 */
public class APIResponse {
    int status;
    JSONObject body;
    JSONArray bodyArray;

    public APIResponse(int status) {
        this.status = status;
    }

    public APIResponse(JSONObject body, int status) {
        this.body = body;
        this.status = status;
    }
    public APIResponse(JSONArray body, int status) {
        this.bodyArray = body;
        this.status = status;
    }
    public JSONObject getBody() {
        return body;
    }

    public JSONArray getBodyArray() {
        return bodyArray;
    }

    public void setBody(JSONObject body) {
        this.body = body;
    }

    public void setBody(JSONArray body) {
        this.bodyArray = body;
    }

    public int getStatus() {
        return status;
    }

}
