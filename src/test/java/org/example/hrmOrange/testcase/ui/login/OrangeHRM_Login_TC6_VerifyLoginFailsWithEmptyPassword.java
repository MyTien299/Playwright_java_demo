package org.example.hrmOrange.testcase.ui.login;

import org.example.hrmOrange.annotation.TestCaseID;
import org.example.hrmOrange.common.BaseTest;
import org.example.hrmOrange.page.login.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrangeHRM_Login_TC6_VerifyLoginFailsWithEmptyPassword extends BaseTest {
    private static final Logger logger = LogManager.getLogger(OrangeHRM_Login_TC6_VerifyLoginFailsWithEmptyPassword.class);
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage();
    }

    @TestCaseID("OrangeHRM_TC06")
    @Test(description = "Verify login fails when password field is empty")
    public void loginWithEmptyPassword() {
        loginPage.navigateToLogin();
        loginPage.login("Admin", ""); //valid username, empty password

        String errorMessage = loginPage.getRequiredMessage();
        Assert.assertTrue(errorMessage.contains("Required"),
                "Expected 'Required' message not shown when password is empty!");
    }
}
