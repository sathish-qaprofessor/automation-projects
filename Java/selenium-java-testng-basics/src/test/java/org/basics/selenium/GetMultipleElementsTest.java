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
import java.util.List;
import java.util.stream.Collectors;

public class GetMultipleElementsTest {
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
	public void testGetMultipleMenuItems() {
		driver.navigate().to("https://www.automationtesting.co.uk");
		List<WebElement> getAllMenuItems = driver.findElements(By.cssSelector("nav#menu ul li a"));
		List<String> menuItems = getAllMenuItems.stream().map(WebElement::getText).collect(Collectors.toList());
		Assert.assertFalse(menuItems.isEmpty(), "No menu items found!");
		// Click the first menu item as an example
		if (!menuItems.isEmpty()) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click()", driver.findElement(By.linkText(menuItems.get(0))));
			driver.navigate().back();
		}
	}
}
