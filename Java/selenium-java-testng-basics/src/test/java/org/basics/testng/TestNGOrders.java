package org.basics.testng;

import org.testng.annotations.*;

public class TestNGOrders {

    // Ordered annotation methods here the way testng executes

    @BeforeSuite
    public void suiteOneBeforeSuite() {
        System.out.println("TestNG Suite One - Before Suite");
    }

    @BeforeTest
    public void suiteOneBeforeTest() {
        System.out.println("TestNG Suite One - Before Test");
    }

    @BeforeClass
    public void suiteOneBeforeClass() {
        System.out.println("TestNG Suite One - Before Class");
    }

    @BeforeMethod
    public void suiteOneBeforeMethod() {
        System.out.println("TestNG Suite One - Before Method");
    }

    @Test
    public void testMethodOne() {
        System.out.println("TestNG Suite One - Method One");
    }

    @Test
    public void testMethodTwo() {
        System.out.println("TestNG Suite One - Method Two");
    }

    @Test
    public void testMethodThree() {
        System.out.println("TestNG Suite One - Method Three");
    }

    @AfterMethod
    public void suiteOneAfterMethod() {
        System.out.println("TestNG Suite One - After Method");
    }

    @AfterClass
    public void suiteOneAfterClass() {
        System.out.println("TestNG Suite One - After Class");
    }

    @AfterTest
    public void suiteOneAfterTest() {
        System.out.println("TestNG Suite One - After Test");
    }

    @AfterSuite
    public void suiteOneAfterSuite() {
        System.out.println("TestNG Suite One - After Suite");
    }
}
