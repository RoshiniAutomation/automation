package com.apiautomation;

import com.github.wnameless.json.flattener.JsonFlattener;
import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class Digitalboard {
    @Test
    public void addtvlegacy() {
            Map<String, Object> fparams = new HashMap<>();
            Object response = null;
            try {
                fparams.put("tv_name", "RoshiniFoodhubpe");
                fparams.put("tv_serialnumber", "00-0B-CA-56-8A-iauto20");
                fparams.put("slide_duration", "10");
                fparams.put("tv_layout", "PORTRAIT");
                fparams.put("split_screen", "ENABLED");
                fparams.put("show_order_no", "YES");
                fparams.put("show_logo", "YES");
                fparams.put("show_door_delivery", "YES");
                fparams.put("type", "ORDER_VIEW_SCREEN");
                Map<String, Object> qparams = new HashMap<>();
                qparams.put("api_token", "0b44a18db4cc0896422fed6fa6871495");
                baseURI = "https://sit-api.t2scdn.com/television";
                response = given().formParams(fparams).
                        when().queryParams(qparams).
                        post(baseURI).
                        then().
                        extract().response().asString();
                System.out.println(response);
                Map<String, Object> map = JsonFlattener.flattenAsMap((response).toString());


            } catch (Exception e) {
                hardFail("Failed to add TV" + e);
            }
        }

        private void hardFail(String s) {
    }


}


