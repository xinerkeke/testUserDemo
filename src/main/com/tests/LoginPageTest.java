package main.com.tests;

import static org.testng.Assert.fail;

import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import main.com.base.TestBase;
import main.com.pages.HomePage;
import main.com.pages.LoginPage;
import main.com.util.ReadExecl;

public class LoginPageTest extends TestBase {
	LoginPage loginPage;
	HomePage homePage;
	private StringBuffer verificationErrors = new StringBuffer();

	public LoginPageTest() {
		super();
	}

	@BeforeClass
	public void setUp() {
		initialization();
		loginPage = new LoginPage();
	}

	@DataProvider(name = "testLoginData")
	public static Object[][] getTestData() throws Exception {
		return ReadExecl.initTestData("login_user.xls", "login");
	}

	@Test(dataProvider = "testLoginData")
	public void testLogin(String account, String password, String expect) throws Exception {
		try {
			homePage = loginPage.login(account, password);
			if (loginPage.isUserTextDisplay()) {
				isTextPresent(driver, expect);
			} 
		} catch (NoSuchElementException e) {
		}
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
		/* 断言失败,异常信息append verificationErrors中，继续执行，在tearDown中抛出异常信息 */
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

}
