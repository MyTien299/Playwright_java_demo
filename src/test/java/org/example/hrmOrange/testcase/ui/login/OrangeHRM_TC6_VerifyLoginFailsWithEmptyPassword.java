package org.example.hrmOrange.testcase.ui.login;

import org.example.hrmOrange.common.BaseTest;
import org.example.hrmOrange.page.login.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OrangeHRM_TC6_VerifyLoginFailsWithEmptyPassword extends BaseTest {
    @Test
    public void loginWithEmptyPassword() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("Admin", ""); // username đúng, password trống

        String error = loginPage.getRequiredMessage();
        Assert.assertTrue(error.contains("Required"),
                "Expected 'Required' message not shown when password is empty!");
    }
}
