package main.com.base;

import static org.testng.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class TestBase01 {
	public static WebDriver driver;
	public static Properties pps;

	private String OSType = System.getProperty("os.name");
	private static String projectpath = System.getProperty("user.dir");
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	private static long PAGE_LOAD_TIMEOUT = 20;
	private static long IMPLICIT_WAIT = 30;
	private static long SCRIPT_TIMEOUT = 15;

	
	
	@BeforeTest
	public void beforeTest() throws Exception {
		initialization();
	}
	
	/**
	 * 加载配置
	 */
	private void loadProp() {
		pps = new Properties();
		try {
			pps.load(new FileInputStream(projectpath + "/src/testng.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * init create driver init wait time
	 */
	public void initialization() {
		System.out.println("TestBase01.initialization()");
		loadProp();
		String webDriver = pps.getProperty("webdriver");
		String driverExe = null;
		if (!OSType.contains("Mac")) {
			// Win系统
			driverExe = pps.getProperty("driverPath");
		} else {
			// MAC系统
		}

		System.setProperty(webDriver, driverExe);

		if (webDriver.contains("webdriver.firefox")) {
			getFirefoxDriver();
		}
		if (webDriver.contains("webdriver.gecko")) {
			driver = new FirefoxDriver();
		}
		if (webDriver.contains("webdriver.safari")) {
			driver = new SafariDriver();
		}
		if (webDriver.contains("webdriver.ie")) {
			getIEDriver();
		}
		if (webDriver.contains("phantomjs")) {
			getPhantomjs();
		}
		if (webDriver.contains("chrome")) {
			ChromeOptions options = new ChromeOptions();
			// ChromeDriver切换浏览器语言
			options.addArguments("--lang=" + "zh-CN");
			// 通过配置参数禁止data;的出现
			options.addArguments(
					"--user-data-dir=C:/Users/Administrator/AppData/Local/Google/Chrome/User Data/Default");
			driver = new ChromeDriver(options);
		}

		// EventFiringWebDriver给webdriver注册自己的listener
		// driver遇到异常的时候就执行异常监控中的截图操作
		// 其他事件中的操作也会执行
		/*
		 * e_driver = new EventFiringWebDriver(driver); e_driver.register(new
		 * WebEventListener()); driver = e_driver;
		 */

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(SCRIPT_TIMEOUT, TimeUnit.SECONDS);

		driver.get(pps.getProperty("baseUrl") + pps.getProperty("loginLink"));
	}

	/*
	 * @Test public void f() { TestBase base = new TestBase();
	 * base.initialization(); }
	 */

	/**
	 * 使用phantomjs,firfox启动失败???
	 */
	private synchronized void getPhantomjs() {
		/*
		 * DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
		 * capabilities.setJavascriptEnabled(true);
		 * capabilities.setCapability("userAgent",
		 * "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:25.0) Gecko/20100101 Firefox/25.0 "
		 * );
		 */
		// driver = new PhantomJSDriver();
	}

	/**
	 * use IE driver
	 */
	private synchronized void getIEDriver() {
		String PROXY = "http://proxy:8083";
		Proxy proxy = new Proxy();
		proxy.setHttpProxy(PROXY).setFtpProxy(PROXY).setSslProxy(PROXY);
		/*
		 * DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
		 * caps.setCapability(InternetExplorerDriver.
		 * INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		 * caps.setCapability("ignoreProtectedModeSettings", true);
		 * caps.setCapability(CapabilityType.PROXY, proxy);
		 */
		driver = new InternetExplorerDriver();
	}

	/**
	 * use firefox driver
	 */
	private synchronized void getFirefoxDriver() {
//		File firebug = new File(projectpath + pps.getProperty("firebug"));
//		File firefinder = new File(projectpath + pps.getProperty("firefinder"));
//		FirefoxProfile profile = new FirefoxProfile();

		//		driver = new FirefoxDriver(profile);
		driver = new FirefoxDriver();
	}


	/*
	 * 遍历body节点下的所有节点 取其文本值 并判断是否包含文本 what
	 */
	public static boolean isTextPresent(WebDriver driver, String what) {
		try {
//			String str = driver.findElement(By.tagName("body")).getText();
//			System.out.println(str);
			return driver.findElement(By.tagName("body")).getText().contains(what);
		} catch (Exception e) {
			return false;
		}
	}

	public static WebElement getTableFirstElt(WebDriver driver, String tableXpath, int serial, String what) {

		if (what == null || tableXpath == null)
			return null;

		List<WebElement> rows = driver.findElements(By.xpath(tableXpath));

		for (WebElement row : rows) {
			List<WebElement> cols = row.findElements(By.tagName("td"));

			if (cols.get(serial).getText().contains(what)) {
				return row;
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

	public void WaitAndClickElmt(final WebElement ele) throws InterruptedException {
		int i = 0;

		for (; i < 60; i++) {
			try {
				(new WebDriverWait(driver, 2)).until(new ExpectedCondition<WebElement>() {
					public WebElement apply(WebDriver d) {
						return ele;
					}
				});

				// driver.findElement(By.xpath(xpath)).click();
				ele.click();
				break;
			} catch (Exception e) {
				System.out.println("Failed to click the element.");
			}
			// Thread.sleep(3000);
		}
		if (i >= 60) {
			ele.click();
		}
	}
	
	
	/**
	 * 等待并点击元素
	 * @param xpath
	 * @throws InterruptedException
	 */
	public void WaitAndClickElmt(final String xpath) throws InterruptedException {
		int i = 0;

		for (; i < 60; i++) {
			try {
				(new WebDriverWait(driver, 2)).until(new ExpectedCondition<WebElement>() {
					public WebElement apply(WebDriver d) {
						return driver.findElement(By.xpath(xpath));
					}
				});

				driver.findElement(By.xpath(xpath)).click();
				break;
			} catch (Exception e) {
				System.out.println("Failed to click the element.");
			}
			// Thread.sleep(3000);
		}
		if (i >= 60) {
			driver.findElement(By.xpath(xpath)).click();
		}
	}
	
	@AfterTest
	public void afterTest() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

}
