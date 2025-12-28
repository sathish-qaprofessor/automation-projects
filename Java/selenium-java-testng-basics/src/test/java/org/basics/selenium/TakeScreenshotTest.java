package org.basics.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;

public class TakeScreenshotTest {
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
	public void testTakeScreenshot() throws IOException {
		driver.get("https://www.automationtesting.co.uk");
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileHandler.copy(src, new File(System.getProperty("user.dir") + "\\screenshots\\screenshot.png"));

		String timestamp = LocalDateTime.now().toString().replace(":", "-");
		String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
		File methodSrc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileHandler.copy(methodSrc, new File(System.getProperty("user.dir")
				+ String.format("\\screenshots\\%s_%s_screenshot.png", methodName, timestamp)));

		File postmanImage = driver.findElement(By.cssSelector("img[src='images/postman.png']")).getScreenshotAs(OutputType.FILE);
		FileHandler.copy(postmanImage, new File(System.getProperty("user.dir")
				+ String.format("\\screenshots\\%s_%s_screenshot.png", "Postman", timestamp)));

		String postmanBase64 = driver.findElement(By.cssSelector("img[src='images/postman.png']")).getScreenshotAs(OutputType.BASE64);
		System.out.println(postmanBase64);

		byte[] decodedBytes = Base64.getDecoder().decode(postmanBase64);

        try (FileOutputStream fos = new FileOutputStream(
                System.getProperty("user.dir") + "\\screenshots\\postman.png")) {
            fos.write(decodedBytes);
            fos.flush();
        }

		System.out.println("Image saved successfully!");
		Assert.assertTrue(new File(System.getProperty("user.dir") + "\\screenshots\\screenshot.png").exists(),
				"Screenshot file was not created!");
	}
}
