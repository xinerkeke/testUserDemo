package main.com.tests;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import main.com.base.TestBase;
import main.com.util.ReadExecl;
import main.com.util.TestngListener;


@Listeners({ TestngListener.class })
public class testUserDemo extends TestBase {
	
	public static Logger logger = Logger.getLogger(testUserDemo.class); 

	public static String dataFile = "user.xls";

	@BeforeClass
	public void beforeClass() throws Exception {
		
	}

	@DataProvider(name = "userAdd")
	public static Object[][] getTestAddData() throws Exception {
		return ReadExecl.initTestData(dataFile, "t_user");
	}


	@Test(dataProvider = "userAdd")
	public void testAdd(String u_id, String u_name, String u_sex, String u_tel, String u_addr,
			String expectStr) throws Exception {
		driver.findElement(By.linkText("用户管理")).click();
		driver.findElement(By.xpath("//input[@value='新增']")).click();
		driver.findElement(By.id("userID")).clear();
		driver.findElement(By.id("userID")).sendKeys(u_id);
		driver.findElement(By.id("userName")).clear();
		driver.findElement(By.id("userName")).sendKeys(u_name);
		driver.findElement(By.id("userSex")).clear();
		driver.findElement(By.id("userSex")).sendKeys(u_sex);
		driver.findElement(By.id("userTel")).clear();
		driver.findElement(By.id("userTel")).sendKeys(u_tel);
		driver.findElement(By.id("userAddr")).clear();
		driver.findElement(By.id("userAddr")).sendKeys(u_addr);
		driver.findElement(By.xpath("//input[@value='保存']")).click();

	}
		
}
