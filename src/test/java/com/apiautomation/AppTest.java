package com.apiautomation;


import com.github.wnameless.json.flattener.JsonFlattener;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class AppTest
{
   //@Test
    public void getitems() throws IOException, ParseException {

       // Specify the base URL to the RESTful web service
       RestAssured.baseURI="https://sit-api.t2scdn.com/items?api_token=0b44a18db4cc0896422fed6fa6871495";

       // Get the RequestSpecification of the request to be sent to the server.
       RequestSpecification httpRequest= given();
       Map<String,String> qparam = new HashMap<>();
       qparam.put("api_token","0b44a18db4cc0896422fed6fa6871495");
       httpRequest.queryParams(qparam);

       // specify the method type (GET) and the parameters if any.
       Response response=httpRequest.request(Method.GET);
       String responseBody=response.getBody().asString();
       System.out.println("Response is " +responseBody);

       Map<String, Object> map = JsonFlattener.flattenAsMap((responseBody));
       System.out.println(map);
   }
   //@Test
   public void createcart(){
       RestAssured.baseURI="https://sit-api.t2scdn.com";
       RequestSpecification httpRequest= given();
       Map<String,String> qparams = new HashMap<>();
       qparams.put("app_name","FOODHUB");
       httpRequest.queryParams(qparams);
       RequestSpecificationImpl request = null;
       request.header("Store","8051235");
       RequestSpecification header = request.header("Content-Type", "application/x-www-form-urlencoded");
       Response response = request.post("/consumer/cart");
       ResponseBody body = response.getBody();
       Map<String, Object> map = JsonFlattener.flattenAsMap(String.valueOf((response)));
       System.out.println(response.getStatusLine());
       System.out.println(body.asString());

   }
   @Test
   public void create(){


       baseURI = "https://sit-api.t2scdn.com/consumer/cart?app_name=FOODHUB";
       Object res = given().
               header("store", "8051235").
               header("Content-Type", "application/x-www-form-urlencoded").
               contentType(ContentType.TEXT).
                accept(ContentType.TEXT)
               .queryParam("online_offline","online")
               .when().
                post(baseURI)
               .then().
               extract().response().asString();
       System.out.println(res);
//       String jsonString = ((JSONObject) res).toJSONString();
//        Map<String, Object> map = JsonFlattener.flattenAsMap(jsonString);
//        System.out.println(map);

   }


}
