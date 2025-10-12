package org.example.hrmOrange.testcase.ui.login;

import org.example.hrmOrange.annotation.TestCaseID;
import org.example.hrmOrange.common.BaseTest;
import org.example.hrmOrange.page.login.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrangeHRM_Login_TC5_VerifyLoginFailsWithEmptyUsername extends BaseTest {
    private static final Logger logger = LogManager.getLogger(OrangeHRM_Login_TC5_VerifyLoginFailsWithEmptyUsername.class);
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage();
    }

    @TestCaseID("OrangeHRM_TC05")
    @Test(description = "Verify login fails when username field is empty")
    public void loginWithEmptyUsername() {
        logger.info("Navigating to login page...");
        loginPage.navigateToLogin();

        logger.info("Performing login with empty username='Admin' and valid password='admin123'");
        loginPage.login("", "admin123"); // empty username , valid password

        logger.info("Getting error message from login page...");
        String errorMessage = loginPage.getRequiredMessage();

        logger.info("Verifying error message...");
        Assert.assertTrue(errorMessage.contains("Required"),
                "Expected 'Required' message not shown when username is empty!");

        logger.info("Testcase passed: login failed with empty username");
    }
}
