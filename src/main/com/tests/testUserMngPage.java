package main.com.tests;

import org.testng.annotations.Test;

import main.com.base.TestBase;
import main.com.pages.HomePage;
import main.com.pages.LoginPage;
import main.com.pages.UserMngPage;
import main.com.util.ReadExecl;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterClass;

public class testUserMngPage extends TestBase{
	
	private static String dataFile = "user.xls";
	LoginPage loginPage;
	HomePage homePage;
	UserMngPage userMngPage;

	@BeforeClass
	public void beforeClass() {
		initialization();
		loginPage = new LoginPage();
		homePage = loginPage.login(pps.getProperty("username"), pps.getProperty("password"));
		userMngPage = homePage.toUserMngPae();
	}
	
	@DataProvider(name = "userAdd")
	public static Object[][] getTestAddData() throws Exception {
		return ReadExecl.initTestData(dataFile, "t_user");
	}
	
	@Test(dataProvider = "userAdd")
	public void testAddUser(String u_id, String u_name, String u_sex, String u_tel, String u_addr,
			String expectStr) throws Exception {
		userMngPage.toAddUser().addUser(u_id, u_name, u_sex, u_tel, u_addr);
		assertTrue(isTextPresent(driver, expectStr));
	}

	@AfterClass
	public void afterClass() {
	}

}
