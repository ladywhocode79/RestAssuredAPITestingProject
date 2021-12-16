package apicourse.pojoclass;

import io.restassured.RestAssured;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class GoogleApiSerialization {
    public static void main(String[] args) {
        AddPlace place = new AddPlace();
        place.setAccuracy(50);
        place.setName("Frontline house");
        place.setPhone_number("(+91) 983 893 3937");
        place.setAddress("29, side layout, cohen 09");
        place.setWebsite("http://google.com");
        place.setLanguage("French-IN");
        List<String> types = new ArrayList<String>();
        types.add("shoe park");
        types.add("shop");
        place.setTypes(types);
        Location location = new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);
        place.setLocation(location);

        RestAssured.baseURI ="https://rahulshettyacademy.com";
        String response =given().log().all().queryParams("key","qaclick123").
                body(place).
                when().
                post("/maps/api/place/add/json").
                then().assertThat().statusCode(200).extract().response().asString();
        System.out.println(response);
    }
}
