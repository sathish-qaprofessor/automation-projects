package org.basics.drivers;

import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class WebDriverFactory {

    private static final ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
    public static BrowserName browser;

    /**
     * Returns WebDriver instance
     */
    public static WebDriver getDriver() {
        if (webDriver.get() == null) {
            try {
                webDriver.set(WebDriverCreator.createWebDriver(browser));
            } catch (IOException e) {
            }
        }
        return webDriver.get();
    }

    public static void quitWebDriver() {
        if (webDriver.get() != null) {
            webDriver.get().quit();
            webDriver.remove();
        }
    }
}
