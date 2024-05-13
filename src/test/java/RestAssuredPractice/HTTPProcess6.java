package RestAssuredPractice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.json.Json;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class HTTPProcess6 {

    private String baseURI = "http://localhost:3000";
    private static String employeeId;

    @BeforeTest
    void setup() {
        RestAssured.baseURI = baseURI;
    }

    @Test(priority = 1)
    void getRequest() {
        Response res = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/employees");
        int statusCode = res.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Different code is displayed");
        System.out.println("Status Code: " + statusCode);

        String header = res.getHeader("Connection");
        Assert.assertEquals(header, "keep-alive");
        System.out.println("Header: " + header);

        JSONArray ja = new JSONArray(res.body().asString());
        for (int i = 0; i < ja.length(); i++) {
            String names = ja.getJSONObject(i).get("name").toString();
            System.out.println(names);
        }

        res.body().prettyPrint();

    }

    @Test(priority = 2)
    void postRequest() {

        HashMap<String, String> data = new HashMap<>();
        data.put("name", "moku Uzimaki");
        data.put("position", "Automation Engineer");
        data.put("department", "Technology");
        data.put("phone", "7037037033");
        data.put("email", "umGoku@dbz.com");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/employees");

        Assert.assertEquals(response.getStatusCode(), 201, "Incorrect status code for POST request");

        employeeId = response.jsonPath().getString("id");

    }

    @Test(priority = 3)
    void putRequest() {

        HashMap<String, String> data = new HashMap<>();
        data.put("name", "Bernad Uzimaki");
        data.put("position", "Automation QA Tester");
        data.put("department", "Software Testing");
        data.put("phone", "7037037039");
        data.put("email", "umBerBer@dbz.com");

        Response res = RestAssured.given()
                .body(data)
                .headers("Content-Type", "application/json")
                .when()
                .put("/employees/" + employeeId);

        int statusCode = res.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Incorrect Code is Displayed");
        System.out.println("Status code: " + statusCode);

        System.out.println("Updated Employee Details: ");
        res.body().prettyPrint();
    }

    @Test(priority = 4)
    void deleteRequest() {
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/employees/" + employeeId);

        Assert.assertEquals(response.getStatusCode(), 200, "Incorrect status code for DELETE request");

        System.out.println("Employee with ID " + employeeId + " deleted successfully");

        response.body().prettyPrint();

    }
}
