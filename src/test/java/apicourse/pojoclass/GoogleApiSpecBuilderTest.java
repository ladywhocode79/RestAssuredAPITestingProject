package apicourse.pojoclass;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class GoogleApiSpecBuilderTest {
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

        //spec builder
        RequestSpecification req=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").
                addQueryParam("key","qaclick123").
                setContentType(ContentType.JSON).build();

        ResponseSpecification responseSpecification =new ResponseSpecBuilder().
                expectStatusCode(200).expectContentType(ContentType.JSON).build();

        RequestSpecification res =given().spec(req).
                body(place);
        String response =
                res.
                when().
                post("/maps/api/place/add/json").
                then().spec(responseSpecification).extract().response().asString();
        System.out.println(response);
    }
}
