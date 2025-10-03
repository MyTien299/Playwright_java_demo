package org.example.hrmOrange.testcase.ui.login;

import org.example.hrmOrange.common.BaseTest;
import org.example.hrmOrange.page.login.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OrangeHRM_TC2_VerifyLoginFailsWithInvalidPassword extends BaseTest {
    @Test
    public void loginWithInvalidPassword() {
        LoginPage loginPage = new LoginPage();
        loginPage.navigateToLogin();
        loginPage.login("Admin", "wrongpass");

        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Invalid credentials"),
                "Expected error message not shown when logging in with wrong password!");
    }
}
