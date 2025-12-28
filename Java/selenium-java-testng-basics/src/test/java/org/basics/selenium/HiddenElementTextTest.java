package org.basics.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class HiddenElementTextTest {
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
	public void testHiddenElementText() throws InterruptedException {
		driver.navigate().to("https://www.automationtesting.co.uk");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click()", driver.findElement(By.linkText("HIDDEN ELEMENTS")));

		String hiddenText = (String) js.executeScript("return arguments[0].textContent",
				driver.findElement(By.cssSelector("p[hidden]")));
		Assert.assertFalse(hiddenText.isEmpty(), "Hidden element text is empty!");
	}
}
