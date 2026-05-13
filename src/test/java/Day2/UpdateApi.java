package Day2;

import files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class UpdateApi {
    public static void main(String[] args) {
        RestAssured.baseURI="https://rahulshettyacademy.com";

        //post
String response= given().log().all().queryParam("key","qaclick123")
                .header("Content-Type","application/json")
                .body(Payload.AddPlace())
                .when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200)
                .body("scope",equalTo("APP"))
                .header("server","Apache/2.4.52 (Ubuntu)")
                //here only extract the json
                .extract().response().asString();

        System.out.println(response);
        JsonPath jp=new JsonPath(response);
        String placeid=jp.getString("place_id");
        System.out.println(placeid);

        //PUT - update address
        String updatedAdd="perala andhra pradesh";
        given().log().all().queryParam("key","qaclick123")
                .header("Content-Type","application/json")
                .body("{\n" +
                        "  \"place_id\": \""+placeid+",\n" +
                        "  \"address\": \""+updatedAdd+"\",\n" +
                        "  \"key\": \"qaclick123\"\n" +
                        "}")
                .when().put("maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200);

        //GET - to validate updated adress
        String response2=given().queryParam("key","qaclick123")
                .queryParam("place_id",placeid)
                .when().get("maps/api/place/get/json")
                .then().assertThat().statusCode(200).extract().response().asString();

        System.out.println("response2: "+response2);
        JsonPath jp1=new JsonPath(response2);
        String acAddress=jp1.getString("address");
        System.out.println(acAddress);

        Assert.assertEquals(acAddress,updatedAdd);


    }
}
