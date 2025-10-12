package org.example.hrmOrange.testcase.ui.login;

import org.example.hrmOrange.annotation.TestCaseID;
import org.example.hrmOrange.common.BaseTest;
import org.example.hrmOrange.page.login.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrangeHRM_Login_TC10_VerifyLoginFailsWithPasswordTrailingSpace extends BaseTest {
    private static final Logger logger = LogManager.getLogger(OrangeHRM_Login_TC10_VerifyLoginFailsWithPasswordTrailingSpace.class);
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage();
    }

    @TestCaseID("OrangeHRM_TC10")
    @Test(description = "Verify that login fails when password ends with whitespace")
    public void loginWithPasswordTrailingSpace() {
        logger.info("Navigating to login page...");
        loginPage.navigateToLogin();

        logger.info("Performing login with username='Admin' and password='admin123 '");
        loginPage.login("Admin", "admin123 "); // password ends with whitespace

        logger.info("Getting error message from login page...");
        String error = loginPage.getErrorMessage();

        logger.info("Verifying error message...");
        Assert.assertTrue(error.contains("Invalid credentials"),
                "Expected 'Invalid credentials' message not shown when password has trailing space!");

        logger.info("Testcase passed: login failed with password having trailing space");
    }
}
