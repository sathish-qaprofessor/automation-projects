package org.basics.testng;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class TestNGGroups {

    @BeforeMethod
    public void beforeMethod(Method method) {
        System.out.println("Starting test: " + method.getName());
    }

    @AfterMethod
    public void afterMethod(Method method) {
        System.out.println("Finished test: " + method.getName());
    }

    @Test(groups = "smoke")
    public void testLogin() {
        System.out.println("Sanity Test: Login");
    }

    @Test(groups = "smoke")
    public void testLogout() {
        System.out.println("Sanity Test: Logout");
    }

    @Test(groups = "e2e")
    public void testAddToCart() {
        System.out.println("Regression Test: Add to Cart");
    }

    @Test(groups = "e2e")
    public void testCheckout() {
        System.out.println("Regression Test: Checkout");
    }

    @Test(groups = {"smoke", "e2e"})
    public void testProfileUpdate() {
        System.out.println("Sanity + Regression Test: Profile Update");
    }

    @Test(groups = "e2e")
    public void testFullPurchaseFlow() {
        System.out.println("End-to-End Test: Full Purchase Flow");
    }
}
