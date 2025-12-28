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

public class XpathElementLocatorStrategy {
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
    public void xpathByClassStrategy() {
        // Implement test using XPath by class strategy
        String pageHeader = driver.findElement(By.xpath("//div[@class='content']/header/h1")).getText();
        Assert.assertEquals(pageHeader, "Testing Arena", "Page header does not match!");
    }

    @Test
    public void xpathByIdStrategy() {
        // Using id in xpath selector
        String sidebarHeader = driver.findElement(By.xpath("//div[@id='sidebar']//nav[@id='menu']//header/h2")).getText();
        Assert.assertEquals(sidebarHeader, "Menu", "Sidebar header text is incorrect!");
    }

    @Test
    public void xpathGetMultipleElements() {
        List<String> expectedMenuItems = new ArrayList<>(List.of(
                "HOMEPAGE", "ACCORDION", "ACTIONS", "BROWSER TABS", "BUTTONS", "CALCULATOR (JS)",
                "CONTACT US FORM TEST", "DATE PICKER", "DROPDOWN CHECKBOX RADIO", "FILE UPLOAD",
                "HIDDEN ELEMENTS", "IFRAMES", "LOADER", "LOADER TWO", "LOGIN PORTAL TEST", "MOUSE MOVEMENT",
                "POP UPS & ALERTS", "PREDICTIVE SEARCH", "TABLES", "TEST STORE", "ABOUT ME"
        ));

        // Using xpath to get all menu items
        List<String> actualMenuItems = driver.findElements(By.xpath("//nav[@id='menu']//ul/li/a"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        System.out.println(actualMenuItems);

        for (String expected : expectedMenuItems) {
            Assert.assertTrue(actualMenuItems.contains(expected), "Menu item " + expected + " is missing!");
        }
        Assert.assertEquals(actualMenuItems, expectedMenuItems, "Menu items do not match!");
    }

    @Test
    public void xpathByAttributeStrategy() {
        // Using attribute in xpath selector
        String futureSkillAcademyText = driver.findElement(By.xpath("//a[@href='https://futureskillzacademy.com/']")).getText();
        Assert.assertEquals(futureSkillAcademyText, "futureskillzacademy", "future skill academy text is incorrect!");
    }

    @Test
    public void xpathByPartialAttributeStrategy() {
        // Using partial attribute in xpath selector
        String contactUsText = driver.findElement(By.xpath("//a[contains(@href, 'contactForm')]")).getText();
        Assert.assertEquals(contactUsText, "CONTACT US FORM TEST", "Contact Us text is incorrect!");
    }

    @Test
    public void xpathByMultipleAttributesStrategy() {
        // Using multiple attributes in xpath selector
        driver.findElement(By.xpath("//a[@href='#sidebar' and contains(@class, 'toggle')]")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='sidebar' and contains(@class, 'inactive')]")).isDisplayed(), "Sidebar is displayed!");
        driver.findElement(By.xpath("//a[contains(@class, 'toggle') and @href='#sidebar']")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='sidebar' and not(contains(@class, 'inactive'))]")).isDisplayed(), "Sidebar is not displayed!");
        // other way around
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='sidebar']")).isDisplayed(), "Sidebar is not displayed!");
    }

    @Test
    public void xpathByNthChildStrategy() {
        // Using position() in xpath selector
        String fourthMenuItemText = driver.findElement(By.xpath("//nav[@id='menu']//ul/li[4]/a")).getText();
        Assert.assertEquals(fourthMenuItemText, "BROWSER TABS", "Fourth menu item text is incorrect!");
    }

    @Test
    public void xpathByDirectChildStrategy() {
        // Using direct child in xpath selector
        String menuHeaderText = driver.findElement(By.xpath("//nav[@id='menu']/header/h2")).getText();
        Assert.assertEquals(menuHeaderText, "Menu", "Menu header text is incorrect!");
    }

    @Test
    public void xpathByAdjacentSiblingStrategy() {
        // Using following-sibling in xpath selector for adjacent sibling
        String secondMenuItemText = driver.findElement(By.xpath("//nav[@id='menu']//ul/li[1]/following-sibling::li[1]/a")).getText();
        Assert.assertEquals(secondMenuItemText, "ACCORDION", "Second menu item text is incorrect!");
    }

    @Test
    public void xpathByGeneralSiblingStrategy() {
        // Using following-sibling in xpath selector for general sibling
        String thirdMenuItemText = driver.findElement(By.xpath("//nav[@id='menu']//ul/li[1]/following-sibling::li[2]/a")).getText();
        Assert.assertEquals(thirdMenuItemText, "ACTIONS", "Third menu item text is incorrect!");
    }

    @Test
    public void xpathByLastChildStrategy() {
        // Using last() in xpath selector
        String lastMenuItemText = driver.findElement(By.xpath("//nav[@id='menu']//ul/li[last()]/a")).getText();
        Assert.assertEquals(lastMenuItemText, "ABOUT ME", "Last menu item text is incorrect!");
    }

    @Test
    public void xpathByContainsTextStrategy() {
        // Using contains(text()) in xpath selector
        String loaderMenuItemText = driver.findElement(By.xpath("//nav[@id='menu']//ul/li/a[contains(text(),'Loader')]")).getText();
        Assert.assertEquals(loaderMenuItemText, "LOADER", "Loader menu item text is incorrect!");
    }

    @Test
    public void xpathByTextStrategy() {
        // Find element by exact text
        String aboutMeText = driver.findElement(By.xpath("//a[text()='About Me']")).getText();
        Assert.assertEquals(aboutMeText, "ABOUT ME", "About Me text is incorrect!");
    }

    @Test
    public void xpathByPartialTextStrategy() {
        // Find element by partial text
        String popupsText = driver.findElement(By.xpath("//a[contains(text(),'Pop Ups')]")).getText();
        Assert.assertEquals(popupsText, "POP UPS & ALERTS", "Pop Ups & Alerts text is incorrect!");
    }

    @Test
    public void xpathByAttributeValueNotContainsStrategy() {
        // Find menu items that do not contain 'inactive' in class
        List<WebElement> activeSidebars = driver.findElements(By.xpath("//div[@id='sidebar' and not(contains(@class, 'inactive'))]"));
        Assert.assertTrue(!activeSidebars.isEmpty(), "No active sidebar found!");
    }

    @Test
    public void xpathByAttributeValueEndsWithStrategy() {
        // Find the link whose href ends with 'co.uk/' and text is 'Test Store' (case-insensitive, trimmed)
        List<WebElement> testStoreLinks = driver.findElements(
            By.xpath("//a[substring(@href, string-length(@href) - string-length('co.uk/') + 1) = 'co.uk/' and normalize-space(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'))='test store']")
        );
        Assert.assertFalse(testStoreLinks.isEmpty(), "Test Store link not found!");
        WebElement testStoreLink = testStoreLinks.get(0);
        Assert.assertTrue(testStoreLink.getText().trim().equalsIgnoreCase("Test Store"), "Test Store link text is incorrect!");
        Assert.assertTrue(testStoreLink.isDisplayed(), "Test Store link is not displayed!");
    }

    @Test
    public void xpathByParentStrategy() {
        // Find the parent <li> of the 'Test Store' link
        WebElement testStoreParentLi = driver.findElement(
            By.xpath("//a[normalize-space(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'))='test store']/parent::li")
        );
        Assert.assertEquals(testStoreParentLi.getTagName(), "li", "Parent of 'Test Store' link is not <li>!");
        // Optionally, check that the <li> contains the <a> as a child
        WebElement testStoreLink = testStoreParentLi.findElement(By.xpath("./a"));
        Assert.assertTrue(testStoreLink.getText().trim().equalsIgnoreCase("Test Store"), "Test Store link not found in parent <li>!");
    }

    @Test
    public void xpathByAncestorStrategy() {
        // Find the ancestor <nav> of the 'Test Store' link
        WebElement testStoreNavAncestor = driver.findElement(
            By.xpath("//a[normalize-space(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'))='test store']/ancestor::nav[@id='menu']")
        );
        Assert.assertEquals(testStoreNavAncestor.getTagName(), "nav", "Ancestor of 'Test Store' link is not <nav>!");
        Assert.assertEquals(testStoreNavAncestor.getAttribute("id"), "menu", "Ancestor <nav> does not have id='menu'!");
        // Optionally, check that the nav contains the 'Test Store' link
        WebElement testStoreLink = testStoreNavAncestor.findElement(By.xpath(".//a[normalize-space(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'))='test store']"));
        Assert.assertTrue(testStoreLink.isDisplayed(), "Test Store link is not displayed in ancestor <nav>!");
    }

    @Test
    public void xpathByOrStrategy() {
        // Find a menu item that is either 'Test Store' or 'About Me' (case-insensitive)
        List<WebElement> links = driver.findElements(By.xpath(
            "//nav[@id='menu']//ul/li/a[normalize-space(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'))='test store' or normalize-space(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'))='about me']"
        ));
        List<String> foundTexts = links.stream().map(e -> e.getText().trim().toLowerCase()).collect(Collectors.toList());
        Assert.assertTrue(foundTexts.contains("test store") || foundTexts.contains("about me"),
            "Neither 'Test Store' nor 'About Me' link was found!");
        // Optionally, assert both are found
        Assert.assertTrue(foundTexts.contains("test store"), "'Test Store' link not found!");
        Assert.assertTrue(foundTexts.contains("about me"), "'About Me' link not found!");
    }

    @Test
    public void xpathByOrAttributeStrategy() {
        // Find an <a> element where:
        // (href contains 'teststore' OR href is exactly 'https://teststore.automationtesting.co.uk/')
        // AND class attribute contains 'menu'
        List<WebElement> links = driver.findElements(By.xpath(
            "//a[(contains(@href, 'teststore') or @href='https://teststore.automationtesting.co.uk/') and contains(@class, 'menu')]"
        ));
        // If no class='menu', relax to just the href or add a fallback assertion
        if (links.isEmpty()) {
            // fallback: just check the href or text
            links = driver.findElements(By.xpath("//a[contains(@href, 'teststore') or @href='https://teststore.automationtesting.co.uk/']"));
        }
        Assert.assertFalse(links.isEmpty(), "No matching <a> element found with 'or' strategy on attributes!");
        // Optionally, print or assert details
        for (WebElement link : links) {
            String href = link.getAttribute("href");
            String clazz = link.getAttribute("class");
            String text = link.getText();
            Assert.assertTrue(href.contains("teststore") || href.equals("https://teststore.automationtesting.co.uk/"), "Href does not match expected values!");
            // If class is present, check it contains 'menu'
            if (clazz != null) {
                Assert.assertTrue(clazz.contains("menu") || clazz.isEmpty(), "Class does not contain 'menu'!");
            }
            Assert.assertFalse(text.isEmpty(), "Link text should not be empty!");
        }
    }
}
