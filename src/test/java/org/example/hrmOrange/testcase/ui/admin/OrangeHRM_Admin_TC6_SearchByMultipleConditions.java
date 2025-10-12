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

public class OrangeHRM_Admin_TC6_SearchByMultipleConditions extends BaseTest {
    private static final Logger logger = LogManager.getLogger(OrangeHRM_Admin_TC6_SearchByMultipleConditions.class);
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

    @TestCaseID("OrangeHRM_TC16")
    @Test(description = "Verify admin can search users by Username + Role + Status")
    public void searchByUsernameRoleStatus() {
        // Navigate to login page
        logger.info("Navigating to login page...");
        loginPage.navigateToLogin();

        // Login
        logger.info("Logging in with Admin credentials...");
        loginPage.login("Admin", "admin123");

        // Verify dashboard visible
        logger.info("Verifying dashboard visibility...");
        Assert.assertTrue(dashboardComponent.isAtDashboard(), "Login failed — Dashboard not visible!");

        // Navigate to Admin module
        logger.info("Navigating to Admin page...");
        adminPage.navigateToAdmin();

        //  Enter Username, Role, Status
        String username = "Admin";
        String role = "Admin";
        String status = "Enabled";

        logger.info("Filling search fields: username='{}', role='{}', status='{}'", username, role, status);
        adminPage.searchAdminByUsername(username);
        adminPage.selectUserRole(role);
        adminPage.selectStatus(status);

        // Click Search
        logger.info("Clicking Search button...");
        adminPage.clickSearch();

        // Verify result
        logger.info("Verifying result contains user '{}' with role '{}' and status '{}'", username, role, status);
        Assert.assertTrue(adminPage.isAdminDisplayedInTable(username),
                "Expected user '" + username + "' not found in result table!");
        Assert.assertTrue(adminPage.isEmployeeDisplayedInTable(role),
                "Expected role '" + role + "' not found in result table!");
        Assert.assertTrue(adminPage.isAdminDisplayedInTable(status),
                "Expected status '" + status + "' not found in result table!");

        logger.info("✅ Testcase passed: Search by Username + Role + Status works correctly.");
    }
}
