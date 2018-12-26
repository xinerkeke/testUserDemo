package main.com.pages;

import static org.testng.Assert.assertTrue;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Listeners;

import main.com.base.TestBase01;
import main.com.util.TestngListener;


@Listeners({ TestngListener.class })
public class UserMngPageMetrics extends TestBase01{
	
	@FindBy(xpath="//li/div/span[@class='tree-title' and text()='用户管理']")
	WebElement userMng;

	@FindBy(xpath="//div[@class='datagrid-toolbar']/a/span/span[text()='新增']")
	WebElement useradd;
	
	@FindBy(xpath="//div[@class='datagrid-toolbar']/a/span/span[text()='修改']")
	WebElement usermod;
	
	@FindBy(xpath="//div[@class='datagrid-toolbar']/a/span/span[text()='删除']")
	WebElement userdel;
	
	@FindBy(id="login_name")
	WebElement loginName;
	
	@FindBy(id="user_name")
	WebElement userName;
	
	@FindBy(xpath="//input[@name='userStatus' and @value='1']")
	WebElement userStatus;
	
	@FindBy(xpath="//form[@id='userEditForm']/div[1]/ul/li[4]/span/span/span/span")
	WebElement userTypeSelect;
	
	@FindBy(xpath="//div[@value='2']")
	WebElement userType;
	
	@FindBy(xpath="//form[@id='userEditForm']/div[@class='imoia_pop_con1 cl']/ul/li[5]/span/input[@type='text']")
	WebElement instituteEle;
	
	@FindBy(id="user_email")
	WebElement email;
	
	@FindBy(id="user-tel")
	WebElement tel;
	
	@FindBy(id="user-mobile")
	WebElement mobile;
	
	@FindBy(id="user-save")
	WebElement save;
	
	@FindBy(id="popConfirmYes")
	WebElement confirm;
	
	private Random rand = new Random();
	private String Institut[] = {"XX银行S","桂林银行总行","XX银行一级支行2", "XX银行二级支行3"};
	private String InstitutId[] =  {"0000","999999","0004","0005"};
	private int InstituSize = Institut.length;

	public UserMngPageMetrics() {
		System.out.println("UserMngPageMetrics.UserMngPageMetrics()");
		PageFactory.initElements(driver, this);
	}

	/**
	 * @param loginname
	 * @param userName
	 * @param email
	 * @param phone
	 * @param mobile
	 * @param expectStr
	 * @throws Exception
	 */
	public void addUser(String loginname, String username, String useremail, String phone, String mobileNum) throws Exception {		
		
		/**
		 *  左侧菜单选择,用户管理
		 */
		WaitAndClickElmt(userMng);
		
		/**
		 * 新增按钮
		 */
		useradd.click();
//		WaitAndClickElmt(useradd);
		
		loginName.clear();
		loginName.sendKeys(loginname);
		userName.clear();
		userName.sendKeys(username);
		userStatus.click();
		userTypeSelect.click();
		userType.click();
		//设置所属机构
		setInstituteVal();
		email.clear();
		email.sendKeys(useremail);
		tel.clear();
		tel.sendKeys(phone);
		mobile.clear();
		mobile.sendKeys(mobileNum);
		
		save.click();
	}
	
	public void modUser(String loginname, String name, String useremail, String phone, String usermobile) throws Exception{
		WebElement chkbox;
		chkbox = getTableFirstElt(driver, "//table[@class='datagrid-btable']/tbody/tr", 2, loginname);
		if (chkbox != null && !loginname.equals("")) {
//			chkbox.findElement(By.name("userId")).click();
			WaitAndClickElmt(chkbox.findElement(By.name("userId")));

			usermod.click();
			userName.clear();
			userName.sendKeys(name);
			userStatus.click();
			userTypeSelect.click();
			userType.click();

			//设置所属机构
			setInstituteVal();
			
			email.clear();
			email.sendKeys(useremail);
			tel.clear();
			tel.sendKeys(phone);
			mobile.clear();
			mobile.sendKeys(usermobile);
			
			save.click();

		}
	}
	
	public void delUser(String loginname ,String expectStr) throws Exception{
		WebElement chkbox;
		chkbox = getTableFirstElt(driver, "//table[@class='datagrid-btable']/tbody/tr", 2, loginname);
	//	chkbox.findElement(By.name("userId")).click();
		WaitAndClickElmt(chkbox.findElement(By.name("userId")));
		userdel.click();
		confirm.click();
		assertTrue(isTextPresent(driver, expectStr));
	}
	
	/**
	 * 设置所属机构
	 * @throws Exception 
	 */
	private void setInstituteVal() throws Exception {
	
		//1. 删除"readonly"只读属性
		((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('readonly')", instituteEle);
		
		// 2.机构列表中随机选择"机构"
		int randVal = rand.nextInt(InstituSize);
		String instiVal = Institut[randVal];
		
		// 3.设置机构
		instituteEle.clear();
		instituteEle.sendKeys(instiVal);
		
		// 4.设置"readonly"只读属性
		((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('readonly','readonly')", instituteEle);
		//5.隐藏框设置ID值
		((JavascriptExecutor) driver).executeScript("document.getElementsByName('branchNo')[0].value='"+InstitutId[randVal]+"'");
		Thread.sleep(2000);

	}
}
