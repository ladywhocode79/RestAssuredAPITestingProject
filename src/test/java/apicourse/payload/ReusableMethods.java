package apicourse.payload;

import io.restassured.path.json.JsonPath;

public class ReusableMethods {
    public static JsonPath rawToJson(String response){
        JsonPath updatedJson = new JsonPath(response);
        return updatedJson;
    }
}
