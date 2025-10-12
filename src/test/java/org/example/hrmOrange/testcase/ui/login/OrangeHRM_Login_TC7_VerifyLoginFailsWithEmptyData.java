package org.example.hrmOrange.testcase.ui.login;

import org.example.hrmOrange.annotation.TestCaseID;
import org.example.hrmOrange.common.BaseTest;
import org.example.hrmOrange.page.login.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrangeHRM_Login_TC7_VerifyLoginFailsWithEmptyData extends BaseTest {
    private static final Logger logger = LogManager.getLogger(OrangeHRM_Login_TC7_VerifyLoginFailsWithEmptyData.class);
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage();
    }

    @TestCaseID("OrangeHRM_TC07")
    @Test(description = "Verify login fails when both username and password fields are empty")
    public void loginWithEmptyUsernameAndPassword() {
        logger.info("Navigating to login page...");
        loginPage.navigateToLogin();

        logger.info("Performing login with both empty username and password...");
        loginPage.login("", ""); // both empty username and password

        logger.info("Getting error message from login page...");
        String usernameError = loginPage.getUsernameRequiredMessage();
        String passwordError = loginPage.getPasswordRequiredMessage();

        logger.info("Verifying error message...");
        Assert.assertTrue(usernameError.contains("Required"),
                "Expected 'Required' message not shown for empty username!");
        Assert.assertTrue(passwordError.contains("Required"),
                "Expected 'Required' message not shown for empty password!");

        logger.info("Testcase passed: login failed with both empty username and password");
    }
}
