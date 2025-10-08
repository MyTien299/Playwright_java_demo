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
        loginPage.navigateToLogin();
        loginPage.login("wrongUser", "wrongPass"); // both invalid username and password

        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Invalid credentials"),
                "Expected 'Invalid credentials' message not shown for invalid username & password!");
    }
}
