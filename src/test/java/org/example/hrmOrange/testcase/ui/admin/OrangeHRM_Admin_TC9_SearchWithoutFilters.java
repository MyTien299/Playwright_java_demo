package org.example.hrmOrange.testcase.ui.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.hrmOrange.annotation.TestCaseID;
import org.example.hrmOrange.common.BaseTest;
import org.example.hrmOrange.managers.PageManager;
import org.example.hrmOrange.page.admin.AdminPage;
import org.example.hrmOrange.page.dashboard.DashboardComponent;
import org.example.hrmOrange.page.login.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class OrangeHRM_Admin_TC9_SearchWithoutFilters extends BaseTest {
    private static final Logger logger = LogManager.getLogger(OrangeHRM_Admin_TC9_SearchWithoutFilters.class);
    private LoginPage loginPage;
    private DashboardComponent dashboardComponent;
    private AdminPage adminPage;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage();
        dashboardComponent = new DashboardComponent(PageManager.getPage());
        adminPage = new AdminPage();
    }

    @TestCaseID("OrangeHRM_TC19")
    @Test(description = "Verify that searching without filters displays all users")
    public void searchWithoutAnyFilters() {
        logger.info("Navigating to login page...");
        loginPage.navigateToLogin();

        logger.info("Logging in as Admin...");
        loginPage.login("Admin", "admin123");
        Assert.assertTrue(dashboardComponent.isAtDashboard(), "Dashboard not visible after login!");

        logger.info("Navigating to Admin page...");
        adminPage.navigateToAdmin();

        logger.info("Clicking Search without entering any filters...");
        adminPage.clickSearch();

        logger.info("Verifying that user list is displayed...");
        Assert.assertTrue(adminPage.isResultTableVisible(), "User list not displayed!");
        logger.info("Testcase passed: Search without filters displays all users.");
    }
}
