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

public class OrangeHRM_Admin_TC10_SearchWithValidUsernameAndInvalidRole extends BaseTest {
    private static final Logger logger = LogManager.getLogger(OrangeHRM_Admin_TC10_SearchWithValidUsernameAndInvalidRole.class);
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

    @TestCaseID("OrangeHRM_TC21")
    @Test(description = "Verify that searching with a valid username but invalid role returns no results")
    public void searchWithValidUsernameAndInvalidRole() {
        logger.info("Navigating to login page...");
        loginPage.navigateToLogin();

        logger.info("Logging in as Admin...");
        loginPage.login("Admin", "admin123");
        Assert.assertTrue(dashboardComponent.isAtDashboard(), "Dashboard not visible after login!");

        logger.info("Navigating to Admin page...");
        adminPage.navigateToAdmin();

        logger.info("Entering valid username = 'Admin'...");
        adminPage.searchAdminByUsername("Admin");

        logger.info("Selecting invalid role = 'ESS'...");
        adminPage.selectUserRole("ESS"); // giả sử Admin không phải role ESS

        logger.info("Clicking Search button...");
        adminPage.clickSearch();

        logger.info("Verifying that no records are found...");
        Assert.assertTrue(adminPage.isNoRecordFound(), "Unexpected records found for valid username + invalid role!");

        logger.info("Testcase passed: Searching with valid username and invalid role shows no results.");
    }
}
