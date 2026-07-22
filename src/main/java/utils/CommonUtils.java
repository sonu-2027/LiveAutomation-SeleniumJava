package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;


public class CommonUtils {

	public static WebDriver takeScreenshot(WebDriver driver, String pathToBCopied) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File srcScreenshot = ts.getScreenshotAs(OutputType.FILE);
		try {
			FileHandler.copy(srcScreenshot, new File(System.getProperty("user.dir") + pathToBCopied));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return driver;
	}
	
	public static String takeScreenshotAndReturnPath(WebDriver driver, String pathToBCopied) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File srcScreenshot = ts.getScreenshotAs(OutputType.FILE);
		String destScreenshotPath = System.getProperty("user.dir") + pathToBCopied;
		try {
			FileHandler.copy(srcScreenshot, new File(destScreenshotPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destScreenshotPath;
	}

		
}