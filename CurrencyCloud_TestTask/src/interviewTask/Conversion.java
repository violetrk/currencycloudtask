package interviewTask;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Conversion {
	
	public void sellGbpWithSuccess(String token) {
		RequestSpecification request = RestAssured.given();
		request
	    .contentType("multipart/form-data")
	    .multiPart("buy_currency", "USD")
	    .multiPart("sell_currency", "GBP")
	    .multiPart("fixed_side", "sell")
	    .multiPart("amount", "1000")
	    .multiPart("term_agreement", true);
		request.header("X-AUTH-TOKEN", token);
		Response response = request.post("/v2/conversions/create");
		
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);
		if (statusCode == 200) {
			System.out.println("Transaction successfull");
			float buy = response.jsonPath().getFloat("client_buy_amount");
			float sell = response.jsonPath().getFloat("client_sell_amount");
			float rate = response.jsonPath().getFloat("client_rate");
			float buyAmount = sell*rate;
			Assert.assertEquals(buy, buyAmount);
			System.out.println("Buy amount correct to rate");	
		}
		else System.out.println("Transaction unsuccessfull");
		
	}
	
	public void sellGBPWithFailure(String token) {
		RequestSpecification request = RestAssured.given();
		request
	    .contentType("multipart/form-data")
	    .multiPart("buy_currency", "USD")
	    .multiPart("sell_currency", "GBP")
	    .multiPart("fixed_side", "buy")
	    .multiPart("amount", "-1000")
	    .multiPart("term_agreement", true);
		request.header("X-AUTH-TOKEN", token);
		Response response = request.post("/v2/conversions/create");
		
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 400);	
		
		if(statusCode == 400) {
			String  error = response.jsonPath().getString("error_code");
			String errorMessage = response.jsonPath().get("error_messages.amount[0].message");
			System.out.println("Transaction failed: " + error +" - message: "+ errorMessage);
		} 
	}
}
