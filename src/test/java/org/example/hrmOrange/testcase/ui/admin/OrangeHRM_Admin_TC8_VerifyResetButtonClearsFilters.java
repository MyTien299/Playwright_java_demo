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

public class OrangeHRM_Admin_TC8_VerifyResetButtonClearsFilters extends BaseTest {
    private static final Logger logger = LogManager.getLogger(OrangeHRM_Admin_TC8_VerifyResetButtonClearsFilters.class);
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

    @TestCaseID("OrangeHRM_TC18")
    @Test(description = "Verify that Reset button clears all filters in Admin search form")
    public void verifyResetButtonClearsFilters() {
        logger.info("Navigating to login page...");
        loginPage.navigateToLogin();

        logger.info("Logging in as Admin...");
        loginPage.login("Admin", "admin123");

        Assert.assertTrue(dashboardComponent.isAtDashboard(), "Dashboard not visible after login!");

        logger.info("Navigating to Admin page...");
        adminPage.navigateToAdmin();

        logger.info("Filling search filters...");
        adminPage.searchAdminByUsername("Admin");
        adminPage.selectUserRole("ESS");
        adminPage.enterEmployeeName("manda user");
        adminPage.selectStatus("Enabled");

        logger.info("Clicking Reset button...");
        adminPage.clickReset();

        logger.info("Verifying that all filters are cleared...");
        Assert.assertTrue(adminPage.areAllFiltersCleared(), "Filters were not cleared after clicking Reset!");

        logger.info("Testcase passed: Reset button clears all filters successfully.");
    }
}
