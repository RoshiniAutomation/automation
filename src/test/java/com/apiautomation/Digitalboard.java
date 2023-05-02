package com.apiautomation;

import com.github.wnameless.json.flattener.JsonFlattener;
import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.*;

public class Digitalboard {
    Object response = null;

    String resourceid = null;
    String tvtoken = null;

    String serialNumber=null;

    String tvName=null;

    Map<String, Object> fparams = new HashMap<>();
    Map<String, Object> qparams = new HashMap<>();
    JSONParser parser = new JSONParser();
    Object obj = parser.parse(new FileReader("testdata/testenv.json"));
    JSONObject jsonObject = (JSONObject)obj;
    Map<String, Object> stringObjectMap = JsonFlattener.flattenAsMap(((JSONObject) obj).toJSONString());

    String token = stringObjectMap.get("roshinifoodhubtest.token").toString();
    String siturl = stringObjectMap.get("api.url").toString();


    public Digitalboard() throws IOException, ParseException {
    }
    public static int getRandom(int var1, int var2) {
        if (var1 > var2) {
            System.out.println("Enter minimum number less than maximum number");
            var1 = var2 - 1;
        }
        boolean var3 = false;
        Random var4 = new Random();
        var2 = var2 - var1 + 1;
        int var5 = var4.nextInt(var2) + var1;
        System.out.println("number" + var5);
        return var5;

    }


    @Test(priority = 0)
    public void addtvlegacy() {

        try {
                serialNumber = "ob-ca-cc" + getRandom(111,999);
                tvName = "RoshiniFoodhub" + getRandom(111,999);
            // Get the body data which is in form-data in postman

               fparams.put("tv_name", tvName);
                fparams.put("tv_serialnumber", serialNumber);
                fparams.put("slide_duration", "10");
                fparams.put("tv_layout", "PORTRAIT");
                fparams.put("split_screen", "ENABLED");
                fparams.put("show_order_no", "YES");
                fparams.put("show_logo", "YES");
                fparams.put("show_door_delivery", "YES");
                fparams.put("type", "ORDER_VIEW_SCREEN");

                //getting the data from json file
                // file path is root directory path
                // Once after bringing the json convert into map by using json flattener for key value which is written globally


                // Get the body data which is in params in postman
                qparams.put("api_token", token);
                baseURI = ""+ siturl + "/television";
                response = given().formParams(fparams).
                        when().queryParams(qparams).
                        post(baseURI).
                        then().
                        extract().response().asString();
                System.out.println(response);
                Map<String, Object> map = JsonFlattener.flattenAsMap((response).toString());
                System.out.println(map);
                resourceid = map.get("resource_id").toString();
                System.out.println(resourceid);
                qparams.clear();
                fparams.clear();



            } catch (Exception e) {
                hardFail("Failed to add TV" + e);
            }
        }



    @Test(priority = 1)
        public void gettvtoken(){
        try {
            fparams.put("serial_number", serialNumber);
            baseURI = "" + siturl + "/television/token";
            response = given().formParams(fparams).when().post(baseURI).then().extract().response().asString();
            System.out.println(response);
            Map<String, Object> map = JsonFlattener.flattenAsMap((response).toString());
            System.out.println(response);
            tvtoken = map.get("api_token").toString();
            System.out.println("tvtoken is : " + tvtoken);
        }
        catch (Exception e) {
            hardFail("Failed to get TVTOKEN" + e);
        }

    }
    @Test(priority = 2)
    public void gettvboard() {
        try {

            qparams.put("api_token", tvtoken);
            baseURI = siturl + "/client/television/board";
            response = given().queryParams(qparams).
                    when().
                    get(baseURI).
                    then().extract().response().asPrettyString();
            System.out.println("before is " + response);
            Map<String, Object> map = JsonFlattener.flattenAsMap((response).toString());
            for (Map.Entry<String,Object> rest: map.entrySet()){
                System.out.println("Key : "+rest.getKey() +"  "+"Value : "+rest.getValue());
            }
            System.out.println(map);

        } catch (Exception e) {
            hardFail("Failed to getTV details" + e);
        }
    }





        private void hardFail(String s) {
            System.out.println(s);
    }


}