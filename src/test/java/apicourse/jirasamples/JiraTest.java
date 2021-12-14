package apicourse.jirasamples;

import apicourse.basics.payload.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.io.File;

import static io.restassured.RestAssured.given;

public class JiraTest {

    public void loginAndAddComment(){
    RestAssured.baseURI = "https://localhost:8080";
    String username = "myuser";
    String password = "mypassword";

    //use Session Filter class objects
        SessionFilter sessionFilter = new SessionFilter();
    //login to get session id and then comment
       String loginResponse = given(). header("Content-Type","application/json").
                body("'{ \"username\": \""+username+"\", \"password\": \""+password+"\" }'").log().all().
               filter(sessionFilter).
                when().
                post("/rest/auth/1/session").then().extract().response().asString();
        String projectKey = "10101";
       /* JsonPath loginDetailsJsonPath = ReusableMethods.rawToJson(loginResponse);
        String sessionId = loginDetailsJsonPath.getString("session.value");
        given().pathParam("key",projectKey).log().all().
                header("Content-Type","application/json").
                header("JSESSIONID",sessionId).
                body("{\n" +
                        "    \"body\": \"This is a comment that only administrators can see.\",\n" +
                        "    \"visibility\": {\n" +
                        "        \"type\": \"role\",\n" +
                        "        \"value\": \"Administrators\"\n" +
                        "    }\n" +
                        "}").
                when().post("rest/api/2/issue/{key}/comment").then().assertThat().statusCode(201);*/
    //add comment
        String expectedCommentMessage = "This is a comment ,my first comment.";
   String commentResponse =given().pathParam("key",projectKey).log().all().
            header("Content-Type","application/json").
            body("{\n" +
                    "    \"body\": \""+expectedCommentMessage+"\",\n" +
                    "    \"visibility\": {\n" +
                    "        \"type\": \"role\",\n" +
                    "        \"value\": \"Administrators\"\n" +
                    "    }\n" +
                    "}").filter(sessionFilter).
            when().post("rest/api/2/issue/{key}/comment").then().log().all().assertThat().
           statusCode(201).extract().response().asString();

   JsonPath commentResponseJsonPath = ReusableMethods.rawToJson(commentResponse);
   String commentId = commentResponseJsonPath.getString("id");

   //add attachment
        given().header("X-Atlassian-Token","no-check").filter(sessionFilter)
                .header("Content-Type","multipart/form-data")
                        .pathParam("key",projectKey).multiPart("file",
                        new File("src/test/java/apicourse/jirasamples/attachmenttest.txt")).
                    when().
                    post("/rest/api/2/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);

        //get issue and get comment id with respective comments added above..here we are using query params and path params together
        String issuesResponse = given().filter(sessionFilter).pathParam("key",projectKey).
                queryParam("fields","comment"). //with query params we are restricting our output to few fields..
                when().
                get("/rest/api/2/issue/{key}").
                then().log().all().extract().response().asString();
        System.out.println("issue Response"+issuesResponse);
        JsonPath issueResponseJsonPath = ReusableMethods.rawToJson(issuesResponse);
        int countOfArrayOfComments = issueResponseJsonPath.getInt("fields.comment.comments.size()");
       String actualCommentMessage=null;
        for(int i=0;i<countOfArrayOfComments;i++) {
           if(commentId.equals(issueResponseJsonPath.get("fields.comment.comments["+i+"].id").toString())){
               actualCommentMessage = issueResponseJsonPath.get("fields.comment.comments["+i+"].body").toString();
               System.out.println("Actual Comment Message"+actualCommentMessage);
            };
        }
        Assert.assertEquals(actualCommentMessage,expectedCommentMessage);

    }
}
