package apicourse.oauthtest;

import apicourse.basics.payload.ReusableMethods;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class OAuthTest {
    @Test
    public void authTest() throws InterruptedException {
        //get auth code using selenium web browser
        System.setProperty("webdriver.chrome.driver","src/test/driver/chromedriver");
        WebDriver chromeDriver = new ChromeDriver();
        String email = "sonaltestcode28@gmail.com";
        String password = "testcode28";
        //to hit url
        chromeDriver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com" +
                "&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
        chromeDriver.findElement(By.cssSelector("input[type=email]")).sendKeys(email);
        chromeDriver.findElement(By.cssSelector("input[type=password]")).sendKeys(password);
        chromeDriver.findElement(By.cssSelector("input[type=password]")).sendKeys(Keys.ENTER);
        Thread.sleep(4000);


        //get access token
       String accessTokenResponse = given().
                queryParams("code","").
                queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com").
                queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W").
                queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php").
                queryParams("grant_type", "authorization_code").
                when().log().all().
                post("https://www.googleapis.com/oauth2/v4/token").
                asString();
        JsonPath accessTokenJsonPath = ReusableMethods.rawToJson(accessTokenResponse);
        String accessToken = accessTokenJsonPath.getString("acess_token");
             //     queryParams("state", "verifyfjdss").
              //  queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")


        //hit the url to get list of course using acess token generated above
       String response = given().queryParam("access_token",accessToken).
                when().
                get("https://rahulshettyacademy.com/getCourse.php").
                asString();
        System.out.println(response);
    }
}
