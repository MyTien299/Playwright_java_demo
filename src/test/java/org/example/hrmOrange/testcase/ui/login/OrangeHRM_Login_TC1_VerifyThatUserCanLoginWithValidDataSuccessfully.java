package org.example.hrmOrange.testcase.ui.login;

import org.example.hrmOrange.common.BaseTest;
import org.example.hrmOrange.managers.PageManager;
import org.example.hrmOrange.page.dashboard.DashboardComponent;
import org.example.hrmOrange.page.login.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.example.hrmOrange.annotation.TestCaseID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class OrangeHRM_Login_TC1_VerifyThatUserCanLoginWithValidDataSuccessfully extends BaseTest {
    private static final Logger logger = LogManager.getLogger(OrangeHRM_Login_TC1_VerifyThatUserCanLoginWithValidDataSuccessfully.class);
    private LoginPage loginPage;
    private DashboardComponent dashboardComponent;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage();
        dashboardComponent = new DashboardComponent(PageManager.getPage());
    }

    @TestCaseID("OrangeHRM_TC01")
    @Test(description = "Verify that user can login with valid data successfully")
    public void loginWithValidUser() {
        loginPage.navigateToLogin();
        loginPage.login("Admin", "admin123");

        // Wait dashboard xuất hiện trước khi verify
        loginPage.waitForDashboard();

        // Verify Dashboard is visible
        Assert.assertTrue(dashboardComponent.isAtDashboard(),
                "Dashboard not displayed — login might have failed!");

        // Verify title
        String actualTitle = dashboardComponent.getDashboardTitle();
        Assert.assertEquals(actualTitle.trim(), "Dashboard",
                "Dashboard title mismatch!");
    }
}
