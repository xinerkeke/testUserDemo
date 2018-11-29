package main.com.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.com.base.TestBase;

public class LoginPage extends TestBase{
	
	@FindBy(name="adminCode")
	WebElement user;
	
	@FindBy(name="password")
	WebElement passwd;
	
	@FindBy(linkText="登录")
	WebElement commit;
	
	public LoginPage() {
		PageFactory.initElements(driver, this);
	}


	public HomePage login(String username, String password) {
		user.sendKeys(username);
		passwd.sendKeys(password);
		commit.click();
		return new HomePage();
	}
	
	public boolean isUserTextDisplay(){
		return user.isDisplayed();
	}

}
