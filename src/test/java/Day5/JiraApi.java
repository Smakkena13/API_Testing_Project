package Day5;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.io.File;

import static io.restassured.RestAssured.*;



public class JiraApi {
    public static void main(String[] args) {
        RestAssured.baseURI="https://suvarna-kumari.atlassian.net";
        String res=given().log().all().header("Content-Type","application/json")
                .header("Authorization","Basic MTlsMTFhMDUwN0BnbWFpbC5jb206QVRBVFQzeEZmR0YwQW9tUjhSY3hDSnlnMjl0Wll4UlpGRlR2dlQ1djlEaHlDOXhDSFZ2U2JYZWlCcldnWjhRSGQ2TkU3MmFvbFdHYmlxZW11aGJGaW50SUdESWtQa19MRlFWNGhHYktwc1ZhZWR2SDN3c21UMmtHMUo0b0UtbXlyN3M0Y3lvTkM5ZE95M29wc0VtRy1GNEd1S0hfeUtKdGlKYzdUdmQ0ck1zQjFmaEZwVEZ2MEw4PTIxQjYxN0FB")
                .body("{\n" +
                        "    \"fields\": {\n" +
                        "       \"project\":\n" +
                        "       {\n" +
                        "          \"key\": \"SCRUM\"\n" +
                        "       },\n" +
                        "       \"summary\": \"dropdown button not working-automation\",\n" +
                        "       \"issuetype\": {\n" +
                        "          \"name\": \"Bug\"\n" +
                        "       }\n" +
                        "   }\n" +
                        "}")
                .when().post("/rest/api/3/issue")
                .then().log().all().statusCode(201).extract().response().asString();

        JsonPath jp=new JsonPath(res);
        String id=jp.get("id");
        System.out.println("id: "+id);

        //Adding attachment
        given().pathParam("key",id)
                .header("X-Atlassian-Token","no-check")
                .header("Authorization","Basic MTlsMTFhMDUwN0BnbWFpbC5jb206QVRBVFQzeEZmR0YwQW9tUjhSY3hDSnlnMjl0Wll4UlpGRlR2dlQ1djlEaHlDOXhDSFZ2U2JYZWlCcldnWjhRSGQ2TkU3MmFvbFdHYmlxZW11aGJGaW50SUdESWtQa19MRlFWNGhHYktwc1ZhZWR2SDN3c21UMmtHMUo0b0UtbXlyN3M0Y3lvTkM5ZE95M29wc0VtRy1GNEd1S0hfeUtKdGlKYzdUdmQ0ck1zQjFmaEZwVEZ2MEw4PTIxQjYxN0FB")
                .multiPart("file",new File("C:\\Users\\Happy\\OneDrive\\Pictures\\Saved Pictures\\Snapchat-1961249129.jpg"))
        .when().post("rest/api/3/issue/{key}/attachments")
                .then().assertThat().statusCode(200);


    }
}
