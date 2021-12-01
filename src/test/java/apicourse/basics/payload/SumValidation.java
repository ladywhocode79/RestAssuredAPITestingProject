package apicourse.basics.payload;

import io.restassured.path.json.JsonPath;
import org.junit.Assert;
import org.junit.Test;

public class SumValidation {
    @Test
    public void sumOfCourse(){
        JsonPath jsonPath = ReusableMethods.rawToJson(Payload.createCourseLists());
        int countOfCourses = jsonPath.getInt("courses.size()");
        int sum =0;
        for (int i=0;i<countOfCourses;i++){
            int coursePrice =jsonPath.getInt("courses["+i+"].price");
            int copiesCount =jsonPath.getInt("courses["+i+"].copies");
            int amount = copiesCount * coursePrice;
            sum =sum +amount;

        }
        System.out.println(sum);
        int purchaseAmount = jsonPath.getInt("dashboard.purchaseAmount");
        Assert.assertEquals(purchaseAmount,sum);
    }
}
