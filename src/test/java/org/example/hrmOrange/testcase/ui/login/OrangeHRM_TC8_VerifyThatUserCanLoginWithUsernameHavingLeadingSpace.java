package org.example.hrmOrange.testcase.ui.login;

import org.example.hrmOrange.common.BaseTest;
import org.example.hrmOrange.managers.PageManager;
import org.example.hrmOrange.page.login.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OrangeHRM_TC8_VerifyThatUserCanLoginWithUsernameHavingLeadingSpace extends BaseTest {
    @Test
    public void loginWithUsernameHavingLeadingSpace() {
        LoginPage loginPage = new LoginPage();
        loginPage.navigateToLogin();

        // Username có khoảng trắng ở đầu → login phải fail
        loginPage.login(" Admin", "admin123");

        // Kiểm tra thông báo lỗi xuất hiện
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Invalid credentials"),
                "Login should fail when username has leading space.");
    }
}
