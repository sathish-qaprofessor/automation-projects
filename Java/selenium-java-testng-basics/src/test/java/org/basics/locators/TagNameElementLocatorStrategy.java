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
import java.util.List;

public class TagNameElementLocatorStrategy {
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
    public void tagNameStrategy() {
        // Find all <a> tags
        List<WebElement> links = driver.findElements(By.tagName("a"));
        Assert.assertTrue(links.size() > 0, "No <a> tags found!");
        // Check at least one link is displayed
        boolean anyDisplayed = links.stream().anyMatch(WebElement::isDisplayed);
        Assert.assertTrue(anyDisplayed, "No <a> tags are displayed!");
    }
}

