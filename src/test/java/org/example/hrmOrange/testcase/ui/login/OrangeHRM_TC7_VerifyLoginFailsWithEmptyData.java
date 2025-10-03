package org.example.hrmOrange.testcase.ui.login;

import org.example.hrmOrange.common.BaseTest;
import org.example.hrmOrange.page.login.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OrangeHRM_TC7_VerifyLoginFailsWithEmptyData extends BaseTest
{
    @Test
    public void loginWithEmptyUsernameAndPassword() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("", ""); // cả username và password đều trống

        // Trường hợp này, OrangeHRM hiển thị thông báo "Required" cho cả 2 ô input.
        String usernameError = loginPage.getRequiredMessage();
        String passwordError = loginPage.getRequiredMessage();

        Assert.assertTrue(usernameError.contains("Required"),
                "Expected 'Required' message not shown for empty username!");
        Assert.assertTrue(passwordError.contains("Required"),
                "Expected 'Required' message not shown for empty password!");
    }
}
