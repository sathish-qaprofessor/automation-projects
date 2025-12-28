package org.basics.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.stream.Collectors;

public class DropdownSelectOptionTest {
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
    public void testDropdownSelectMercedes() throws InterruptedException {
        driver.get("https://www.automationtesting.co.uk");
        driver.findElement(By.partialLinkText("DROPDOWN CHECKBOX")).click();

        Select menuItem = new Select(driver.findElement(By.id("cars")));
        menuItem.selectByVisibleText("Mercedes");
        String selected = menuItem.getFirstSelectedOption().getText();
        Assert.assertEquals(selected, "Mercedes", "Selected option should be 'Mercedes'!");
        String allOptions = menuItem.getOptions().stream().map(WebElement::getText).collect(Collectors.joining(", "));
        Assert.assertTrue(allOptions.contains("Mercedes"), "Dropdown options should contain 'Mercedes'!");
    }
}
