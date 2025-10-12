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

public class OrangeHRM_Admin_TC5_SearchWithInvalidEmployee extends BaseTest{
    private static final Logger logger = LogManager.getLogger(OrangeHRM_Admin_TC5_SearchWithInvalidEmployee.class);
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

    @TestCaseID("OrangeHRM_TC15")
    @Test(description = "Verify that searching with an invalid employee name shows no records found")
    public void searchWithInvalidEmployee() {
        // Navigate to login page
        logger.info("Navigating to login page...");
        loginPage.navigateToLogin();

        //Login as Admin
        logger.info("Logging in as Admin...");
        loginPage.login("Admin", "admin123");

        // Verify dashboard loaded
        Assert.assertTrue(dashboardComponent.isAtDashboard(), "Dashboard not visible!");

        // Navigate to Admin page
        logger.info("Navigating to Admin page...");
        adminPage.navigateToAdmin();

        // Enter invalid employee name
        logger.info("Entering Employee Name = manda akhil user");
        adminPage.enterEmployeeName("Invalid Employee XYZ");

        // Click Search
        logger.info("Clicking Search...");
        adminPage.clickSearch();

        // Verify invalid employee name message displayed
        logger.info("Verifying invalid employee name message displayed...");
        Assert.assertTrue(adminPage.isEmployeeInvalidMessageVisible(), "Expected 'Invalid' message not found!");

        logger.info("Testcase passed: Searching with invalid employee name shows no data.");
    }
}
