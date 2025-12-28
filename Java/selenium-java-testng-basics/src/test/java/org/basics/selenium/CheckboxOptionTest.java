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

public class CheckboxOptionTest {
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
    public void testRedCheckboxSelection() {
        driver.get("https://www.automationtesting.co.uk");
        driver.findElement(By.partialLinkText("DROPDOWN CHECKBOX")).click();
        boolean isSelected = driver.findElement(By.cssSelector("input#cb_red")).isSelected();
        // Assert initial state (selected or not)
        Assert.assertTrue(isSelected, "Red checkbox should be selected initially!");

        // Click to select/unselect the checkbox
        driver.findElement(By.cssSelector("label[for='cb_red']")).click();
        boolean isUnselected = driver.findElement(By.cssSelector("input#cb_red")).isSelected();
        // Assert state after click (should be toggled)
        Assert.assertFalse(isUnselected, "Red checkbox should not be selected after clicking the label!");
    }
}
