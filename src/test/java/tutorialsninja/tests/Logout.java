package tutorialsninja.tests;

import java.util.Properties;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;
import tutorialsninja.base.Base;
import utils.CommonUtils;

public class Logout extends Base {
	
	public WebDriver driver;
	Properties prop;
	LandingPage landingPage;
	LoginPage loginPage;
	AccountPage accountPage;
	
	@AfterMethod
	public void tearDown() {
		closeBrowser(driver);
	}
	
	@BeforeMethod
	public void setup() {
		
		driver = openBrowserAndApplication();
		prop = CommonUtils.loadProperties();
		landingPage = new LandingPage(driver);
		
	}
	
	@Test(priority = 1)
	public void verifyLoggingOutUsingMyAccountDropMenu() {
		
		loginPage = landingPage.navigateToLoginPage();
		accountPage = loginPage.loginToApplication(prop.getProperty("existingEmail"),prop.getProperty("validPassword"));
		headerOptions = new HeaderOptions(accountPage.getDriver());
		headerOptions.clickOnMyAccountDropMenu();
		accountLogoutPage = headerOptions.selectLogoutOption();
		Assert.assertTrue(accountLogoutPage.didWeNavigateToAccountLogoutPage());
		headerOptions = new HeaderOptions(accountLogoutPage.getDriver());
		headerOptions.clickOnMyAccountDropMenu();
		Assert.assertTrue(headerOptions.isLoginOptionAvailable());
		accountLogoutPage = new AccountLogoutPage(headerOptions.getDriver());
		landingPage = accountLogoutPage.clickOnContinueButton();
		Assert.assertEquals(getPageURL(landingPage.getDriver()),prop.getProperty("landingPageURL"));
	
	}
	
	
	@Test(priority = 2)
	public void verifyLoggingOutAndBrowingBack() {
		
		loginPage = landingPage.navigateToLoginPage();
		accountPage = loginPage.loginToApplication(prop.getProperty("existingEmail"), prop.getProperty("validPassword"));
		headerOptions = new HeaderOptions(accountPage.getDriver());
		headerOptions.clickOnMyAccountDropMenu();
		accountLogoutPage = headerOptions.selectLogoutOption();
		driver = navigateBack(accountLogoutPage.getDriver());
		driver = refreshPage(driver);
		loginPage = new LoginPage(driver);
		Assert.assertTrue(loginPage.didWeNaviateToLoginPage());
		
	}
	
	@Test(priority = 3)
	public void verifyThereIsNoLogoutOptionBeforeLogin() {
		
		headerOptions = new HeaderOptions(landingPage.getDriver());
		headerOptions.clickOnMyAccountDropMenu();
		Assert.assertFalse(headerOptions.isLogoutOptionAvaialable());
		
	}
	
	@Test(priority = 4)
	public void verifyAccountLogoutFunctionality() {
		
		loginPage = landingPage.navigateToLoginPage();
		accountPage = loginPage.loginToApplication(prop.getProperty("existingEmail"),prop.getProperty("validPassword"));
		headerOptions = new HeaderOptions(accountPage.getDriver());
		headerOptions.clickOnMyAccountDropMenu();
		accountLogoutPage = headerOptions.selectLogoutOption();
		Assert.assertTrue(accountLogoutPage.didWeNavigateToAccountLogoutPage());
		landingPage = accountLogoutPage.clickOnContinueButton();
		Assert.assertEquals(getPageURL(landingPage.getDriver()),prop.getProperty("landingPageURL"));
		headerOptions = new HeaderOptions(landingPage.getDriver());
		headerOptions.clickOnMyAccountDropMenu();
		Assert.assertTrue(headerOptions.isLoginOptionAvailable());
		
	}
	
}