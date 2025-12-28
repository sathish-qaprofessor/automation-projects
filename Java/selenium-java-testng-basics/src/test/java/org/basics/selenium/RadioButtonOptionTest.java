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

public class RadioButtonOptionTest {
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
    public void testRadioButtonSelection() {
        driver.get("https://www.automationtesting.co.uk");
        driver.findElement(By.partialLinkText("DROPDOWN CHECKBOX")).click();

        boolean isSelectedBefore = driver.findElement(By.cssSelector("input#demo-priority-normal")).isSelected();
        Assert.assertFalse(isSelectedBefore, "Radio button should not be selected initially!");
        driver.findElement(By.cssSelector("label[for='demo-priority-normal']")).click();
        boolean isSelectedAfter = driver.findElement(By.xpath("//input[@id='demo-priority-normal']")).isSelected();
        Assert.assertTrue(isSelectedAfter, "Radio button should be selected after clicking!");
    }
}
