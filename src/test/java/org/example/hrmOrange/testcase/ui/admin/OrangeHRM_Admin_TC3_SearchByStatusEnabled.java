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

public class OrangeHRM_Admin_TC3_SearchByStatusEnabled extends BaseTest {
    private static final Logger logger = LogManager.getLogger(OrangeHRM_Admin_TC3_SearchByStatusEnabled.class);
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

    @TestCaseID("OrangeHRM_TC13")
    @Test(description = "Verify that admin search by Status 'Enabled' returns correct data")
    public void searchByStatusEnabled() {
        // Navigate to login
        logger.info("Navigating to login page...");
        loginPage.navigateToLogin();

        // Login
        logger.info("Logging in with username='Admin' and password='admin123'");
        loginPage.login("Admin", "admin123");

        // Verify dashboard visible
        Assert.assertTrue(dashboardComponent.isAtDashboard(),
                "Login failed — dashboard not visible!");
        logger.info("Dashboard is visible");

        // Navigate to Admin module
        logger.info("Navigating to Admin page...");
        adminPage.navigateToAdmin();

        // Select Status = Enabled
        logger.info("Selecting Status = Enabled...");
        adminPage.selectStatus("Enabled");

        // Click Search
        logger.info("Clicking Search button...");
        adminPage.clickSearch();

        // Verify at least one Enabled account exists
        logger.info("Verifying that at least one Enabled account is displayed...");
        boolean found = adminPage.isAdminDisplayedInTable("Enabled");
        Assert.assertTrue(found, "No enabled users found in the result table!");

        logger.info("Testcase passed: Admin search by Status 'Enabled' returns correct data");
    }
}
