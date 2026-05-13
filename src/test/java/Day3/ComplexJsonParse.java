package Day3;

import files.Payload;
import io.restassured.path.json.JsonPath;
/*
1. Print No of courses returned by API
2. Print Purchase Amount
3. Print Title of the first course
4. Print All course titles and their respective Prices
5. Print no of copies sold by RPA Course
6. Verify if Sum of all Course prices matches with Purchase Amount
*/
public class ComplexJsonParse {
    public static void main(String[] args) {
        JsonPath js=new JsonPath(Payload.CoursePrice()); // assuming the dummy response and parsing

        //Print No of courses returned by API
        int totalcourses= js.getInt("courses.size()");
        System.out.println("total courses: "+totalcourses);

        //2. Print Purchase Amount
        int amt=js.getInt("dashboard.purchaseAmount");
        System.out.println("amount: "+amt);

        //3. Print Title of the first course
        String title=js.get("courses[0].title");
        System.out.println("title: "+title);

        //4. Print All course titles and their respective Prices
        for(int i=0;i<totalcourses;i++){
            String course=js.get("courses["+i+"].title");
            int price=js.getInt("courses["+i+"].price");
            System.out.println("course: "+course);
            System.out.println("price: "+price);
        }

        //5. Print no of copies sold by RPA Course
        for(int i=0;i<totalcourses;i++){
            String ti=js.get("courses["+i+"].title");
            if(ti.equalsIgnoreCase("RPA")){
                int copies=js.getInt("courses["+i+"].copies");
                System.out.println("copies: "+copies);
                break;
            }
        }

        //6. Verify if Sum of all Course prices matches with Purchase Amount
        int sum=0;
        for(int i=0;i<totalcourses;i++){
            int price=js.getInt("courses["+i+"].price");
            int copies=js.getInt("courses["+i+"].copies");
            sum=sum+(price*copies);
        }
        System.out.println("sum: "+sum);
        int expsum=js.getInt("dashboard.purchaseAmount");
        if(expsum==sum){
            System.out.println("matches!!!");
        }

    }
}
