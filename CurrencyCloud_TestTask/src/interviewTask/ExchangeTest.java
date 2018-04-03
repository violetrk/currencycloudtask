package interviewTask;
import org.testng.annotations.Test;

public class ExchangeTest {

	
  @Test
  public void StartTest() {
	  // Task 1  - Login and verify token process
	  Authentication authentication = new Authentication();
	  Conversion conversion = new Conversion();
	  
	  if (authentication.login() == true) {
		  
	 // Tasks 2 and 3 - conversion and rate comparison
		  conversion.sellGbpWithSuccess(authentication.token);
		  
	 // Task 4 - conversion with wrong amount
		  conversion.sellGBPWithFailure(authentication.token);
		  
	// Task 5 End API session
		  authentication.logout();
	  };
	
  }
}