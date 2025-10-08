package org.example.hrmOrange.testcase.ui.login;

import org.example.hrmOrange.annotation.TestCaseID;
import org.example.hrmOrange.common.BaseTest;
import org.example.hrmOrange.page.login.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrangeHRM_Login_TC8_VerifyThatUserCanLoginWithUsernameHavingLeadingSpace extends BaseTest {
    private static final Logger logger = LogManager.getLogger(OrangeHRM_Login_TC8_VerifyThatUserCanLoginWithUsernameHavingLeadingSpace.class);
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage();
    }

    @TestCaseID("OrangeHRM_TC08")
    @Test(description = "Verify that login fails when username starts with whitespace")
    public void loginWithUsernameHavingLeadingSpace() {
        loginPage.navigateToLogin();
        loginPage.login(" Admin", "admin123"); // username starts with whitespace

        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Invalid credentials"),
                "Expected 'Invalid credentials' message not shown when username has leading space!");
    }
}
