package RestAssuredPractice;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GetRequests2 {

    public String baseURI = "https://api.nasa.gov/planetary/earth/assets";
    public String token = "XNyIjbscQVi4m6gc40Dcis0axEGzUeR3mIxJe3Ng";

    @BeforeTest
    void setup(){
        RestAssured.baseURI = baseURI;
    }

    @Test
    public void ValidateAndGetData(){

        String completeURL = baseURI + "?lon=-95.33&lat=29.78&date=2018-01-01&dim=0.10&api_key=" + token;


//        String completeURL = baseURI + "?lon=-95.33&lat=29.78&date=2018-01-01&&dim=0.10&api_key=" + token;

        Response response = RestAssured.given()
                .contentType("application/json")
                .when().get(completeURL);
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode,200);
        System.out.println("Status code: " + statusCode);

        JSONObject bodyContent = new JSONObject(response.getBody().asString());
        Assert.assertTrue(bodyContent.has("date"));
        Assert.assertTrue(bodyContent.has("id"));
        Assert.assertTrue(bodyContent.has("url"));

        boolean flag = bodyContent.has("date");
        if (flag) {
            System.out.println("Test passed: 'date' key is present");
        } else {
            System.out.println("Test failed: 'date' key is missing");
        }

        // Return the flag indicating the presence of the 'date' key in the response

        JSONObject resource = bodyContent.getJSONObject("resource");
        String printContent = resource.get("dataset").toString();
        System.out.println("Content: " + printContent);
    }


}
