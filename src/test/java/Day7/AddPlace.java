package Day7;

import Day7.Pojo.Entire;
import Day7.Pojo.Location;
import io.restassured.RestAssured;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class AddPlace  {
    public static void main(String[] args) {
        Entire e=new Entire();
        e.setName("Frontline house");
        e.setAccuracy(50);
        e.setAddress("29, side layout, cohen 09");
        e.setPhone_number("(+91) 983 893 3937");
        e.setWebsite("http://google.com");
        e.setLanguage("French-IN");

        List<String> type=new ArrayList<String>();
        type.add("shoe park");
        type.add("shop");
        e.setTypes(type);
        Location l=new Location();
        l.setLat(-38.383494);
        l.setLng(33.427362);

        RestAssured.baseURI="https://rahulshettyacademy.com";
        given().log().all().queryParam("key","qaclick123")
                .body(e)
                .when().post("/maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200);
    }
}
