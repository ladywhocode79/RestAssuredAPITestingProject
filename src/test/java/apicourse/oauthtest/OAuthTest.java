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
/*        System.setProperty("webdriver.chrome.driver","src/test/driver/chromedriver");
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
        //get current browser url
        String url = chromeDriver.getCurrentUrl();*/
        String url="https://rahulshettyacademy.com/getCourse.php?code=4%2F0AX4XfWhZl6skntKpLPzyYNvytA61ySB5WR8Li1C" +
                "7KS2AeIk-Sh8AC_UB3GsgjJS54drO2A&scope=email+https%3A%2F%2F" +
                "www.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none#";
        //split the url to get code,this will return an array,where we are storing second half after code ie 1st index
        String partialCode=url.split("code=")[1];
        //again split partial code till &scope at 0th index
        String authCode = partialCode.split("&scope")[0];
        System.out.println("authcode=="+authCode);


        //get access token
       String accessTokenResponse = given().urlEncodingEnabled(false). //to disable any special character encoding
                queryParams("code",authCode).
                queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com").
                queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W").
               queryParams("grant_type", "authorization_code").
                queryParams("state", "verifyfjdss").
                queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8").
                queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php").
                when().log().all().
                post("https://www.googleapis.com/oauth2/v4/token").
                asString();
        JsonPath accessTokenJsonPath = ReusableMethods.rawToJson(accessTokenResponse);
        String accessToken = accessTokenJsonPath.getString("access_token");
             //     queryParams("state", "verifyfjdss").
              //  queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")


        //hit the url to get list of course using acess token generated above
       String response = given().queryParam("access_token",accessToken).
                when().
                get("https://rahulshettyacademy.com/getCourse.php").
                asString();
        System.out.println("getcourse response= "+response);
    }
}
