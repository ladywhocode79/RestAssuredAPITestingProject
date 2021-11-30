package apicourse.payload;

import io.restassured.path.json.JsonPath;

public class GetComplexJson {
    public static void main(String[] args) {
        //create jsonpath object , parse json string from payload function as string into rawToJson
        //method to convert string into Jsonpath
        JsonPath jsonPath = ReusableMethods.rawToJson(Payload.createCourseLists());
        //get number of courses where courses is an array and array.size() will return number of elements
        //in an array
        int countOfCourses = jsonPath.getInt("courses.size()");
        //1.Print no of courses returned by API..
        System.out.println("No of courses returned by API are: "+countOfCourses);
        //2.Print purchase amount
        int purchaseAmount = jsonPath.getInt("dashboard.purchaseAmount");
        System.out.println("Purchase amount is : "+purchaseAmount);
        //3.Print title of first course
        String titleOfFirstCourse = jsonPath.getString("courses[0].title");
        System.out.println("Title of first course is : "+titleOfFirstCourse);
        //Print all courses titles and respective prices
        String courseName;
        int coursePrice;
        for(int i=0;i<countOfCourses;i++){
            courseName=jsonPath.getString("courses["+i+"].title");
            System.out.println("Course name is "+courseName);
            coursePrice =jsonPath.getInt("courses["+i+"].price");
            System.out.println("Course price is "+coursePrice);

        }
        //4.Get number of copies sold for book named RPA,hence scan full array
        // and find course name as RPA and its respective price
        System.out.println("Number of copies sold for RPA");
        for(int i=0;i<countOfCourses;i++){
            courseName=jsonPath.getString("courses["+i+"].title");

            if(courseName.equalsIgnoreCase("RPA")){
                System.out.println("Number of copies sold of RPA : "+
                        jsonPath.getInt("courses["+i+"].copies") );
                break;
            }

        }
        //5.Verify if sum of all course prices matches with purchase amount

    }
}
