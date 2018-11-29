package main.com.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.com.base.TestBase;

public class AddUserPage extends TestBase {
	@FindBy(id = "userID")
	WebElement userID;

	@FindBy(id = "userName")
	WebElement userName;

	@FindBy(id = "userSex")
	WebElement userSex;

	@FindBy(id = "userTel")
	WebElement userTel;

	@FindBy(id = "userAddr")
	WebElement userAddr;
	
	@FindBy(xpath="//input[@value='保存']")
	WebElement save;

	public AddUserPage() {
		PageFactory.initElements(driver, this);
	}

	public void addUser(String u_id, String u_name, String u_sex, String u_tel, String u_addr) {
		userID.sendKeys(u_id);
		userName.sendKeys(u_name);
		userSex.sendKeys(u_sex);
		userTel.sendKeys(u_tel);
		userAddr.sendKeys(u_addr);
		save.click();
	}
	
	
}
