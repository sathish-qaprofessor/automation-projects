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

public class AccordionsTest {
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
	public void testAccordionHeadersAndContents() throws InterruptedException {
		driver.navigate().to("https://www.automationtesting.co.uk");

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click()", driver.findElement(By.partialLinkText("ACCORDION")));

		List<String> accordionHeaders = driver.findElements(By.cssSelector("div.accordion-header")).stream()
				.map(WebElement::getText).toList();

		Assert.assertFalse(accordionHeaders.isEmpty(), "No accordion headers found!");

		for (String header : accordionHeaders) {
			driver.findElement(By.xpath(String.format("//div[text()='%s']", header))).click();
			String content = driver.findElement(By.xpath(String.format("//div[text()='%s']/following-sibling::div", header))).getText();
			Assert.assertFalse(content.isEmpty(), "Accordion content is empty for header: " + header);
		}
	}
}
