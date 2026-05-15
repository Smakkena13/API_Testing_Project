package Day4;

import files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class Addbook {

    ArrayList<String> ids=new ArrayList<String>();
    @Test(priority=1,dataProvider="dp")
    public void testaddBook(String isbn,String ais){
        RestAssured.baseURI="http://216.10.245.166";
        String response=given().log().all().header("content-type","application/json")
                .body(Payload.addbook(isbn,ais))
                .when().post("Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js=new JsonPath(response);
        String id=js.get("ID");

        ids.add(id);
        System.out.println("Added Book ID: " + id);

    }

    @Test(priority = 2)
    public void testdeleteBook(){
        for(String id:ids){
            RestAssured.baseURI="http://216.10.245.166";
            given().log().all().header("Content-Type","application/json")
                    .body(Payload.deletebook(id))
                    .when().delete("Library/DeleteBook.php")
                    .then().log().all().statusCode(200);
            System.out.println("book deleted with ID: "+id);
        }
    }

    @DataProvider(name="dp")
    public Object[][] getData(){
        Object data[][]={
                {"aooo","111"},
                {"booo","222"},
//                {"ccc","333"},
//                {"ddd","444"}
        };
        return data;
    }
}
