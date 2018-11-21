package main.com.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.os.WindowsUtils;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;



public class DriverFactory {

	/** The webdriver. */
	private String webdriver;

	/** The chromedriver. */
	private String driverpath;

	/**
	 * the driverfactory
	 */
	public static DriverFactory driverfactory;

	/** The prop. */
	private Properties prop = null;

	/** The log. */
	static LogUtils log = new LogUtils(DriverFactory.class);

	/** The fire bug. */
	private String fireBug;

	/**
	 * The fire finder
	 */
	private String fireFinder;

	/** The config. */
	private String config = System.getProperty("user.dir") + "\\src\\test.properties";

	/**
	 * The driver.
	 */
	public WebDriver driver = null;

	/** The OS type. */
	private String OSType = System.getProperty("os.name");

	public enum BrowserType {
		firefox, chrome, ie, edge, safari
	}

	/**
	 * constructor
	 */
	public DriverFactory() {

	}

	/**
	 * @return
	 */
	public WebDriver getDriver() {
		return driver;
	}

	/**
	 * Gets the single instance of DriverFactory.
	 */
	public static DriverFactory getInstance() {
		if (driverfactory == null) {
			synchronized (DriverFactory.class) {
				driverfactory = new DriverFactory();
			}
		}
		return driverfactory;
	}

	/**
	 * close driver.
	 */
	public static void close() {
		if (driverfactory != null) {
			driverfactory = null;
		}
	}

	/**
	 * This method will get BrowserType.
	 */
	public BrowserType getBrowserType() {

		if (driver instanceof FirefoxDriver) {
			return BrowserType.firefox;
		} else if (driver instanceof ChromeDriver) {
			return BrowserType.chrome;
		} else if (driver instanceof InternetExplorerDriver) {
			return BrowserType.ie;
		} else if (driver instanceof EdgeDriver) {
			return BrowserType.edge;
		} else if (driver instanceof SafariDriver) {
			return BrowserType.safari;
		} else {
			return null;
		}

	}

	public WebDriver getChromeDriver() {

		try {
			prop = ConfigUtils.getProperties(config);
		} catch (IOException e) {
			log.error(e.getMessage());
		}

		if (prop != null) {
			webdriver = prop.getProperty("chromewebdriver");
			if (!OSType.contains("Mac")) {
				driverpath = prop.getProperty("chromedriverPath");
			} else {
				// TODO
			}
		}

		ChromeOptions options = new ChromeOptions();
		// ChromeDriver切换浏览器语�?
		options.addArguments("--lang=" + "zh-CN");
		// 通过配置参数禁止data;的出�?
		options.addArguments("--user-data-dir=C:/Users/Administrator/AppData/Local/Google/Chrome/User Data/Default");
		System.setProperty(webdriver, driverpath);

		driver = new ChromeDriver(options);

		log.info("Create ChromeDrive ");
		return driver;
	}

	/**
	 * @return
	 */
	public WebDriver getFirefoxDriver() {
		try {
			prop = ConfigUtils.getProperties(config);
			WindowsUtils.killByName("firefox");

		} catch (Exception e) {
			log.error("can not find firefox process");
		}

		if (prop != null) {
			webdriver = prop.getProperty("webdriver");
			fireBug = prop.getProperty("firebug");
			fireFinder = prop.getProperty("firefinder");
			if (!OSType.contains("Mac")) {
				driverpath = prop.getProperty("driverPath");
			} else {
				// TODO
			}

		}

		System.setProperty(webdriver, driverpath);
		File firebug = new File(fireBug);
		File firefinder = new File(fireFinder);
		FirefoxProfile profile = new FirefoxProfile();

		try {
			// 加载firefinder,firebug插件
			profile.addExtension(firebug);
			profile.addExtension(firefinder);
			profile.setPreference("extensions.firebug.currentVersion", "2.0.19");
			profile.setPreference("extensions.firebug.allPagesActivation", "off");
			profile.setPreference("webdriver.accept.untrusted.certs", "true");
		} catch (Exception e) {
			e.printStackTrace();
		}

		driver = new FirefoxDriver(profile);
		log.info("Create FirefoxDriver ");
		return driver;

	}

	/**
	 * Open IE Broswer
	 * 
	 * @return
	 */
	public synchronized WebDriver getIEDriver() {
		try {
			prop = ConfigUtils.getProperties(config);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (prop != null) {
			webdriver = prop.getProperty("IEwebdriver");
			driverpath = prop.getProperty("IEdriverPath");
		}
		System.setProperty(webdriver, driverpath);
		String PROXY = "http://proxy:8083";
		org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
		proxy.setHttpProxy(PROXY).setFtpProxy(PROXY).setSslProxy(PROXY);
		DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
		caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		caps.setCapability("ignoreProtectedModeSettings", true);
		caps.setCapability(CapabilityType.PROXY, proxy);
		driver = new InternetExplorerDriver();
		return driver;
	}

	public static void main(String[] args) {
		getInstance().getIEDriver();
		getInstance().driver.get("http://www.baidu.com");
	}

}
