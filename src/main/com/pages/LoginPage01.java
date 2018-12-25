package main.com.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import main.com.base.TestBase01;

public class LoginPage01 extends TestBase01{
	
	@FindBy(id="loginName")
	WebElement user;
	
	@FindBy(id="loginPassword")
	WebElement passwd;
	
	@FindBy(id="loginBtn")
	WebElement commit;
	
	public LoginPage01() {
		System.out.println("LoginPage01.LoginPage01()");
		PageFactory.initElements(driver, this);
	}


	@Test
	public void f(){
		initialization();
		LoginPage01 login = new LoginPage01();
		login.login("admin", "admin");
	}
	
	public void login(String username, String password) {
		user.sendKeys(username);
		passwd.sendKeys(password);
		commit.click();
	}
	
	public boolean isUserTextDisplay(){
		return user.isDisplayed();
	}

}
