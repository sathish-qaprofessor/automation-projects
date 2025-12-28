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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CssSelectorElementLocatorsStrategy {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://www.automationtesting.co.uk/");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void cssSelectorByClassStrategy() {

        // Using class in css selector
        String pageHeader = driver.findElement(By.cssSelector("div.content header h1")).getText();
        Assert.assertEquals(pageHeader, "Testing Arena", "Page header does not match!");
    }

    @Test
    public void cssSelectorByIdStrategy() {
        // Using id in css selector
        String sidebarHeader = driver.findElement(By.cssSelector("div#sidebar nav#menu header h2")).getText();
        Assert.assertEquals(sidebarHeader, "Menu", "Sidebar header text is incorrect!");
    }

    @Test
    public void cssSelectorGetMultipleElements() {
        List<String> expectedMenuItems = new ArrayList<>(List.of(
                "HOMEPAGE", "ACCORDION", "ACTIONS", "BROWSER TABS", "BUTTONS", "CALCULATOR (JS)",
                "CONTACT US FORM TEST", "DATE PICKER", "DROPDOWN CHECKBOX RADIO", "FILE UPLOAD",
                "HIDDEN ELEMENTS", "IFRAMES", "LOADER", "LOADER TWO", "LOGIN PORTAL TEST", "MOUSE MOVEMENT",
                "POP UPS & ALERTS", "PREDICTIVE SEARCH", "TABLES", "TEST STORE", "ABOUT ME"
                ));

        // Using attribute in css selector
        List<String> actualMenuItems = driver.findElements(By.cssSelector("nav#menu ul li a"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        System.out.println(actualMenuItems);

        expectedMenuItems.forEach(expected -> Assert.assertListContains(actualMenuItems,
                item -> item.equals(expected), "Menu item " + expected + " is missing!"));
        Assert.assertEquals(actualMenuItems, expectedMenuItems, "Menu items do not match!");
    }

    @Test
    public void cssSelectorByAttributeStrategy() {
        // Using attribute in css selector
        String futureSkillAcademyText = driver.findElement(By.cssSelector("a[href='https://futureskillzacademy.com/']")).getText();
        Assert.assertEquals(futureSkillAcademyText, "futureskillzacademy", "future skill academy text is incorrect!");
    }

    @Test void cssSelectorByPartialAttributeStrategy() {
        // Using partial attribute in css selector
        String contactUsText = driver.findElement(By.cssSelector("a[href*='contactForm']")).getText();
        Assert.assertEquals(contactUsText, "CONTACT US FORM TEST", "Contact Us text is incorrect!");
    }

    @Test
    public void cssSelectorByMultipleAttributesStrategy() {
        // Using multiple attributes in css selector
        driver.findElement(By.cssSelector("a[href='#sidebar'][class='toggle']")).click();
        Assert.assertTrue(driver.findElement(By.cssSelector("div#sidebar.inactive")).isDisplayed(), "Sidebar is displayed!");
        driver.findElement(By.cssSelector("a.toggle[href='#sidebar']")).click();
        Assert.assertTrue(driver.findElement(By.cssSelector("div#sidebar:not(.inactive)")).isDisplayed(), "Sidebar is not displayed!");
        // other way around
        Assert.assertTrue(driver.findElement(By.cssSelector("div#sidebar")).isDisplayed(), "Sidebar is not displayed!");
    }

    @Test
    public void cssSelectorByNthChildStrategy() {
        // Using nth-child in css selector
        String fourthMenuItemText = driver.findElement(By.cssSelector("nav#menu ul li:nth-child(4) a")).getText();
        Assert.assertEquals(fourthMenuItemText, "BROWSER TABS", "Fourth menu item text is incorrect!");
    }

    @Test
    public void cssSelectorByDirectChildStrategy() {
        // Using direct child selector in css selector
        String menuHeaderText = driver.findElement(By.cssSelector("nav#menu header h2")).getText();
        Assert.assertEquals(menuHeaderText, "Menu", "Menu header text is incorrect!");
    }

    @Test
    public void cssSelectorByAdjacentSiblingStrategy() {
        // Using adjacent sibling selector in css selector
        String secondMenuItemText = driver.findElement(By.cssSelector("nav#menu ul li:first-child + li a")).getText();
        Assert.assertEquals(secondMenuItemText, "ACCORDION", "Second menu item text is incorrect!");
    }

    @Test
    public void cssSelectorByAdjacentChildSiblingStrategy() {
        // Using general sibling selector in css selector
        String thirdMenuItemText = driver.findElement(By.cssSelector("nav#menu ul li:first-child ~ li:nth-child(3) a")).getText();
        Assert.assertEquals(thirdMenuItemText, "ACTIONS", "Third menu item text is incorrect!");
    }

    @Test
    public void cssSelectorByPseudoClassStrategy() {
        // Using pseudo-class in css selector
        String lastMenuItemText = driver.findElement(By.cssSelector("nav#menu ul li:last-child a")).getText();
        Assert.assertEquals(lastMenuItemText, "ABOUT ME", "Last menu item text is incorrect!");
    }

    @Test
    public void cssSelectorByContainsTextStrategy() {
        // Using contains text in css selector
        String loaderMenuItemText = driver.findElement(By.cssSelector("nav#menu ul li a[href*='loader']")).getText();
        Assert.assertEquals(loaderMenuItemText, "LOADER", "Loader menu item text is incorrect!");
    }

    @Test
    public void cssSelectorByStartsWithAttributeStrategy() {
        // Using starts-with attribute in css selector
        String testStoreMenuItemText = driver.findElement(By.cssSelector("nav#menu ul li a[href^='https://teststore.automationtesting']")).getText();
        Assert.assertEquals(testStoreMenuItemText, "TEST STORE", "Test Store menu item text is incorrect!");
    }

    @Test
    public void cssSelectorMultipleLocatorsStrategy() {
        // Using multiple locators in css selector
        // Uses either of the two locators
        // "nav#menu ul li a[href^='https://teststore.automationtesting'] or nav#menu ul li a[href='https://teststore.automationtesting.co.uk']"
        // to find the Test Store menu item
        String testStoreMenuItemText = driver.findElement(By.cssSelector(
                "nav#menu ul li a[href^='https://teststore.automationtesting'], nav#menu ul li a[href='https://teststore.automationtesting.co.uk']")).getText();
        Assert.assertEquals(testStoreMenuItemText, "TEST STORE", "Test Store menu item text is incorrect!");
    }
}

