package RestAssuredPractice;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GetRequests1 {

    public String baseURI = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos";
    public String apiKey = "XNyIjbscQVi4m6gc40Dcis0axEGzUeR3mIxJe3Ng";

    @BeforeTest
    void setup(){
        RestAssured.baseURI = baseURI;
    }

    // how to print values from all objects within an array of JSON objects

    @Test(priority = 1)
    void testGetPhotosFromCuriosityRover() {
        // Construct the complete URL with query parameters and API key
        String completeURL = baseURI + "?sol=1000&camera=fhaz&api_key=" + apiKey;

        // Print the complete URL for debugging purposes
        System.out.println("Complete URL: " + completeURL);

        // Send the HTTP GET request using RestAssured
        Response response = RestAssured.given()
                .contentType("application/json")
                .when()
                .get(completeURL);

        // Get the status code from the response
        int statusCode = response.getStatusCode();
        System.out.println("Actual Status Code: " + statusCode);

        // Verify the expected status code (e.g., 200 OK)
        Assert.assertEquals(statusCode, 200, "Unexpected status code");

        // Print the response body for debugging purposes
        String responseBody = response.getBody().asString();
        System.out.println("Response Body: " + responseBody);

        // Perform additional assertions or extract information from the response as needed
        // Example: Extract and verify specific information from the JSON response
        String cameraName = response.jsonPath().getString("photos[0].rover.cameras[0].full_name");
        System.out.println("Camera Name: " + cameraName);
        Assert.assertEquals(cameraName, "Front Hazard Avoidance Camera", "Incorrect camera name");



        // Parse the JSON response string to a JSONObject
        JSONObject jsonResponse = new JSONObject(responseBody);

        // Get the "photos" array from the JSON response
        JSONArray photosArray = jsonResponse.getJSONArray("photos");

        // Iterate over each photo in the "photos" array
        for (int i = 0; i < photosArray.length(); i++) {
            JSONObject photo = photosArray.getJSONObject(i);

            // Get the "cameras" array from the current photo's "rover" object
            JSONArray camerasArray = photo.getJSONObject("rover").getJSONArray("cameras");

            // Iterate over each camera in the "cameras" array
            for (int j = 0; j < camerasArray.length(); j++) {
                JSONObject camera = camerasArray.getJSONObject(j);

                // Get the "full_name" value of the current camera
                String fullName = camera.getString("full_name");

                // Print the "full_name" value
                System.out.println("Camera Full Name: " + fullName);
            }
        }
    }

//    @Test
    void testGetPhotosFromCuriosityRover2() {
        // Construct the complete URL with query parameters and API key
        String completeURL = baseURI + "?sol=1000&camera=fhaz&api_key=" + apiKey;

        // Print the complete URL for debugging purposes
        System.out.println("Complete URL: " + completeURL);

        // Send the HTTP GET request using RestAssured
        Response response = RestAssured.given()
                .contentType("application/json")
                .when()
                .get(completeURL);

        // Get the status code from the response
        int statusCode = response.getStatusCode();
        System.out.println("Actual Status Code: " + statusCode);

        // Verify the expected status code (e.g., 200 OK)
        Assert.assertEquals(statusCode, 200, "Unexpected status code");

        // Print the response body for debugging purposes
        String responseBody = response.getBody().asString();
        System.out.println("Response Body: " + responseBody);

        // Perform additional assertions or extract information from the response as needed
        // Example: Extract and verify specific information from the JSON response
        String cameraName = response.jsonPath().getString("photos[0].rover.cameras[0].full_name");
        System.out.println("Camera Name: " + cameraName);
        Assert.assertEquals(cameraName, "Front Hazard Avoidance Camera", "Incorrect camera name");



        // Parse the JSON response string to a JSONObject
        JSONObject jsonResponse = new JSONObject(responseBody);

        // Get the "photos" array from the JSON response
        JSONArray photosArray = jsonResponse.getJSONArray("photos");

        // Ensure that there is at least one photo in the array
        if (photosArray.length() > 0) {
            // Get the first photo object from the "photos" array

            JSONObject firstPhoto = photosArray.getJSONObject(0);
//
//            // Get the "rover" object from the first photo
            JSONObject roverObject = firstPhoto.getJSONObject("rover");

            // Check if the "cameras" array exists within the "rover" object

            if (roverObject.has("cameras")) {
                // Get the "cameras" array from the "rover" object

                JSONArray camerasArray = roverObject.getJSONArray("cameras");

                // Print out the "full_name" values from each camera in the camerasArray
                for (int j = 0; j < camerasArray.length(); j++) {
                    JSONObject camera = camerasArray.getJSONObject(j);
                    String fullName = camera.getString("full_name");
                    System.out.println("Camera Full Name: " + fullName);
                }
            } else {
                System.out.println("No cameras array found in the rover object of the first photo.");
            }
        } else {
            System.out.println("No photos found in the response.");
        }
    }




 //   @Test
    void testGetPhotosFromCuriosityRover3() {
        // Construct the complete URL with query parameters and API key
        String completeURL = baseURI + "?sol=1000&camera=fhaz&api_key=" + apiKey;

        // Print the complete URL for debugging purposes
        System.out.println("Complete URL: " + completeURL);

        Response response = RestAssured.given()
                .contentType("application/json")
                .when()
                .get(completeURL);

        int statusCode = response.getStatusCode();
        System.out.println("Actual Status Code: " + statusCode);

        Assert.assertEquals(statusCode, 200, "Unexpected status code");

        // Print the response body for debugging purposes
        String responseBody = response.getBody().asString();
        System.out.println("Response Body: " + responseBody);

        String cameraName = response.jsonPath().getString("photos[0].rover.cameras[0].full_name");
        System.out.println("Camera Name: " + cameraName);
        Assert.assertEquals(cameraName, "Front Hazard Avoidance Camera", "Incorrect camera name");



        // Parse the JSON response string to a JSONObject
        JSONObject jsonResponse = new JSONObject(responseBody);

        // Get the "photos" array from the JSON response
        JSONArray photosArray = jsonResponse.getJSONArray("photos");

        if (!photosArray.isEmpty()) {
            JSONObject getRoverObject = photosArray.getJSONObject(0).getJSONObject("rover");
            if(getRoverObject.has("cameras")){
                JSONArray cameraArray = getRoverObject.getJSONArray("cameras");
                for (int j = 0; j < cameraArray.length(); j++){
                    String fullname = cameraArray.getJSONObject(j).get("full_name").toString();
                    System.out.println("Camera Full Name: " + fullname);
                }
            } else {
                System.out.println("No cameras array found in the rover object of the first photo.");
            }
        } else {
            System.out.println("No photos found in the response.");
        }

    }


    // Use this logic in the following situation:
    // JSON object has an array of multiple JSON objects, and each JSON object has objects nested within each other
    // Suppose you want to traverse through one of the objects p1 in the array, and you want to target a nested object
    // within p1, and you want to print out only the values within one of the nested objects contained in p1

    @Test
    void GetContentFromObject2(){

        String completeURL = baseURI + "?sol=1000&camera=fhaz&api_key=" + apiKey;

        Response responseBody = RestAssured.given()
                .contentType("application/json; charset=utf-8")
                .when().get(completeURL);

        int statusCode = responseBody.getStatusCode();
        Assert.assertEquals(statusCode,200);
        System.out.println("Status Code is: " + statusCode);

        JSONObject bodyContent = new JSONObject(responseBody.getBody().asString());
        JSONArray photosArray = bodyContent.getJSONArray("photos");

        if(!photosArray.isEmpty()){
            JSONObject getRoverObject2 = photosArray.getJSONObject(1).getJSONObject("rover");

            if (getRoverObject2.has("cameras")){
                JSONArray cameras = getRoverObject2.getJSONArray("cameras");
                for(int i = 0; i < cameras.length(); i++){
                    String camerasName = cameras.getJSONObject(i).get("name").toString();
                    System.out.println("Name is: " + camerasName);
                }
            }
        }

    }


}








