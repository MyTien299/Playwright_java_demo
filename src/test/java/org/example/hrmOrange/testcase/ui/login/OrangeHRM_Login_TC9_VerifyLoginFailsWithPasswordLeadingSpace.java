package org.example.hrmOrange.testcase.ui.login;

import org.example.hrmOrange.annotation.TestCaseID;
import org.example.hrmOrange.common.BaseTest;
import org.example.hrmOrange.page.login.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrangeHRM_Login_TC9_VerifyLoginFailsWithPasswordLeadingSpace extends BaseTest {
    private static final Logger logger = LogManager.getLogger(OrangeHRM_Login_TC9_VerifyLoginFailsWithPasswordLeadingSpace.class);
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage();
    }

    @TestCaseID("OrangeHRM_TC09")
    @Test(description = "Verify that login fails when password starts with whitespace")
    public void loginWithPasswordLeadingSpace() {
        loginPage.navigateToLogin();
        loginPage.login("Admin", " admin123"); // password starts with whitespace

        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Invalid credentials"),
                "Expected 'Invalid credentials' message not shown when password has leading space!");
    }
}
