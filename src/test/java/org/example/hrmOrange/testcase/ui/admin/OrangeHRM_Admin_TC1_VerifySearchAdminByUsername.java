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

    @TestCaseID("OrangeHRM_TC11")
    @Test(description = "Verify that admin search by username 'Admin' returns correct data")
    public void verifySearchAdminByUsername() {
        // Step 1: Navigate to login
        loginPage.navigateToLogin();

        // Step 2: Login
        loginPage.login("Admin", "admin123");
        Assert.assertTrue(dashboardComponent.isAtDashboard(),
                "Login failed — dashboard not visible!");

        // Step 3: Navigate to Admin module
        adminPage.navigateToAdmin();

        // Step 4: Search username = Admin
        adminPage.searchAdminByUsername("Admin");

        // Step 5: Verify result table has Admin
        Assert.assertTrue(adminPage.isAdminDisplayedInTable("Admin"),
                "Admin user not found in the result table!");
    }
}
