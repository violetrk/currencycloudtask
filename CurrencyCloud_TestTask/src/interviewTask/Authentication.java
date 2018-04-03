package interviewTask;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Authentication {
	public String token;
	
	
	public boolean login() {
		RestAssured.baseURI = "https://devapi.currencycloud.com";
		RequestSpecification request = RestAssured.given();
		
		request
         .contentType("multipart/form-data")
         .multiPart("login_id", "wioletarak@gmail.com")
         .multiPart("api_key", "013129ed483304104f91bd2212a5536da91eb10ba3ecbf88d6820e07bfddc9e7");
        Response response = request.post("/v2/authenticate/api");
		
        int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);
		String token = response.jsonPath().get("auth_token");
 		Assert.assertEquals(token.length(), 32);
 		this.token = token;
 		
 		boolean isLogged = token.length() == 32;
 		
 		if (isLogged) {
 			System.out.println("Login successfull with token: " + this.token);
 			
 		} else {
 			System.out.println("Login failure!");
 		}
 		
 		return isLogged;
 		
	}
	
	public void logout() {
			RequestSpecification request = RestAssured.given();
			request.header("X-AUTH-TOKEN", this.token);
			Response response = request.post("/v2/authenticate/close_session");
			int statusCode = response.getStatusCode();
			Assert.assertEquals(statusCode, 200);
			this.token = null;
			
			System.out.println("Logout successfull!");
			
		
	}
}


