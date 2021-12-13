package apicourse.DynamicJson;

import apicourse.basics.payload.Payload;
import apicourse.basics.payload.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DynamicJson {
    @Test(dataProvider = "BooksData")
    public void addBook(String isbn,String aisle){
        RestAssured.baseURI= "http://216.10.245.166";
        /*String isbn = "rdsfk";
        String aisle = "2091";*/
      String response = given().header("Content-Type","application/json").
                body(Payload.addBooksPayload(isbn,aisle)).
                when().
                post(" /Library/Addbook.php").
                then().assertThat().statusCode(200).log().all().body("ID",equalTo(isbn+aisle)).
                extract().response().asString();
        //converts string into json
        JsonPath jsonPath = ReusableMethods.rawToJson(response);
        String msg = jsonPath.getString("Msg");
        System.out.println(msg);
        Assert.assertEquals(msg,"successfully added");
        String id = jsonPath.get("ID");
        System.out.println(id);
        Assert.assertEquals(id,isbn+aisle);

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

    }
    @DataProvider(name = "BooksData")
    public Object[][] getData(){
    //return array of arrays mutlidimentional array
    return new Object[][]{{"rtnv","203"},{"rtns","203"},{"rtnf","203"},{"rtng","203"},{"rtns","203"},};
    }
    public static String GenerateStringFromResource(String path) throws IOException {
         return new String(Files.readAllBytes(Paths.get(path)));}
    @Test
    public void addBooks() throws IOException {
        RestAssured.baseURI= "http://216.10.245.166";
        /*String isbn = "rdsfk";
        String aisle = "2091";*/
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

    }
}
