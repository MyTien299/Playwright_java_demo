package org.example.hrmOrange.testcase.ui.login;

import org.example.hrmOrange.common.BaseTest;
import org.example.hrmOrange.page.login.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OrangeHRM_TC5_VerifyLoginFailsWithEmptyUsername extends BaseTest {
    @Test
    public void loginWithEmptyUsername() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("", "admin123"); // username trống, password đúng

        String error = loginPage.getRequiredMessage();
        Assert.assertTrue(error.contains("Required"),
                "Expected 'Required' message not shown when username is empty!");
    }
}
