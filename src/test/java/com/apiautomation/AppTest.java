package com.apiautomation;


import com.github.wnameless.json.flattener.JsonFlattener;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
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

public class AppTest
{
   @Test
    public void getitems() throws IOException, ParseException {

       // Specify the base URL to the RESTful web service
       RestAssured.baseURI="https://sit-api.t2scdn.com/items?api_token=0b44a18db4cc0896422fed6fa6871495";

       // Get the RequestSpecification of the request to be sent to the server.
       RequestSpecification httpRequest=RestAssured.given();
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
}
