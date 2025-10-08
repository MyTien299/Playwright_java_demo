package org.example.hrmOrange.testcase.ui.login;

import org.example.hrmOrange.annotation.TestCaseID;
import org.example.hrmOrange.common.BaseTest;
import org.example.hrmOrange.page.login.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrangeHRM_Login_TC2_VerifyLoginFailsWithInvalidPassword extends BaseTest {
    private static final Logger logger = LogManager.getLogger(OrangeHRM_Login_TC2_VerifyLoginFailsWithInvalidPassword.class);
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage();
    }

    @TestCaseID("OrangeHRM_TC02")
    @Test(description = "Verify that login fails when using a valid username but invalid password")
    public void loginWithInvalidPassword() {
        loginPage.navigateToLogin();
        loginPage.login("Admin", "wrongpass");

        // Get error message from login page
        String errorMessage = loginPage.getErrorMessage();

        // Verify message
        Assert.assertTrue(errorMessage.contains("Invalid credentials"),
                "Expected error message not shown when logging in with wrong password!");
    }
}
