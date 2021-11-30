package apicourse;

/*1.Create place using create payload and extract response in a string.We use POST method here
 * 2.Take out place_id from string response using JsonPath class which parses the JSON string and returns
 * the required place_id
 * 3.Use this place_id to update address of place using put method.
 * 4.To verify that address is updated use placeid and fetch data using GET method */

import apicourse.payload.ReusableMethods;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static apicourse.payload.Payload.payloadForCreateUser;

public class CreateAndVerifyPlaceId {
  @Test
  public void createAndVerifyPlaceId(){
    //base url address of api server
    baseURI ="https://rahulshettyacademy.com/";
  /* String response = given().log().all().queryParam("key","qaclick123").
            header("Content-Type","application/json").
            body(payloadForCreateUser()).
            when().post("/maps/api/place/add/json").
            then().log().all().assertThat().statusCode(200).
    body("scope",equalTo("APP")).
    extract().response().asString();*/
    String key = "qaclick123";
    String postRequestResponse = given().log().all().queryParam("key",key).
            header("Content-Type","application/json").
            body(payloadForCreateUser()).
            when().post("/maps/api/place/add/json").
            then().assertThat().statusCode(200).
            body("scope",equalTo("APP")).
            extract().response().asString();
    //took response as string and now extract response value place_id
    //System.out.printf(postRequestResponse);
    JsonPath jsonPath = ReusableMethods.rawToJson(postRequestResponse); //parse Json
    String placeId = jsonPath.get("place_id");
    System.out.println("place id: "+placeId);

    //to update address of place id received above.
    String newAddress = "76 winter walk, USA";
    given().log().all().queryParam("key",key).
            body("{\n" +
                    "\"place_id\":\""+placeId+"\",\n" +
                    "\"address\":\""+newAddress+"\",\n" +
                    "\"key\":\""+key+"\"\n" +
                    "}").log().all().
            when().put("/maps/api/place/update/json").
            then().assertThat().statusCode(200).
            body("msg",equalTo("Address successfully updated"));

    //to verify whether address is updated
    String putRequestResponse=given().log().all().queryParam("key",key).
            queryParam("place_id",placeId).log().all().
            when().get("maps/api/place/get/json").
            then().assertThat().log().all().statusCode(200).extract().response().asString();


    JsonPath updatedJson=ReusableMethods.rawToJson(putRequestResponse);
    String actualAddress = updatedJson.getString("address");
    System.out.println(actualAddress);
    Assert.assertEquals(newAddress,actualAddress);



  }

}
