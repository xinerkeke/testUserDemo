package main.com.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.com.base.TestBase;

/**
 * 主页
 * @author Administrator
 *
 */
public class HomePage extends TestBase {

	@FindBy(linkText="用户管理")
	WebElement userMng;
	
	public HomePage() {
		PageFactory.initElements(driver, this);
	}
	
	public UserMngPage toUserMngPae(){
		userMng.click();
		return new UserMngPage();
	}
}
