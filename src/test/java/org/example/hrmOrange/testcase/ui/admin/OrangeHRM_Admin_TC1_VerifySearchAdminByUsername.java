package org.example.hrmOrange.testcase.ui.admin;

import org.example.hrmOrange.annotation.TestCaseID;
import org.example.hrmOrange.common.BaseTest;
import org.example.hrmOrange.managers.PageManager;
import org.example.hrmOrange.page.admin.AdminPage;
import org.example.hrmOrange.page.dashboard.DashboardComponent;
import org.example.hrmOrange.page.login.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrangeHRM_Admin_TC1_VerifySearchAdminByUsername extends BaseTest {
    private static final Logger logger = LogManager.getLogger(OrangeHRM_Admin_TC1_VerifySearchAdminByUsername.class);
    private LoginPage loginPage;
    private DashboardComponent dashboardComponent;
    private AdminPage adminPage;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage(webKeyword);
        dashboardComponent = new DashboardComponent(PageManager.getPage());
        adminPage = new AdminPage(webKeyword);
    }

    @TestCaseID("OrangeHRM_TC11")
    @Test(description = "Verify that admin search by username 'Admin' returns correct data")
    public void verifySearchAdminByUsername() {
        // Step 1: Navigate to login
        logger.info("Navigating to login page...");
        loginPage.navigateToLogin();

        // Step 2: Login
        logger.info("Performing login with username='Admin' and password='admin123'");
        loginPage.login("Admin", "admin123");

        logger.info("Verifying dashboard is visible...");
        Assert.assertTrue(dashboardComponent.isAtDashboard(),
                "Login failed — dashboard not visible!");

        // Step 3: Navigate to Admin module
        logger.info("Navigating to Admin page...");
        adminPage.navigateToAdmin();

        // Step 4: Search username = Admin
        logger.info("Searching for admin user...");
        adminPage.searchAdminByUsername("Admin");

        // Step 5: Verify result table has Admin
        logger.info("Verifying admin user is displayed in the result table...");
        Assert.assertTrue(adminPage.isAdminDisplayedInTable("Admin"),
                "Admin user not found in the result table!");

        logger.info("Testcase passed: admin search by username 'Admin' returns correct data");
    }
}
