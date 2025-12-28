package org.basics.drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public class WebDriverCreator {

    /**
     * Create WebDriver instance
     */
    public static WebDriver createWebDriver(BrowserName browser) throws IOException {
        WebDriver driver = createLocalWebDriver(browser);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        return driver;
    }

    /**
     * Create local WebDriver instance
     */
    private static WebDriver createLocalWebDriver(BrowserName browser) {
        return switch (browser) {
            case CHROME -> createChromeDriver();
            case FIREFOX -> createFirefoxDriver();
            case EDGE -> createEdgeDriver();
        };
    }


    private static WebDriver createChromeDriver() {
        try {
            WebDriverManager.chromedriver().setup();
        } catch (Exception e) {
            System.out.println("WebDriverManager failed to setup ChromeDriver, falling back to local driver.");
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver.exe");
        }
        ChromeOptions options = ChromiumOptions.configure(new ChromeOptions());
        options.setExperimentalOption("useAutomationExtension", false);
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(ChromeOptions.CAPABILITY, options);
        caps.setCapability("browserName", "chrome");
        options.merge(caps);
        return new ChromeDriver(options);
    }

    private static WebDriver createEdgeDriver() {
        try {
            WebDriverManager.edgedriver().setup();
        } catch (Exception e) {
            System.out.println("WebDriverManager failed to setup EdgeDriver, falling back to local driver.");
            System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "/drivers/msedgedriver.exe");
        }
        EdgeOptions options = ChromiumOptions.configure(new EdgeOptions());
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(EdgeOptions.CAPABILITY, options);
        caps.setCapability("browserName", "MicrosoftEdge");
        options.merge(caps);
        return new EdgeDriver(options);
    }

    private static WebDriver createFirefoxDriver() {
        try {
            WebDriverManager.firefoxdriver().setup();
        } catch (Exception e) {
            System.out.println("WebDriverManager failed to setup GeckoDriver, falling back to local driver.");
            System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/drivers/geckodriver.exe");
        }
        FirefoxOptions options = configureFirefoxOptions();
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
        caps.setCapability("browserName", "firefox");
        options.merge(caps);

        return new FirefoxDriver(options);
    }

    private static FirefoxOptions configureFirefoxOptions() {
        Path downloadDir = Paths.get(System.getProperty("user.dir"), "drivers");
        try {
            Files.createDirectories(downloadDir);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create download directory: " + downloadDir, e);
        }

        FirefoxOptions options = new FirefoxOptions();

        // Firefox Profile preferences for downloads
        options.addPreference("browser.download.folderList", 2);
        options.addPreference("browser.download.dir", downloadDir.toString());
        options.addPreference("browser.download.useDownloadDir", true);
        options.addPreference("browser.download.manager.showWhenStarting", false);
        options.addPreference("browser.download.manager.focusWhenStarting", false);
        options.addPreference("browser.download.manager.closeWhenDone", true);
        options.addPreference("browser.helperApps.neverAsk.saveToDisk", 
            "application/pdf,application/octet-stream,application/zip,application/x-zip,application/x-zip-compressed," +
            "text/csv,text/plain,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet," +
            "application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document," +
            "image/png,image/jpeg,image/jpg,image/gif,application/x-rar-compressed");
        options.addPreference("browser.helperApps.alwaysAsk.force", false);
        options.addPreference("pdfjs.disabled", true);

        // Popup and notification preferences
        options.addPreference("dom.webnotifications.enabled", false);
        options.addPreference("dom.push.enabled", false);
        options.addPreference("dom.disable_open_during_load", true);
        options.addPreference("privacy.popups.showBrowserMessage", false);

        // Security and privacy preferences
        options.addPreference("security.insecure_field_warning.contextual.enabled", false);
        options.addPreference("security.insecure_password.ui.enabled", false);
        options.addPreference("datareporting.policy.dataSubmissionEnabled", false);
        options.addPreference("datareporting.healthreport.uploadEnabled", false);
        options.addPreference("toolkit.telemetry.enabled", false);

        // Performance and automation preferences
        options.addPreference("browser.cache.disk.enable", false);
        options.addPreference("browser.cache.memory.enable", false);
        options.addPreference("browser.cache.offline.enable", false);
        options.addPreference("network.http.use-cache", false);
        options.addPreference("dom.webdriver.enabled", true);
        options.addPreference("useAutomationExtension", false);

        // Page load strategy
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        // Accept insecure certificates
        options.setAcceptInsecureCerts(false);
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        options.addArguments("--disable-blink-features=AutomationControlled");


        return options;
    }

}
