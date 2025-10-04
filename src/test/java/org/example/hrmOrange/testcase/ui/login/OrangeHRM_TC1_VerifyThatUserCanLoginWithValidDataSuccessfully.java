package org.example.hrmOrange.testcase.ui.login;

import org.example.hrmOrange.common.BaseTest;
import org.example.hrmOrange.managers.PageManager;
import org.example.hrmOrange.page.login.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.microsoft.playwright.Locator;

public class OrangeHRM_TC1_VerifyThatUserCanLoginWithValidDataSuccessfully extends BaseTest {
    LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage();
    }

    @Test(description = "Verify that user can login with valid data successfully")
    public void loginWithValidUser() {
        loginPage.navigateToLogin();
        loginPage.login("Admin", "admin123");
        PageManager.getPage().locator("h6:has-text('Dashboard')").waitFor(new Locator.WaitForOptions().setTimeout(5000));
        String currentPageUrl = PageManager.getPage().url();
        Assert.assertTrue(currentPageUrl.contains("dashboard"), "Login failed with valid user!");
    }
}
