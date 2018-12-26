package main.com.tests;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import main.com.base.TestBase01;
import main.com.pages.LoginPage01;
import main.com.pages.UserMngPageMetrics;
import main.com.util.ReadExecl;
import main.com.util.TestngListener;

@Listeners({ TestngListener.class })
public class testUserManMetrics extends TestBase01 {
	
	LoginPage01 loginPage;
	UserMngPageMetrics userMngPage;
	WebDriver webdriver;
	
	@BeforeClass
	public void beforeClass() throws Exception {
		
		loginPage = new LoginPage01();
		loginPage.login("admin", "admin");
 		
		userMngPage = new UserMngPageMetrics();
		WaitAndClickElmt("//ul[@id='topMenu']/li/a[text()='系统管理']");
		WaitAndClickElmt("//html/body/div[1]/div/div[1]/div[2]/ul/li[3]");
		WaitAndClickElmt("//li/div/span[@class='tree-title' and text()='用户管理']");
	}
	
	
	/*
	 * 增加测试（取数据、测试代码）
	 */
	@DataProvider(name = "testAddData")
	public static Object[][] getTestAddData() throws Exception {
		return ReadExecl.initTestData("testSysData.xls", "addData");
	}
	
	
	@Test(dataProvider = "testAddData")
	public void testAdd(String loginname, String userName, String email, String phone, String mobile, String expectStr) throws Exception {
		userMngPage.addUser(loginname, userName, email, phone, mobile);
		/*
		 * 断言执行状态
		 */
		assertTrue(isTextPresent(driver, expectStr));
		webdriver = driver;
		driver.findElement(By.id("user-close"));

	}
	
	
	/*
	 * 修改测试（取数据、测试代码）
	 */
	@DataProvider(name = "testModData")
	public static Object[][] getTestModData() throws Exception {
		return ReadExecl.initTestData("testSysData.xls", "modData");
	}
	
	@Test(dataProvider = "testModData", /*dependsOnMethods = { "testAdd" }, */alwaysRun = true)
	public void testMod(String loginname, String name, String email, String phone, String mobile, String expectStr) throws Exception {
		userMngPage.modUser(loginname, name, email, phone, mobile);
		assertTrue(isTextPresent(driver, expectStr));
		driver.findElement(By.id("user-close"));
	}
	
	/*
	 * 删除测试（取数据、测试代码）
	 */
	@DataProvider(name = "testDelData")
	public static Object[][] getTestDelData() throws Exception {
		return ReadExecl.initTestData("testSysData.xls", "delData");
	}

	@Test(dataProvider = "testDelData", /*dependsOnMethods = { "testAdd", "testMod" },*/ alwaysRun = true)
	public void testDel(String loginname, String expectStr) throws Exception {
		userMngPage.delUser(loginname,expectStr);
		
	}

}
