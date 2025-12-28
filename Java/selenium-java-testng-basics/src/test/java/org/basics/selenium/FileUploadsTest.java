package org.basics.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class FileUploadsTest {
	WebDriver driver;

	@BeforeMethod
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	public void testFileUpload() {
        String filePath = System.getProperty("user.dir") + "\\fileuploads\\sample-pdf-file.pdf";
		driver.navigate().to("https://www.automationtesting.co.uk");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click()", driver.findElement(By.partialLinkText("FILE UPLOAD")));

		WebElement fileInput = driver.findElement(By.id("fileToUpload"));
		js.executeScript("arguments[0].style.display='block';", fileInput);
		fileInput.sendKeys(filePath);
		String uploadedFile = fileInput.getAttribute("value");
		Assert.assertFalse(uploadedFile.isEmpty(), "File upload failed, value is empty!");
	}



	@Test
	public void testFileUploadWithDifferentFile() {
		String imagePath = System.getProperty("user.dir") + "\\fileuploads\\sample-image.jpg"; // Assume this file exists
		driver.navigate().to("https://www.automationtesting.co.uk");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click()", driver.findElement(By.partialLinkText("FILE UPLOAD")));

		WebElement fileInput = driver.findElement(By.id("fileToUpload"));
		js.executeScript("arguments[0].style.display='block';", fileInput);
		fileInput.sendKeys(imagePath);
		String uploadedFile = fileInput.getAttribute("value");
		Assert.assertFalse(uploadedFile.isEmpty(), "File upload with different file failed, value is empty!");
		Assert.assertTrue(uploadedFile.contains("sample-image.jpg"), "Uploaded file name does not match!");
	}
}
