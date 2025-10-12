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

public class OrangeHRM_Admin_TC4_SearchByEmployeeName extends BaseTest {
    private static final Logger logger = LogManager.getLogger(OrangeHRM_Admin_TC4_SearchByEmployeeName.class);
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

    @TestCaseID("OrangeHRM_TC14")
    @Test(description = "Verify that admin search by Employee Name 'manda akhil user' returns result 'manda user'")
    public void searchByEmployeeName() {
        logger.info("Navigating to login page...");
        loginPage.navigateToLogin();

        logger.info("Performing login with username='Admin' and password='admin123'");
        loginPage.login("Admin", "admin123");

        logger.info("Verifying dashboard is visible...");
        Assert.assertTrue(dashboardComponent.isAtDashboard(),
                "Login failed — dashboard not visible!");

        logger.info("Navigating to Admin page...");
        adminPage.navigateToAdmin();

        logger.info("Entering Employee Name = test");
        adminPage.enterEmployeeName("test");

        logger.info("Clicking Search button...");
        adminPage.clickSearch();

        logger.info("Verifying result contains employee 'TestFirstName TestLastName'...");
        Assert.assertTrue(adminPage.verifyEmployeeNameInResults("TestFirstName TestLastName"),
                "Expected employee 'manda user' not found in the result table!");

        logger.info("Testcase passed: search by Employee Name returns correct result");
    }
}
