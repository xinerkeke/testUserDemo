package main.com.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.com.base.TestBase;

public class UserMngPage extends TestBase{
	
	@FindBy(xpath="//input[@value='新增']")
	WebElement addUser;
	
	@FindBy(xpath="//input[@value='修改']")
	WebElement modUser;
	
	@FindBy(xpath="//input[@value='删除']")
	WebElement delUser;

	public UserMngPage() {
		PageFactory.initElements(driver, this);
	}
	
	public AddUserPage toAddUser(){
		addUser.click();
		return new AddUserPage();
	}
	
	public ModUserPage toModUer(){
		modUser.click();
		return new ModUserPage();
	}
	
	public void delUser(){
		delUser.click();
	} 
}
