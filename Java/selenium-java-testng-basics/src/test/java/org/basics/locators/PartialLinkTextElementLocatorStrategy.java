package org.basics.locators;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class PartialLinkTextElementLocatorStrategy {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://www.automationtesting.co.uk/");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void partialLinkTextStrategy() {
        // Find link by partial visible text
        WebElement link = driver.findElement(By.partialLinkText("Test"));
        Assert.assertTrue(link.getText().contains("Test"), "Link text does not contain 'Test'!");
        Assert.assertTrue(link.isDisplayed(), "Link is not displayed!");
    }
}

