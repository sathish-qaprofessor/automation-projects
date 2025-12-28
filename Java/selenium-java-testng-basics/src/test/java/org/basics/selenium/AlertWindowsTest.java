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

public class AlertWindowsTest {
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
    public void testAlertWindowAccept() {
        driver.get("https://www.automationtesting.co.uk");
        driver.findElement(By.linkText("BUTTONS")).click();
        driver.findElement(By.id("btn_one")).click();
        String alertText = driver.switchTo().alert().getText();
        Assert.assertNotNull(alertText, "Alert text should not be null!");
        driver.switchTo().alert().accept();
    }

    @Test
    public void testAlertWindowDismiss() {
        driver.get("https://www.automationtesting.co.uk");
        driver.findElement(By.linkText("BUTTONS")).click();
        driver.findElement(By.id("btn_one")).click();
        String alertText = driver.switchTo().alert().getText();
        Assert.assertNotNull(alertText, "Alert text should not be null!");
        driver.switchTo().alert().dismiss();
    }
}
