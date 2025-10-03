package org.example.hrmOrange.testcase.ui.login;

import org.example.hrmOrange.common.BaseTest;
import org.example.hrmOrange.page.login.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OrangeHRM_TC4_VerifyLoginFailsWithInvalidData extends BaseTest
{
    @Test
    public void loginWithInvalidUsernameAndPassword() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("wrongUser", "wrongPass"); // cả username và password đều sai

        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Invalid credentials"),
                "Expected 'Invalid credentials' message not shown for invalid username & password!");
    }
}
