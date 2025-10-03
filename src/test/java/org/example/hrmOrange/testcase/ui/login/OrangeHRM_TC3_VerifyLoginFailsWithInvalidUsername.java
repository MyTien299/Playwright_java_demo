package org.example.hrmOrange.testcase.ui.login;

import org.example.hrmOrange.common.BaseTest;
import org.example.hrmOrange.page.login.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OrangeHRM_TC3_VerifyLoginFailsWithInvalidUsername extends BaseTest {
    @Test
    public void loginWithInvalidUsername() {
        LoginPage loginPage = new LoginPage();
        loginPage.navigateToLogin();
        loginPage.login("WrongUser", "admin123"); // password đúng nhưng username sai

        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Invalid credentials"),
                "Expected error message not shown when logging in with wrong username!");
    }
}
