package org.basics.testng;

import org.testng.annotations.*;

public class TestNgAnnotationsOrder {

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("BeforeSuite → Runs once BEFORE entire test suite");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("AfterSuite → Runs once AFTER entire test suite");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("BeforeTest → Runs before <test> tag in testng.xml");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("AfterTest → Runs after <test> tag in testng.xml");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("BeforeClass → Runs before FIRST test method in this class");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("AfterClass → Runs after ALL test methods in this class");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("BeforeMethod → Runs BEFORE every @Test method");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("AfterMethod → Runs AFTER every @Test method");
    }

    @BeforeGroups("sanity")
    public void beforeGroup() {
        System.out.println("BeforeGroups → Runs before SANITY group");
    }

    @AfterGroups("sanity")
    public void afterGroup() {
        System.out.println("AfterGroups → Runs after SANITY group");
    }

    @Test(groups = "sanity")
    public void testMethod1() {
        System.out.println("Test1 → Sanity test");
    }

    @Test(groups = "regression")
    public void testMethod2() {
        System.out.println("Test2 → Regression test");
    }

    @Test
    public void testMethod3() {
        System.out.println("Test3 → Normal test (no group)");
    }
}
