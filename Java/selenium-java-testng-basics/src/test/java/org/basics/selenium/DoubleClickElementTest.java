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

public class DoubleClickElementTest {
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
    public void testDoubleClickAction() {
        driver.navigate().to("https://www.automationtesting.co.uk");
        driver.findElement(By.linkText("ACTIONS")).click();

        Actions actions = new Actions(driver);
        WebElement doubleClickElement = driver.findElement(By.id("doubClickStartText"));
        String beforeText = doubleClickElement.getText();
        actions.doubleClick(doubleClickElement).perform();
        String afterText = doubleClickElement.getText();
        Assert.assertNotEquals(afterText, beforeText, "Text should change after double click!");
    }
}
