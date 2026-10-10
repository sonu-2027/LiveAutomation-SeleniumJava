package tutorialsninja.tests;

import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.AccountPage;
import pages.LandingPage;
import pages.LoginPage;

import org.openqa.selenium.WebDriver;

import tutorialsninja.base.Base;
import utils.CommonUtils;

public class Login extends Base {
	
	WebDriver driver;
	Properties prop;
	LandingPage landingPage;
	LoginPage loginPage;
	AccountPage accountPage;
	 
	@BeforeMethod
	public void setup() {
		driver = openBrowserAndApplication();
		prop = CommonUtils.loadProperties();
		landingPage = new LandingPage(driver);
		landingPage.clickOnMyAccount();
		loginPage = landingPage.selectLoginOption();	
		
	}
	
	@AfterMethod
	public void tearDown() {
		if(driver!=null) {
			driver.quit();
		}
	}
	
	@Test(priority=1)
	public void verifyLoginWithValidCredentials() {
		
		Assert.assertTrue(loginPage.didWeNaviateToLoginPage());
		loginPage.enterEmail(prop.getProperty("existingEmail"));
		loginPage.enterPassword(prop.getProperty("validPassword"));
		accountPage = loginPage.clickOnLoginButton();
		Assert.assertTrue(accountPage.isUserLoggedIn());
		Assert.assertTrue(accountPage.didWenavigateToAccountPage());
		
	}
}
