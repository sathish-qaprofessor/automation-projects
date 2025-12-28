package org.basics.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.time.Duration;

public class FileDownloadsTest {
	WebDriver driver;
	String downloadPath;

	@BeforeMethod
	public void setup() {
		WebDriverManager.chromedriver().setup();
		downloadPath = System.getProperty("user.dir") + "\\downloads";
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("download.default_directory", downloadPath);
		prefs.put("download.prompt_for_download", false);
		prefs.put("profile.default_content_setting_values.automatic_downloads", 1);
		prefs.put("plugins.always_open_pdf_externally", true);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);
		options.addArguments("--start-maximized");
		options.addArguments("--disable-infobars");
		options.addArguments("--disable-extensions");
		options.addArguments("--disable-popup-blocking");
		options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
		options.setExperimentalOption("useAutomationExtension", false);
		//options.addArguments("--headless=new");
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
	}

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	public void testFileDownload() throws InterruptedException {
		File file = new File(downloadPath + "\\sample-pdf-file.pdf");
		clearOldFile(file);
		driver.get("https://www.learningcontainer.com/sample-pdf-files-for-testing/");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click()", driver.findElement(By.cssSelector("a.wpdm-download-link.download-on-click")));
		boolean downloaded = isFileDownloaded(downloadPath, file, 30);
		Assert.assertTrue(downloaded, "Download failed for sample-pdf-file.pdf");
	}

	public void clearOldFile(File file) {
		if (file.exists()) {
			file.delete();
		}
	}

	public boolean isFileDownloaded(String downloadPath, File file, int timeoutSec) throws InterruptedException {
		int waited = 0;
		while (waited < timeoutSec) {
			if (file.exists() && !file.getName().endsWith(".crdownload")) {
				return true;
			}
			Thread.sleep(1000);
			waited++;
		}
		return false;
	}
}
