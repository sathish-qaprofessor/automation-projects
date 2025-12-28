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

public class DragAndDropElements {
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
    public void testDragAndDrop() throws InterruptedException {
        driver.navigate().to("https://www.automationtesting.co.uk");
        driver.findElement(By.linkText("ACTIONS")).click();

        Actions actions = new Actions(driver);
        WebElement source = driver.findElement(By.xpath("//div[@class='row']/div/div/p"));
        WebElement target = driver.findElement(By.xpath("//div[@class='row']/div/div[2]"));
        actions.dragAndDrop(source, target).perform();
        String droppedText = driver.findElement(By.xpath("//div[@class='row']/div/div[2]/p")).getText();
        Assert.assertFalse(droppedText.isEmpty(), "Dropped text should not be empty!");
    }
}
