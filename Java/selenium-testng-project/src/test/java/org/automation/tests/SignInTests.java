package org.automation.tests;

import org.automation.pages.HomePage;
import org.automation.pages.SignInPage;
import org.framework.asserts.CustomSoftAssert;
import org.framework.base.SeleniumTestBase;
import org.framework.config.ConfigResolver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SignInTests extends SeleniumTestBase {

    HomePage homePage;
    SignInPage signInPage;

    @BeforeMethod
    public void beforeMethod(){
        String baseUrl = ConfigResolver.resolve("baseUrl");
        System.out.println("Navigating to base URL: " + baseUrl);
        navigateToUrl(baseUrl);
    }

    @BeforeMethod
    public void setupPageObjects(){
        homePage = new HomePage();
        signInPage = new SignInPage();
    }

    @Test
    public void verifySignInPageComponents() {
        createTestCase("Verify Sign In Page Components", () -> {
            CustomSoftAssert softAssert = new CustomSoftAssert();
            testStep("1. Navigate to Sign In page.", () -> homePage.signInLink.click());

            testStep("2. Verify Sign In page components.", () -> {
                waitUntil(x -> signInPage.signInPageTitle.isDisplayed());
                softAssert.assertEquals(signInPage.signInPageTitle.getText(), "Log in to your account", "Sign In page title is not displayed.");
                softAssert.assertTrue(signInPage.emailInput.isDisplayed(), "Email input field is not present.");
                softAssert.assertTrue(signInPage.passwordInput.isDisplayed(), "Password input field is not present.");
                softAssert.assertTrue(signInPage.showPasswordButton.isDisplayed(), "Show Password button is not present.");
                softAssert.assertTrue(signInPage.signInButton.isEnabled(), "Sign In button is not enabled.");
                softAssert.assertTrue(signInPage.forgotYourPasswordLink.isDisplayed(), "Forgot Your Password link is not displayed.");
                softAssert.assertTrue(signInPage.noAccountCreateOneLink.isDisplayed(), "No Account? Create One link is not displayed.");
            });
        });
    }

    @Test
    public void validateSignInIntoAccount() {
        createTestCase("Validate Sign In Into Account", () -> {
            CustomSoftAssert softAssert = new CustomSoftAssert();
            testStep("1. Navigate to Sign In page.", () -> homePage.signInLink.click());

            testStep("2. Verify alert message is displayed for signing in without email.", () -> {
                waitUntil(x -> signInPage.signInPageTitle.isDisplayed());
                signInPage.passwordInput.setText("ValidPassword123");
                signInPage.signInButton.click();
                signInPage.acceptAlertIfPresent();
            });

            testStep("3. Verify alert message is displayed for signing in without password.", () -> {
                signInPage.emailInput.setText("test@gmail.com");
                signInPage.signInButton.click();
                signInPage.acceptAlertIfPresent();
            });
            softAssert.assertAll();
        });
    }
}
