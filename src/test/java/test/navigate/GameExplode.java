package test.navigate;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;

public class GameExplode {

	public static void main(String[] args) throws IOException, InterruptedException, TesseractException {
		navigateGameExplode();
	}

	public static void navigateGameExplode() throws IOException, InterruptedException, TesseractException {
		WebDriver driver = null;
		String homePath = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", homePath + "\\Drivers\\chromedriver.exe");

		// Create a map to store preferences
		Map<String, Object> prefs = new HashMap<String, Object>();

		// add key and value to map as follow to switch off browser notification
		// Pass the argument 1 to allow and 2 to block
		prefs.put("profile.default_content_setting_values.notifications", 1);

		// Create an instance of ChromeOptions
		ChromeOptions options = new ChromeOptions();

		// set ExperimentalOption - prefs
		options.setExperimentalOption("prefs",prefs);

		driver = new ChromeDriver(options);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get("https://sso.us.lg.com/oauth/page/login?authorizeKey=0c55d88a-43f3-4f0e-a52a-ac49d92caf05");
		Thread.sleep(5000);

		FluentWait wait = new FluentWait<WebDriver>(driver).withTimeout(35, TimeUnit.SECONDS).pollingEvery(3, TimeUnit.SECONDS).ignoring(Exception.class);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@type='email'])[1]")));
		File src=driver.findElement(By.id("customizedCaptcha_CaptchaImage")).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir")+"/screenshots/captcha.png";
		FileHandler.copy(src, new File(path));		
		Thread.sleep(5000);
		
		ITesseract image = new Tesseract();
		
		image.setDatapath(System.getProperty("user.dir")+"/tessdata/");
        image.setLanguage("eng");
        String imageText=image.doOCR(new File(path));
		System.out.println(imageText);
		
	}
}
