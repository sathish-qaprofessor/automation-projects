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

public class IFrameContentsTest {
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
	public void testIFrameSwitching() {
		driver.navigate().to("https://www.automationtesting.co.uk");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click()", driver.findElement(By.linkText("IFRAMES")));

		WebElement iFrameElement = driver.findElement(By.cssSelector("iframe[src='index.html']"));
		driver.switchTo().frame(iFrameElement);
		WebElement toggleInFrame = driver.findElement(By.cssSelector(".toggle"));
		Assert.assertTrue(toggleInFrame.isDisplayed(), "Toggle in iframe is not displayed!");
		toggleInFrame.click();
		driver.switchTo().defaultContent();
		WebElement toggleOutside = driver.findElement(By.cssSelector(".toggle"));
		Assert.assertTrue(toggleOutside.isDisplayed(), "Toggle outside iframe is not displayed!");
	}
}
