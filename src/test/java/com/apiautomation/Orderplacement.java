package com.apiautomation;


import com.github.wnameless.json.flattener.JsonFlattener;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class Orderplacement
{
    String resourceid=null;
    String itemid=null;

    @Test
    public void placeorders() throws IOException, ParseException {
        for(int i=0;i<=0;i++){
            createcart();
            getitems();
            additems();
            updatecart();
            confirmcart();
            deleteorder();
        }
    }
   @Test(priority = 1)
    public void createcart() {
       JSONObject request = new JSONObject();
       request.put("online_offline", "online");
       baseURI = "https://sit-api.t2scdn.com/consumer/cart?app_name=FOODHUB";
       Object response = given().
               headers("Content-Type", "application/json").
               header("store", "8051235").
               contentType(ContentType.JSON).
               accept(ContentType.JSON).
               body(request.toJSONString()).
               when().
               post(baseURI).
               then().
               extract().response().asString();
       System.out.println(response);
       Map<String, Object> map = JsonFlattener.flattenAsMap((response).toString());
       System.out.println(map);
        resourceid = map.get("resource_id").toString();
       System.out.println(resourceid);
   }

   @Test(priority = 2)
   public void getitems()  {

       // Specify the base URL to the RESTful web service
       baseURI="https://sit-api.t2scdn.com/items?api_token=0b44a18db4cc0896422fed6fa6871495";

       // Get the RequestSpecification of the request to be sent to the server.
       RequestSpecification httpRequest= given();
       Map<String,String> qparam = new HashMap<>();
       qparam.put("api_token","0b44a18db4cc0896422fed6fa6871495");
       httpRequest.queryParams(qparam);

       // specify the method type (GET) and the parameters if any.
       Response response=httpRequest.request(Method.GET);
       String responseBody=response.getBody().asString();
       //System.out.println("Response is " +responseBody);

       //Map<String, Object> map = JsonFlattener.flattenAsMap((responseBody));
       //System.out.println(map);
       JsonElement jsonData = new JsonParser().parse(responseBody);
       JsonArray dataArray = jsonData.getAsJsonObject().get("data").getAsJsonArray();
       int id = (int) (Math.random() * dataArray.size());
       JsonObject name = dataArray.get(id).getAsJsonObject();
        itemid = name.get("id").getAsString();
       System.out.println("item: "+itemid);

       //itemid = map.get("data[0].id").toString();
       //System.out.println("the item id is :" +itemid);

   }

   @Test(priority = 3)
   public void additems(){
       baseURI="https://sit-api.t2scdn.com/consumer/cart/"+resourceid+"/item/"+itemid+"?app_name=FOODHUB";
       Object response=given().
               headers("Content-Type", "application/json").
               header("store", "8051235").
               contentType(ContentType.JSON).
               accept(ContentType.JSON).
               when().
               post(baseURI).
               then().
               extract().response().asString();
       System.out.println(response);

   }

      @Test(priority = 4)
       public void updatecart(){
       JSONObject request = new JSONObject();
       request.put("email", "roshiniautomation@gmail.com");
       request.put("password","Roshiniautomation@123");
       request.put("online_offline","online");
       request.put("host","sit-webrosh.t2scdn.com");
       request.put("customers_id","507127");
       request.put("address","123 nhg, jhj AA1 1AA");
       request.put("houseno","236");
       request.put("flat","201");
       request.put("address1","Victoria Park Road");
       request.put("address2","Tunstall");
       request.put("postcode","ST6 1DT");
       request.put("phone","07380308580");
       request.put("sending","to");
       request.put("firstname","Roshini");
       request.put("lastname","automation");
       request.put("order_source_id","3");
       request.put("check_validation","true");
       request.put("payment","0");
       baseURI = "https://sit-api.t2scdn.com/consumer/cart/"+resourceid+"?app_name=FOODHUB";
       Object response = given().
               headers("Content-Type", "application/json").
               header("store", "8051235").
               contentType(ContentType.JSON).
               accept(ContentType.JSON).
               body(request.toJSONString()).
               when().
               put(baseURI).
               then().
               extract().response().asString();
       System.out.println(response);

   }
   @Test(priority = 5)
   public void confirmcart(){

       JSONObject request = new JSONObject();
       request.put("online_offline", "online");
       baseURI="https://sit-api.t2scdn.com/consumer/cart/"+resourceid+"/confirm?app_name=FOODHUB";
       Object response = given().
               headers("Content-Type", "application/json").
               header("store", "8051235").
               contentType(ContentType.JSON).
               accept(ContentType.JSON).
               body(request.toJSONString()).
               when().
               put(baseURI).
               then().
               extract().response().asString();
       System.out.println(response);

   }

   @Test
   public void deleteorder(){
        baseURI="https://sit-api.t2scdn.com/orders/"+resourceid+"?api_token=0b44a18db4cc0896422fed6fa6871495";
        Object response = given().
                headers("Content-Type", "application/json").
                when().
                delete(baseURI).
                then().
                extract().response().asString();
                System.out.println(response);


   }

}



