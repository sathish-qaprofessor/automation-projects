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

public class NameElementLocatorStrategy {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://www.automationtesting.co.uk/contactForm.html");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void nameStrategy() {
        // Find input by name attribute (e.g., name='first_name')
        WebElement firstNameInput = driver.findElement(By.name("first_name"));
        Assert.assertEquals(firstNameInput.getAttribute("name"), "first_name", "Input name attribute does not match!");
        Assert.assertTrue(firstNameInput.isDisplayed(), "First name input is not displayed!");
    }


}

