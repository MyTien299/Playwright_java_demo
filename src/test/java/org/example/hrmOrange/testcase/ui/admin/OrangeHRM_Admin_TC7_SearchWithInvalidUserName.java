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

public class OrangeHRM_Admin_TC7_SearchWithInvalidUserName extends BaseTest{
    private static final Logger logger = LogManager.getLogger(OrangeHRM_Admin_TC7_SearchWithInvalidUserName.class);
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

    @TestCaseID("OrangeHRM_TC17")
    @Test(description = "Verify that searching with invalid employee name shows no results")
    public void searchWithInvalidEmployee() {
        logger.info("Navigating to login page...");
        loginPage.navigateToLogin();

        logger.info("Logging in as Admin...");
        loginPage.login("Admin", "admin123");

        Assert.assertTrue(dashboardComponent.isAtDashboard(), "Dashboard not visible!");

        logger.info("Navigating to Admin page...");
        adminPage.navigateToAdmin();

        logger.info("Entering invalid employee name...");
        adminPage.searchAdminByUsername("Invalid Name XYZ");

        logger.info("Clicking Search...");
        adminPage.clickSearch();

        logger.info("Verifying no results found...");
        Assert.assertTrue(adminPage.isNoRecordFound(), "Unexpected records found!");

        logger.info("Testcase passed: Searching with invalid employee name shows no data.");
    }

}
