package org.basics.drivers;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class TestStartBrowsers {

    @Test
    public void testChromeBrowser() {
        BrowserName browser = BrowserName.getBrowser("chrome");
        WebDriverFactory.browser = browser;
        WebDriver driver = WebDriverFactory.getDriver();
        driver.get("https://www.automationtesting.co.uk");
        System.out.println("Title: " + driver.getTitle());
        WebDriverFactory.quitWebDriver();
    }

    @Test
    public void testEdgeBrowser() {
        BrowserName browser = BrowserName.getBrowser("edge");
        WebDriverFactory.browser = browser;
        WebDriver driver = WebDriverFactory.getDriver();
        driver.get("https://www.automationtesting.co.uk");
        System.out.println("Title: " + driver.getTitle());
        WebDriverFactory.quitWebDriver();
    }

    @Test
    public void testFirfoxBrowser() {
        BrowserName browser = BrowserName.getBrowser("firefox");
        WebDriverFactory.browser = browser;
        WebDriver driver = WebDriverFactory.getDriver();
        driver.get("https://www.automationtesting.co.uk");
        System.out.println("Title: " + driver.getTitle());
        WebDriverFactory.quitWebDriver();
    }
}
