package Day6;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

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
        given().log().all().queryParam("access_token",token)
                .when().get("/oauthapi/getCourseDetails")
                .then().log().all().assertThat().statusCode(401);
    }
}
