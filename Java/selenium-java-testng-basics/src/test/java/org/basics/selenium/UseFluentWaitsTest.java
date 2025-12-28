package org.basics.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class UseFluentWaitsTest {
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
    public void testFluentWaitUsage() {
        driver.navigate().to("https://www.automationtesting.co.uk/loadertwo.html");

        // Implement Fluent Wait logic here as needed
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);

        // Example usage of wait can be added here
        WebElement element = wait.until(driver -> {
            WebElement elem = driver.findElement(By.cssSelector("p#appears"));
            return elem.isDisplayed() ? elem : null;
        });

        Assert.assertNotNull(element);
        System.out.println("Element found and the text is: " + element.getText());
    }

    @Test
    public void testFluentWaitForElementClickable() {
        driver.navigate().to("https://www.automationtesting.co.uk/loadertwo.html");

        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(ElementNotInteractableException.class);

        WebElement button = wait.until(driver -> {
            WebElement btn = driver.findElement(By.id("loaderBtn"));
            return btn.isEnabled() && btn.isDisplayed() ? btn : null;
        });

        Assert.assertNotNull(button, "Button is not clickable!");
        System.out.println("Button is clickable: " + button.getText());
    }

    @Test
    public void testFluentWaitForTextPresence() {
        driver.navigate().to("https://www.automationtesting.co.uk/loadertwo.html");

        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);

        Boolean textPresent = wait.until(driver -> {
            WebElement elem = driver.findElement(By.cssSelector("p#appears"));
            return elem.getText().contains("This text has appeared");
        });

        Assert.assertTrue(textPresent, "Expected text did not appear!");
    }

    @Test
    public void testFluentWaitForAttributeValue() {
        driver.navigate().to("https://www.automationtesting.co.uk/loadertwo.html");

        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);

        String attributeValue = wait.until(driver -> {
            WebElement elem = driver.findElement(By.cssSelector("p#appears"));
            String attr = elem.getAttribute("class");
            return attr != null && attr.equals("appeared") ? attr : null;
        });

        Assert.assertEquals(attributeValue, "appeared", "Attribute value does not match!");
    }

    @Test
    public void testFluentWaitForNumberOfElements() {
        driver.navigate().to("https://www.automationtesting.co.uk/loadertwo.html");

        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);

        Integer elementCount = wait.until(driver -> {
            int count = driver.findElements(By.cssSelector("p")).size();
            return count > 1 ? count : null;
        });

        Assert.assertTrue(elementCount > 1, "Expected more than 1 paragraph element!");
        System.out.println("Number of paragraph elements: " + elementCount);
    }

    @Test
    public void testFluentWaitForAlertPresence() {
        driver.navigate().to("https://www.automationtesting.co.uk/loadertwo.html");

        // Assuming the page triggers an alert after some action, e.g., clicking a button
        driver.findElement(By.id("loaderBtn")).click(); // Trigger action if needed

        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoAlertPresentException.class);

        Alert alert = wait.until(driver -> {
            try {
                return driver.switchTo().alert();
            } catch (NoAlertPresentException e) {
                return null;
            }
        });

        Assert.assertNotNull(alert, "Alert did not appear!");
        alert.accept();
    }

    @Test
    public void testFluentWaitCustomCondition() {
        driver.navigate().to("https://www.automationtesting.co.uk/loadertwo.html");

        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(Exception.class); // Ignore all exceptions for custom condition

        String result = wait.until(driver -> {
            WebElement elem = driver.findElement(By.cssSelector("p#appears"));
            if (elem.isDisplayed() && elem.getText().length() > 10) {
                return "Condition met: " + elem.getText();
            }
            return null;
        });

        Assert.assertNotNull(result, "Custom condition not met!");
        System.out.println(result);
    }
}
