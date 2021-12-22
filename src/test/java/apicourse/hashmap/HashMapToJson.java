package apicourse.hashmap;

import apicourse.basics.payload.Payload;
import apicourse.basics.payload.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class HashMapToJson {
    @Test
    public void addBook(){
        HashMap<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("name","RestAssured");
        jsonAsMap.put("isbn","RDFG");
        jsonAsMap.put("aisle","100");
        jsonAsMap.put("author","Sonal Solanki");
        /*Nested Json with Nested HAshmap
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("lat","23");
        map2.put("long","30");
        * jsonAsMap.put("location",map2);
        *  */

        RestAssured.baseURI= "http://216.10.245.166";

      String response = given().header("Content-Type","application/json").
                body(jsonAsMap).
                when().
                post(" /Library/Addbook.php").
                then().assertThat().statusCode(200).log().all().
                extract().response().asString();
        //converts string into json
       JsonPath jsonPath = ReusableMethods.rawToJson(response);
        String msg = jsonPath.getString("Msg");
        System.out.println(msg);
        Assert.assertEquals(msg,"successfully added");
        String id = jsonPath.get("ID");
        System.out.println(id);
        //Assert.assertEquals(id,isbn+aisle);*/

    /*    //delete book
       given().
                body("{\n" +
                        " \n" +
                        "\"ID\" : \""+id+"\"\n" +
                        " \n" +
                        "}\n").
                when().
                delete("/Library/DeleteBook.php").
                then().assertThat().statusCode(200).log().all().body("msg",
                       equalTo("book is successfully deleted"));
        //converts string into json*/

    }

  /*  @Test
    public void addBooks() throws IOException {
        RestAssured.baseURI= "http://216.10.245.166";
        *//*String isbn = "rdsfk";
        String aisle = "2091";*//*
       String response = given().header("Content-Type","application/json").
                    body(GenerateStringFromResource("src/test/java/apicourse/" +
                            "basics/payload/addBoooks.json")).
                    when().
                    post(" /Library/Addbook.php").
                    then().assertThat().statusCode(200).log().all().
                    extract().response().asString();
            System.out.println("response==="+response);


         //converts string into json
        JsonPath jsonPath = ReusableMethods.rawToJson(response);
        String msg = jsonPath.getString("Msg");
        System.out.println(msg);
        Assert.assertEquals(msg,"successfully added");
        String id = jsonPath.get("ID");
        System.out.println(id);
      //  Assert.assertEquals(id,isbn+aisle);

        //delete book
        given().
                body("{\n" +
                        " \n" +
                        "\"ID\" : \""+id+"\"\n" +
                        " \n" +
                        "}\n").
                when().
                delete("/Library/DeleteBook.php").
                then().assertThat().statusCode(200).log().all().body("msg",
                        equalTo("book is successfully deleted"));
        //converts string into json

    }*/
}
