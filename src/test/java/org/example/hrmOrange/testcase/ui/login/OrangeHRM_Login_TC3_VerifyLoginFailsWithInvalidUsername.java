package org.example.hrmOrange.testcase.ui.login;

import org.example.hrmOrange.annotation.TestCaseID;
import org.example.hrmOrange.common.BaseTest;
import org.example.hrmOrange.page.login.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrangeHRM_Login_TC3_VerifyLoginFailsWithInvalidUsername extends BaseTest {
    private static final Logger logger = LogManager.getLogger(OrangeHRM_Login_TC3_VerifyLoginFailsWithInvalidUsername.class);
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage();
    }

    @TestCaseID("OrangeHRM_TC03")
    @Test(description = "Verify that login fails when using an invalid username with a valid password")
    public void loginWithInvalidUsername() {
        logger.info("Navigating to login page...");
        loginPage.navigateToLogin();

        logger.info("Performing login with invalid username='WrongUser' and valid password='admin123'");
        loginPage.login("WrongUser", "admin123"); // valid password but invalid username

        logger.info("Getting error message from login page...");
        String errorMessage = loginPage.getErrorMessage();

        logger.info("Verifying error message...");
        Assert.assertTrue(errorMessage.contains("Invalid credentials"),
                "Expected error message not shown when logging in with wrong username!");

        logger.info("Testcase passed: login failed with invalid username");
    }
}
