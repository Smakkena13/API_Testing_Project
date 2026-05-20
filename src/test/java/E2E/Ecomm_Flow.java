package E2E;


import E2E.Pojo.AddtoCart_Request;
import E2E.Pojo.LoginRequest;
import E2E.Pojo.Product;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

import java.io.File;

import static io.restassured.RestAssured.*;

public class Ecomm_Flow {
    public static void main(String[] args) {
        RequestSpecification loginreq= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON).build();

        //login pojo
        LoginRequest l=new LoginRequest();
        l.setUserEmail("19l11a0507@gmail.com");
        l.setUserPassword("Admin@1234");

        RequestSpecification logreq=given().log().all().spec(loginreq).body(l);

        E2E.Pojo.LoginResponse obj=logreq.when().post("/api/ecom/auth/login")
                .then().log().all().extract().response().as(E2E.Pojo.LoginResponse.class);
        String token=obj.getToken();
        String userId=obj.getUserId();
        System.out.println("token: "+token);
        System.out.println("userId: "+userId);

        RequestSpecification createProductreq= new RequestSpecBuilder().addHeader("Authorization",token).setBaseUri("https://rahulshettyacademy.com")
                .build();

        RequestSpecification Createprod=given().spec(createProductreq).param("productName","Dummy")
                .param("productAddedBy",userId)
                .param("productCategory","fasion")
                .param("productSubCategory","shirts")
                .param("productPrice","1500")
                .param("productDescription","Addias Originals")
                .param("productFor","women")
                .multiPart("productImage",new File("C:\\Users\\Happy\\OneDrive\\Pictures\\Saved Pictures\\ab.jpg"));

        String res=Createprod.when().post("/api/ecom/product/add-product")
                .then().extract().response().asString();
        System.out.println(res);
        JsonPath jp=new JsonPath(res);
        String productId=jp.get("productId");

        //Add to cart
        RequestSpecification addToCartreq=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON).addHeader("Authorization",token).build();

        AddtoCart_Request ac=new AddtoCart_Request();
        ac.set_id(userId);

        Product p=new Product();
        p.setProductName("Dummy");
        p.set_id(productId);
        p.setProductCategory("fashion");
        p.setProductSubCategory("shirts");
        p.setProductPrice(1500);
        p.setProductDescription("Addias Originals");
        p.setProductImage("https://rahulshettyacademy.com/api/ecom/uploads/productImage_1779254372141.jpg");
        p.setProductRating("0");
        p.setProductTotalOrders("0");
        p.setProductStatus(true);
        p.setProductFor("women");
        p.setProductAddedBy(userId);
        p.set__v(0);

        ac.setProduct(p);

        String res1=given().log().all().spec(addToCartreq).body(ac)
                .when().post("/api/ecom/user/add-to-cart")
                .then().log().all().extract().response().asString();
        JsonPath js1=new JsonPath(res1);
        System.out.println(res1);
        System.out.println("message:  "+js1.get("message"));

        //place order:
        RequestSpecification placeoderReq= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON).addHeader("Authorization",token).build();

        String res2=given().log().all().spec(placeoderReq).body("{\n" +
                "    \"orders\": [\n" +
                "        {\n" +
                "            \"country\": \"India\",\n" +
                "            \"productOrderedId\": \""+productId+"\"\n" +
                "        }\n" +
                "    ]\n" +
                "}").when().post("/api/ecom/order/create-order")
                .then().log().all().extract().response().asString();

        JsonPath jp2=new JsonPath(res2);
        System.out.println(res2);

        //delete product
        RequestSpecification delProd=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization",token).build();

        String res3=given().log().all().spec(delProd).pathParam("productId",productId)
                .when().delete("/api/ecom/product/delete-product/{productId}")
                .then().log().all().extract().response().asString();
        System.out.println(res3);

    }
}
