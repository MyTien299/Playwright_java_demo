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

public class OrangeHRM_Admin_TC2_SearchByUserRoleAdmin extends BaseTest {
    private static final Logger logger = LogManager.getLogger(OrangeHRM_Admin_TC2_SearchByUserRoleAdmin.class);
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

    @TestCaseID("OrangeHRM_TC12")
    @Test(description = "Verify that admin search by User Role 'Admin' returns correct data")
    public void searchByUserRoleAdmin() {
        //Navigate to login
        logger.info("Navigating to login page...");
        loginPage.navigateToLogin();

        //Login
        logger.info("Performing login with username='Admin' and password='admin123'");
        loginPage.login("Admin", "admin123");

        // Verify dashboard
        logger.info("Verifying dashboard is visible...");
        Assert.assertTrue(dashboardComponent.isAtDashboard(),
                "Login failed — dashboard not visible!");

        // Navigate to Admin module
        logger.info("Navigating to Admin page...");
        adminPage.navigateToAdmin();

        // Select User Role = Admin
        logger.info("Selecting User Role = Admin");
        adminPage.selectUserRole("Admin");

        //Click Search
        logger.info("Clicking Search button...");
        adminPage.searchAdminByUsername(""); // Không nhập username, chỉ lọc theo Role

        // Verify at least 1 result exists
        logger.info("Verifying at least one admin user is displayed in the result table...");
        Assert.assertTrue(adminPage.isAdminDisplayedInTable("Admin"),
                "No admin user found in the result table!");

        logger.info("Testcase passed: admin search by User Role 'Admin' returns correct data");
    }
}
