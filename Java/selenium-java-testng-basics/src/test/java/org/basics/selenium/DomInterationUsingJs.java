package org.basics.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class DomInterationUsingJs {

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
    public void testDomInteractionUsingJs() {
        driver.navigate().to("https://www.automationtesting.co.uk");
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Get text/content of the element
        System.out.println((String) js.executeScript("return document.querySelector('.content').innerText"));

        // Alternative to Xpath, Get webelement by text and perform click
        js.executeScript(
                "Array.from(document.querySelectorAll('header p a')).find(el => el.textContent.trim() === 'webdriveruniversity').click();");

        driver.navigate().back();

        // Scrolling to a element down the page and perform click
        js.executeScript(
                "document.querySelector(arguments[0]).scrollIntoView({ behavior: 'smooth', block: 'center' })",
                "a[href='aboutMe.html']");
        js.executeScript("document.querySelector(arguments[0]).click()", "a[href='aboutMe.html']");

        driver.navigate().back();

        js.executeScript("arguments[0].click()", driver.findElement(By.linkText("BUTTONS")));
        System.out.println("Is button four disabled? "
                + (boolean) js.executeScript("return document.getElementById('btn_four').disabled"));

        driver.navigate().back();

        /*
         * // If this still doesn’t open the submenu, it might be using a CSS :hover
         * effect rather than JS events js.executeScript("arguments[0].click()",
         * driver.findElement(By.partialLinkText("DROPDOWN CHECKBOX"))); js.
         * executeScript("arguments[0].dispatchEvent(new MouseEvent('mousemove', {bubbles: true, cancelable: true}));"
         * +
         * "arguments[0].dispatchEvent(new MouseEvent('mouseenter', {bubbles: true, cancelable: true}));"
         * , driver.findElement(By.xpath("//a[normalize-space(text())='Animals']")));
         * Thread.sleep(5000);
         */
    }

    @Test
    public void testGetElementTextByJs() {
        driver.navigate().to("https://www.automationtesting.co.uk");
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Get text/content of the element using JS
        js.executeScript("arguments[0].click()", driver.findElement(By.linkText("TEST STORE")));

        WebElement productPriceTag = driver.findElement(By.xpath(
                "//a[text()='Hummingbird printed t-shirt']/ancestor::div[@class='product-description']/div/span"));
        String getPriceByJs = (String) js.executeScript("return arguments[0].textContent;", productPriceTag);
        System.out.println("The product 'Hummingbird printed t-shirt' price is " + getPriceByJs);
        Assert.assertEquals(getPriceByJs, "$23.90", "The product 'Hummingbird printed t-shirt' price does not match!");

        String getPriceFromInnerText = (String) js.executeScript("return arguments[0].innerText", productPriceTag);
        System.out.println("The product 'Hummingbird printed t-shirt' price is " + getPriceFromInnerText);
        Assert.assertEquals(getPriceFromInnerText, "$23.90", "The product 'Hummingbird printed t-shirt' price does not match!");
        Assert.assertEquals(productPriceTag.getText(), "$23.90", "The product 'Hummingbird printed t-shirt' price does not match!");
    }

    @Test
    public void testSendTextByJS() {
        driver.get("https://www.automationtesting.co.uk");
        driver.findElement(By.partialLinkText("CONTACT US FORM")).click();

        driver.findElement(By.name("first_name")).sendKeys("Tommy");
        driver.findElement(By.name("last_name")).sendKeys("McQuirre");

        String commentText = "I am excited to reach out to your company to know more about your product and services.";
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("arguments[0].value = arguments[1]", driver.findElement(By.name("email")), "tom.mcquirre@gmail.com");
        js.executeScript("arguments[0].value = arguments[1]", driver.findElement(By.name("message")), commentText);

        String userComment = (String) js.executeScript("return arguments[0].value", driver.findElement(By.name("message")));
        Assert.assertEquals(userComment, commentText, "Comment provided by user does not match!");
    }
}
