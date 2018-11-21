package main.com.base;

import static org.testng.Assert.fail;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestBase {
	public static WebDriver driver;
	private static String baseUrl;
	private Properties pps;

	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@BeforeTest
	public void beforeTest() throws Exception {

		pps = new Properties();
		
		pps.load(new FileInputStream(System.getProperty("user.dir")
				+ "/src/testng.properties"));
		
		driver = createWebDriver(pps);
		
		doLogin(pps);
		
	}
	


	@AfterTest
	public void afterTest() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
	

	protected WebDriver createWebDriver(Properties pps) throws Exception{
		
		System.setProperty(pps.getProperty("webdriver"),
				pps.getProperty("driverPath"));

		if (pps.getProperty("webdriver").contains("webdriver.firefox")) {
		    driver = new FirefoxDriver();
		}
		if (pps.getProperty("webdriver").contains("webdriver.gecko")) {
		    driver = new FirefoxDriver();
		}
		if (pps.getProperty("webdriver").contains("webdriver.safari")) {
			driver = new SafariDriver();
		}
		if (pps.getProperty("webdriver").contains("webdriver.ie")) {
			DesiredCapabilities capabilities = DesiredCapabilities
					.internetExplorer();
			capabilities
					.setCapability(
							InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
							true);
			driver = new InternetExplorerDriver(capabilities);
		}
		if (pps.getProperty("webdriver").contains("phantomjs")) {
			DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
			capabilities.setJavascriptEnabled(true); 
			capabilities.setCapability("userAgent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:25.0) Gecko/20100101 Firefox/25.0 "); 
//			driver = new PhantomJSDriver(capabilities);
		}

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
	    driver.manage().timeouts().setScriptTimeout(15, TimeUnit.SECONDS);
	    
	    return driver;
	}
	
	/*
	 * ��¼ϵͳ
	 */
	protected void doLogin(Properties pps){
		
		baseUrl = pps.getProperty("baseUrl");
		
		driver.get(baseUrl);
		
		driver.findElement(By.name("adminCode")).clear();
		driver.findElement(By.name("adminCode")).sendKeys(
				pps.getProperty("username"));
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys(
				pps.getProperty("password"));
		driver.findElement(By.linkText("登录")).click();
	}
	
	public static boolean isTextPresent(WebDriver driver, String what) {
		try {
			return driver.findElement(By.tagName("body")).getText()
					.contains(what);
		} catch (Exception e) {
			return false;
		}
	}

	
	public static WebElement getTableFirstElt(WebDriver driver,
			String tableXpath, int serial, String what) {

		if (what == null || tableXpath == null)
			return null;

		List<WebElement> rows = driver.findElements(By.xpath(tableXpath));

		for (WebElement row : rows) {
			List<WebElement> cols = row.findElements(By.tagName("td"));

			if (cols.get(serial).getText().contains(what)) {
				return cols.get(0);
			}
		}
		return null;
	}

	protected boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	protected boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	protected String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}

}
