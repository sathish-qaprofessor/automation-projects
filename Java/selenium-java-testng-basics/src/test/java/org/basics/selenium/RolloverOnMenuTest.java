package org.basics.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class RolloverOnMenuTest {
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
	public void testRolloverOnMenu() {
		driver.navigate().to("https://www.automationtesting.co.uk");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click()", driver.findElement(By.partialLinkText("DROPDOWN CHECKBOX")));

		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(By.xpath("//a[normalize-space(text())='Animals']"))).perform();
		WebElement subMenuItem = driver.findElement(By.xpath("//a[normalize-space(text())='Cat']"));
		actions.moveToElement(subMenuItem).perform();
		subMenuItem.click();
		String message = driver.findElement(By.id("outputMessage")).getText();
		Assert.assertEquals(message, "You clicked on menu option 'Cat'", "Mouse roll over message is incorrect!");
		actions.moveToElement(driver.findElement(By.xpath("//a[normalize-space(text())='Sports']"))).perform();
	}
}
