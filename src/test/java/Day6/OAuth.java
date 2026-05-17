package Day6;

import Day6.Pojo.EntireData;
import Day6.Pojo.Mobile;
import Day6.Pojo.WebAutomation;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OAuth {
    public static void main(String[] args) {
        RestAssured.baseURI="https://rahulshettyacademy.com";
        String res=given().log().all().multiPart("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .multiPart("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                .multiPart("grant_type","client_credentials")
                .multiPart("scope","trust")
                .when().post("/oauthapi/oauth2/resourceOwner/token")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath js=new JsonPath(res);
        String token=js.get("access_token");
        System.out.println("token: "+token);

        //use same token to get the book
        Day6.Pojo.EntireData ed =given().log().all().queryParam("access_token",token)
                .when().get("/oauthapi/getCourseDetails")
                .as(Day6.Pojo.EntireData.class);

        System.out.println(ed.getLinkedIn());
        System.out.println(ed.getInstructor());
        //
        System.out.println(ed.getCourses().getWebAutomation().get(0).getCourseTitle());
        //get the procator price
        List<WebAutomation> wa=ed.getCourses().getWebAutomation();
        for(WebAutomation w:wa){
            if(w.getCourseTitle().equalsIgnoreCase("Protractor")){
                System.out.println(w.getPrice());
            }
        }
        //get the mobile title
        List<Mobile> mb=ed.getCourses().getMobile();
        for(Mobile m:mb){
            System.out.println(m.getCourseTitle());
        }

        //sum all the prices in WebAutomation
        List<WebAutomation> web=ed.getCourses().getWebAutomation();
        int sum=0;
        for(WebAutomation w:web){
            String price=w.getPrice();
            sum=sum+Integer.parseInt(price);
        }
        System.out.println("sum: "+sum);



    }
}
