package org.example.hrmOrange.testcase.ui.login;

import com.microsoft.playwright.Page;
import org.example.hrmOrange.common.BaseTest;
import org.example.hrmOrange.managers.PageManager;
import org.example.hrmOrange.page.login.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.microsoft.playwright.Locator;

public class OrangeHRM_TC1_VerifyThatUserCanLoginWithValidDataSuccessfully extends BaseTest {
    @Test
    public void loginWithValidUser() {
        LoginPage loginPage = new LoginPage();
        loginPage.navigateToLogin();
        loginPage.login("Admin", "admin123");

        PageManager.getPage().locator("h6:has-text('Dashboard')").waitFor(new Locator.WaitForOptions().setTimeout(5000));

        String currentPageUrl = PageManager.getPage().url();
        Assert.assertTrue(currentPageUrl.contains("dashboard"), "Login failed with valid user!");
    }
}
