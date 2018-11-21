package utility;

import java.io.IOException;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestngListener extends TestListenerAdapter {

	@Override
    public void onTestFailure(ITestResult result) {    
		
		 try {
	            (new TakeScreenShot()).ScreenShot(result);
	        } catch (SecurityException e) {
	            e.printStackTrace();
	        } catch (IllegalArgumentException e) {         
	            e.printStackTrace();
	        } catch (IOException e) {
				e.printStackTrace();
			}
	}
}
