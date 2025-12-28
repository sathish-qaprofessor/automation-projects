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

public class PredictiveTextSearchTest {
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
	public void testPredictiveSearch() {
		driver.navigate().to("https://www.automationtesting.co.uk");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click()", driver.findElement(By.linkText("PREDICTIVE SEARCH")));

		String inputCountry = "Canada";
		driver.findElement(By.id("myInput")).sendKeys(inputCountry);
		List<WebElement> suggestions = driver.findElements(By.cssSelector("#myInputautocomplete-list div"));
		suggestions.stream().filter(x -> x.getText().equals(inputCountry)).findFirst().ifPresent(WebElement::click);

		String selected = driver.findElement(By.id("myInput")).getAttribute("value");
		Assert.assertEquals(selected, inputCountry, "Selected country does not match!");
		driver.findElement(By.xpath("//button[normalize-space(text())='submit']")).click();
		String info = driver.findElement(By.cssSelector("div#info-div div#info")).getText();
		Assert.assertFalse(info.isEmpty(), "Info text is empty after submission!");
	}
}
