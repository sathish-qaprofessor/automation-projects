package org.automation.tests;

import org.automation.pages.ContactUsPage;
import org.automation.pages.HomePage;
import org.framework.asserts.CustomSoftAssert;
import org.framework.base.SeleniumTestBase;
import org.framework.config.ConfigResolver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ContactUsTests extends SeleniumTestBase {

    HomePage homePage;
    ContactUsPage contactUsPage;

    @BeforeMethod
    public void beforeTest() {
        String baseUrl = ConfigResolver.resolve("baseUrl");
        navigateToUrl(baseUrl);
    }

    @BeforeMethod
    public void setupPageObjects() {
        homePage = new HomePage();
        contactUsPage = new ContactUsPage();
    }

    @Test
    public void verifyContactUsComponents() {
        createTestCase("Verify Contact Us Page Components", () -> {
            CustomSoftAssert softAssert = new CustomSoftAssert();

            testStep("1. Navigate to Contact Us page.", () -> homePage.contactUsLink.click());

            testStep("2. Verify Contact Us page components.", () -> {
                waitUntil(x -> contactUsPage.contactUsHeader.isDisplayed());
                softAssert.assertTrue(contactUsPage.contactUsHeader.isDisplayed(), "Contact Us page header is not displayed.");
                softAssert.assertTrue(contactUsPage.emailAddressInput.isDisplayed(), "Email input field is not present.");
                softAssert.assertTrue(contactUsPage.messageBox.isDisplayed(), "Message input box is not displayed.");
                softAssert.assertTrue(contactUsPage.sendButton.isEnabled(), "Send button is not enabled.");
            });
            softAssert.assertAll();
        });
    }

    @Test
    public void verifySubmitContactUsForm() {
        createTestCase("Verify Contact Us with Invalid Credentials", () -> {
            CustomSoftAssert softAssert = new CustomSoftAssert();

            testStep("1. Navigate to Contact Us page.", () -> homePage.contactUsLink.click());

            testStep("2. Submit Contact Us form without email.", () -> {
                waitUntil(x -> contactUsPage.contactUsHeader.isDisplayed());
                contactUsPage.subjectDropdown.selectByVisibleText("Webmaster");
                contactUsPage.messageBox.setText("This is a test message.");
                contactUsPage.sendButton.click();
                softAssert.assertEquals(contactUsPage.validationMessage.getText(), "Invalid email address.", "Incorrect validation message is displayed for no email.");
            });

            testStep("3. Submit Contact Us form with all valid info.", () -> {
                waitUntil(x -> contactUsPage.contactUsHeader.isDisplayed());
                contactUsPage.subjectDropdown.selectByVisibleText("Webmaster");
                contactUsPage.emailAddressInput.setText("tester@gmail.com");
                contactUsPage.messageBox.setText("This is a test message.");
                contactUsPage.sendButton.click();
            });

            softAssert.assertAll();
        });
    }

}

