package org.basics.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class ClickAndHoldElementTest {

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
	public void testClickAndHoldAction() {
		driver.navigate().to("https://www.automationtesting.co.uk");
		driver.findElement(By.linkText("ACTIONS")).click();

		Actions actions = new Actions(driver);
		WebElement holdButton = driver.findElement(By.className("action_start_text"));
		actions.clickAndHold(holdButton).perform();
		String holdText = driver.findElement(By.id("click-box")).getText();
		Assert.assertTrue(holdText.toLowerCase().contains("hold"), "Text should indicate holding: " + holdText);

		actions.release().perform();
		String releaseText = driver.findElement(By.id("click-box")).getText();
		Assert.assertFalse(releaseText.toLowerCase().contains("hold"), "Text should indicate release: " + releaseText);
	}
}
