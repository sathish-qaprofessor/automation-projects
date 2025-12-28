package org.basics.testngimpl;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class TestNGParameterizedExplicitWaitsTest {
    WebDriver driver;
    WebDriverWait wait;
    private String browser;

    @BeforeMethod
    @Parameters("browser")
    public void setup(@Optional("chrome") String browser) {
        this.browser = browser;
        if ("chrome".equals(browser)) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if ("firefox".equals(browser)) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if ("edge".equals(browser)) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testExplicitWaitForElementVisible() {
        driver.get("https://www.automationtesting.co.uk/loadertwo.html");
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p#appears")));
        Assert.assertTrue(element.isDisplayed(), "Element is not visible!");
        System.out.println("Element text: " + element.getText());
    }

    @Test
    public void testExplicitWaitForElementClickable() {
        driver.get("https://www.automationtesting.co.uk/buttons.html");
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.id("btn_two")));
        Assert.assertTrue(button.isEnabled(), "Button is not clickable!");
        button.click();
    }

    @Test
    public void testExplicitWaitForElementPresence() {
        driver.get("https://www.automationtesting.co.uk/loadertwo.html");
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("p#appears")));
        Assert.assertNotNull(element, "Element is not present!");
    }

    @Test
    public void testExplicitWaitForTextInElement() {
        driver.get("https://www.automationtesting.co.uk/loadertwo.html");
        Boolean textPresent = wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("p#appears"), "This is a new paragraph that appears after 8 seconds."));
        Assert.assertTrue(textPresent, "Text not present in element!");
    }

    @Test
    public void testExplicitWaitForAttributeContains() {
        driver.get("https://www.automationtesting.co.uk/loadertwo.html");
        Boolean attributeContains = wait.until(ExpectedConditions.attributeContains(By.cssSelector("p#appears"), "id", "appears"));
        Assert.assertTrue(attributeContains, "Attribute does not contain expected value!");
    }

    @Test
    public void testExplicitWaitForNumberOfElements() {
        driver.get("https://www.automationtesting.co.uk/loadertwo.html");
        List<WebElement> elements = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.tagName("p"), 1));
        Assert.assertTrue(elements.size() > 1, "Less than 2 paragraph elements found!");
    }

    @Test
    public void testExplicitWaitForAlertPresent() {
        driver.get("https://www.automationtesting.co.uk/popups.html");
        // Assuming an action triggers an alert; adjust as needed
        driver.findElement(By.xpath("//button[text()='Trigger Alert']")).click();
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertNotNull(alert, "Alert is not present!");
        alert.accept();
    }

    @Test
    public void testExplicitWaitForFrameAndSwitch() {
        driver.get("https://www.automationtesting.co.uk/iframes.html");
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("iframe[src='index.html']")));
        WebElement innerElement = driver.findElement(By.cssSelector(".toggle"));
        Assert.assertTrue(innerElement.isDisplayed(), "Element in frame is not displayed!");
        driver.switchTo().defaultContent();
    }

    @Test
    public void testExplicitWaitForTitleContains() {
        driver.get("https://www.automationtesting.co.uk/");
        Boolean titleContains = wait.until(ExpectedConditions.titleContains("Homepage"));
        Assert.assertTrue(titleContains, "Title does not contain 'Homepage'!");
    }

    @Test
    public void testExplicitWaitForUrlContains() {
        driver.get("https://www.automationtesting.co.uk/loadertwo.html");
        Boolean urlContains = wait.until(ExpectedConditions.urlContains("loadertwo"));
        Assert.assertTrue(urlContains, "URL does not contain 'loadertwo'!");
    }

    @Test
    public void testExplicitWaitForInvisibilityOfElement() {
        driver.get("https://www.automationtesting.co.uk/loadertwo.html");
        // Wait for an element to become invisible if applicable; otherwise, this might fail
        Boolean invisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("p.hidden")));
        Assert.assertTrue(invisible, "Element is still visible!");
    }

    @Test
    public void testExplicitWaitVisibilityOfWebElement() {
        driver.get("https://www.automationtesting.co.uk/loadertwo.html");
        WebElement element = driver.findElement(By.cssSelector("p#appears"));
        WebElement visibleElement = wait.until(ExpectedConditions.visibilityOf(element));
        Assert.assertTrue(visibleElement.isDisplayed(), "WebElement is not visible!");
        System.out.println("Visible element text: " + visibleElement.getText());
    }

    @Test
    public void testExplicitWaitElementToBeClickableWebElement() {
        driver.get("https://www.automationtesting.co.uk/buttons.html");
        WebElement button = driver.findElement(By.id("btn_one"));
        WebElement clickableButton = wait.until(ExpectedConditions.elementToBeClickable(button));
        Assert.assertTrue(clickableButton.isEnabled(), "WebElement is not clickable!");
        clickableButton.click();
    }

    @Test
    public void testExplicitWaitTextToBePresentInWebElement() {
        driver.get("https://www.automationtesting.co.uk/loadertwo.html");
        WebElement element = driver.findElement(By.cssSelector("p#appears"));
        Boolean textPresent = wait.until(ExpectedConditions.textToBePresentInElement(element, "This is a new paragraph that appears after 8 seconds."));
        Assert.assertTrue(textPresent, "'This is a new paragraph that appears after 8 seconds.' is not present!");
    }

    @Test
    public void testExplicitWaitAttributeToBeWebElement() {
        driver.get("https://www.automationtesting.co.uk/loadertwo.html");
        WebElement element = driver.findElement(By.cssSelector("p#appears"));
        Boolean attributeIs = wait.until(ExpectedConditions.attributeToBe(element, "innerText", "This is a new paragraph that appears after 8 seconds."));
        Assert.assertTrue(attributeIs, "Attribute is not 'appeared'!");
    }

    @Test
    public void testExplicitWaitInvisibilityOfWebElement() {
        driver.get("https://www.automationtesting.co.uk/actions.html");
        WebElement buttonElement = driver.findElement(By.id("click-box"));
        Actions actions = new Actions(driver);
        actions.clickAndHold(buttonElement).perform();
        // Hold for a few seconds to ensure the message appears
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Wait for the text to appear in the element
        Boolean textPresent = wait.until(ExpectedConditions.textToBePresentInElement(buttonElement, "Keep holding down!"));
        Assert.assertTrue(textPresent, "Text 'Keep holding down!' did not appear!");
        actions.release().perform();
        // Now wait for the text to disappear
        Boolean textGone = wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(buttonElement, "Keep holding down!")));
        Assert.assertTrue(textGone, "Text 'Keep holding down!' did not disappear after release!");
    }

    @Test
    public void testExplicitWaitStalenessOfWebElement() {
        driver.get("https://www.automationtesting.co.uk/loadertwo.html");
        WebElement element = driver.findElement(By.cssSelector("p#appears"));
        // Click something to make element stale, e.g., navigate away or reload
        driver.navigate().refresh();
        Boolean stale = wait.until(ExpectedConditions.stalenessOf(element));
        Assert.assertTrue(stale, "WebElement is not stale!");
    }
}
