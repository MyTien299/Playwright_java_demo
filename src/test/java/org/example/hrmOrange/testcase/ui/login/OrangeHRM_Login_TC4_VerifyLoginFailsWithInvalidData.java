package org.example.hrmOrange.testcase.ui.login;

import org.example.hrmOrange.annotation.TestCaseID;
import org.example.hrmOrange.common.BaseTest;
import org.example.hrmOrange.page.login.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrangeHRM_Login_TC4_VerifyLoginFailsWithInvalidData extends BaseTest {
    private static final Logger logger = LogManager.getLogger(OrangeHRM_Login_TC4_VerifyLoginFailsWithInvalidData.class);
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage();
    }

    @TestCaseID("OrangeHRM_TC04")
    @Test(description = "Verify that login fails when both username and password are invalid")
    public void loginWithInvalidUsernameAndPassword() {
        logger.info("Navigating to login page...");
        loginPage.navigateToLogin();

        logger.info("Performing login with invalid username='wrongUser' and invalid password='wrongPass'");
        loginPage.login("wrongUser", "wrongPass"); // both invalid username and password

        logger.info("Getting error message from login page...");
        String errorMessage = loginPage.getErrorMessage();

        logger.info("Verifying error message...");
        Assert.assertTrue(errorMessage.contains("Invalid credentials"),
                "Expected 'Invalid credentials' message not shown for invalid username & password!");

        logger.info("Testcase passed: login failed with invalid username and password");
    }
}
