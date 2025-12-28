package org.automation.tests;

import org.framework.asserts.CustomSoftAssert;
import org.framework.base.SeleniumTestBase;
import org.framework.config.ConfigResolver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.automation.pages.ContactUsPage;
import org.automation.pages.HomePage;
import org.automation.pages.SignInPage;

import java.util.ArrayList;
import java.util.List;

public class StoreHomeTests extends SeleniumTestBase {
    HomePage homePage;
    ContactUsPage contactUsPage;
    SignInPage signInPage;

    @BeforeMethod
    public void beforeTest() {
        String baseUrl = ConfigResolver.resolve("baseUrl");
        System.out.println("Navigating to base URL: " + baseUrl);
        navigateToUrl(baseUrl);
    }

    @BeforeMethod
    public void setupPageObjects() {
        homePage = new HomePage();
        contactUsPage = new ContactUsPage();
        signInPage = new SignInPage();
    }

    @Test
    public void verifyStoreHomeComponents() {
        createTestCase("Verify Store Home Page Components", () -> {
            CustomSoftAssert softAssert = new CustomSoftAssert();
            List<String> expectedMenuItems = new ArrayList<>(List.of("CLOTHES", "ACCESSORIES", "ART"));
            List<String> expectedSubMenuItems = new ArrayList<>(List.of("MEN", "WOMEN", "STATIONERY", "HOME ACCESSORIES"));

            testStep("1. Check Store logo exist in the home page.", () -> {
                waitUntil(x-> homePage.myStoreLogo.isDisplayed());
                softAssert.assertTrue(homePage.myStoreLogo.isDisplayed(), "Test Store logo is not displayed on Home Page.");
            });

            testStep("2. Check Contact Us link exist in the home page.", () -> {
                softAssert.assertTrue(homePage.contactUsLink.isDisplayed(), "Contact Us link is not displayed on Home Page.");
            });

            testStep("3. Check Sign In link exist in the home page.", () -> {
                softAssert.assertTrue(homePage.signInLink.isDisplayed(), "Sign In link is not displayed on Home Page.");
            });

            testStep("4. Check Cart icon is disabled in the home page.", () -> {
                softAssert.assertTrue(homePage.disabledCartIcon.isDisplayed(), "Cart icon is not disabled on Home Page.");
            });

            testStep("5. Verify Header Menu items and sub menu under each header menu.", () -> {
                List<String> actualMenuItems = homePage.getTopHeaderMenuItems();
                softAssert.assertEquals(actualMenuItems, expectedMenuItems, "Top Header Menu items do not match expected values.");
                softAssert.assertEquals(homePage.getSubMenuItems(), expectedSubMenuItems, "Sub Menu items do not match expected values.");
            });
            softAssert.assertAll();
        });
    }

    @Test
    public void verifyNavigationFromHomeToOtherPages() {
        createTestCase("Verify Navigation from Home Page to Other Pages", () -> {
            testStep("1. Navigate to Contact Us page from Home page.", () -> {
                homePage.contactUsLink.click();
                Assert.assertEquals(contactUsPage.contactUsHeader.getText(), "CONTACT US", "Failed to navigate to Contact Us page.");
                contactUsPage.navigateBack();
            });

            testStep("2. Navigate to Sign In page from Home page.", () -> {
                homePage.signInLink.click();
                Assert.assertEquals(signInPage.signInPageTitle.getText(), "Log in to your account",  "Failed to navigate to Sign In page.");
                signInPage.navigateBack();
            });
        });
    }
}
