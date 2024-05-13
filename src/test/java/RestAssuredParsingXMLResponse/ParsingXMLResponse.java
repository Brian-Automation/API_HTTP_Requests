package RestAssuredParsingXMLResponse;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;



public class ParsingXMLResponse {

    // in the youtube example, the xml rsponse has a page number

    @Test(priority = 1)
    void testXMLResponse1(){
        //Approach 1

        given()
                .when()
                .get("https://thetestrequest.com/authors.xml")
                .then()
                .statusCode(200)
                .header("Content-Type","application/xml; charset=utf-8")
                .body("objects.object[0].id",equalTo("1"))
                .body("objects.object[0].name",equalTo("Karl Zboncak"))
                .body("objects.@type",equalTo("array"));

    }

    @Test(priority = 2)
    void testXMLResponse2(){
        //Approach 2
        // Return response in variable

        Response res = given()
                .when()
                .get("https://thetestrequest.com/authors.xml");
        res.getStatusCode();
        Assert.assertEquals(res.getStatusCode(),200);
        Assert.assertEquals(res.header("Content-Type"),"application/xml; charset=utf-8");

        // xml path to find content within body
        String objectType = res.xmlPath().get("objects.@type").toString(); // must convert xml object into string
        Assert.assertEquals(objectType,"array");

        String name = res.xmlPath().get("objects.object[0].name").toString(); // must convert xml object into string
        Assert.assertEquals(name,"Karl Zboncak");
    }

    @Test(priority = 3)
    void testXMLResponse3(){
        //Approach 3
        //using xml path class

        Response res = given() // response body  is return in Response format
                .when()
                .get("https://thetestrequest.com/authors.xml");
        res.getStatusCode();
        Assert.assertEquals(res.getStatusCode(),200);
        Assert.assertEquals(res.header("Content-Type"),"application/xml; charset=utf-8");

        //to find xml path to find content within body
        String objectType = res.xmlPath().get("objects.@type").toString(); // must convert xml object into string
        Assert.assertEquals(objectType,"array");

        String name = res.xmlPath().get("objects.object[0].name").toString(); // must convert xml object into string
        Assert.assertEquals(name,"Karl Zboncak");

        XmlPath xmlobj = new XmlPath(res.asString()); // will convert response into String from Response format

        //find total number of object's within the objects tag
        // if you want to convert specific into string, use toString
        // if you want to convert entire response body into string, use asString

       List<String> numberOfObjects = xmlobj.getList("objects.object"); // object info we've stored into the variable
        numberOfObjects.size();
        Assert.assertEquals(numberOfObjects.size(),5);
        System.out.println(numberOfObjects.size());
    }

    @Test(priority = 4)
    void testXMLResponse4() {
        //Approach 3
        //using xml path class
        //Suppose we want to verify a specific object name is present in response
        //Using for loop

        Response res = given() // response body  is return in Response format
                .when()
                .get("https://thetestrequest.com/authors.xml");
        res.getStatusCode();
        Assert.assertEquals(res.getStatusCode(), 200);
        Assert.assertEquals(res.header("Content-Type"), "application/xml; charset=utf-8");

        //to find xml path to find content within body
        String objectType = res.xmlPath().get("objects.@type").toString(); // must convert xml object into string
        Assert.assertEquals(objectType, "array");

        String name = res.xmlPath().get("objects.object[0].name").toString(); // must convert xml object into string
        Assert.assertEquals(name, "Karl Zboncak");

        XmlPath xmlobj = new XmlPath(res.asString()); // will convert response into String from Response format

        //find total number of object's within the objects tag
        // if you want to convert specific into string, use toString
        // if you want to convert entire response body into string, use asString

        List<String> numberOfObjects = xmlobj.getList("objects.object"); // object info we've stored into the variable
        numberOfObjects.size();
        Assert.assertEquals(numberOfObjects.size(), 5);
        System.out.println(numberOfObjects.size());

        List<String> verifyNames = xmlobj.getList("objects.object.name"); // this path locates all the names within the object's

        boolean status = false;
        for(String objectname : verifyNames){
//            System.out.println(objectname);
            if(objectname.equals("Jeffie Wolf I")){
                status = true;
                break;
            }
        }
        Assert.assertEquals(status,true);
    }

}
