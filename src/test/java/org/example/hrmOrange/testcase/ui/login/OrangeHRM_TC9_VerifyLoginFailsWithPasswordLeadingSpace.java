package org.example.hrmOrange.testcase.ui.login;

import org.example.hrmOrange.common.BaseTest;
import org.example.hrmOrange.page.login.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OrangeHRM_TC9_VerifyLoginFailsWithPasswordLeadingSpace extends BaseTest {
    @Test
    public void loginWithPasswordLeadingSpace() {
        LoginPage loginPage = new LoginPage();
        loginPage.navigateToLogin();

        // Password có khoảng trắng đầu → login phải fail
        loginPage.login("Admin", " admin123");

        // Kiểm tra thông báo lỗi xuất hiện
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Invalid credentials"),
                "Login should fail when password has leading space.");
    }
}
