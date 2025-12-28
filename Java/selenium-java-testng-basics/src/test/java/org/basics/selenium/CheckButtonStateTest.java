package org.basics.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class CheckButtonStateTest {
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
    public void testFourthButtonEnabledState() {
        driver.get("https://www.automationtesting.co.uk");
        driver.findElement(By.linkText("BUTTONS")).click();
        boolean isEnabled = driver.findElement(By.id("btn_four")).isEnabled();
        // Assert the enabled/disabled state (update as per actual site behavior)
        Assert.assertFalse(isEnabled, "The fourth button should not be enabled!");
    }
}
