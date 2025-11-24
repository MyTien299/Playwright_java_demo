package org.example.hrmOrange.testcase.ui.admin;

import io.qameta.allure.*;
import io.qameta.allure.model.Status;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.Assert;
import org.testng.annotations.Test;

@Story("Search by User Role")
@Severity(SeverityLevel.CRITICAL)
public class OrangeHRM_Admin_TC2_SearchByUserRoleAdmin extends BaseAdminTest {

    @Test
    @TestCaseID("OrangeHRM_TC12")
    @Description("Verify that admin search by User Role 'Admin' returns correct data")
    @Severity(SeverityLevel.CRITICAL)
    public void searchByUserRoleAdmin() {

        final String username = "Admin";
        final String password = "admin123";
        final String userRole = "Admin";

        Allure.addAttachment("Test Data",
                "Login Username: " + username + "\nLogin Password: " + password + "\nUser Role: " + userRole);

        step_Navigate_To_Login_Page();
        step_Login(username, password);
        step_Verify_Dashboard_Page_Displayed();
        step_Navigate_To_Admin_Page();
        step_Select_User_Role(userRole);
        step_Click_Search();
        step_Verify_Admin_Displayed_In_Table(userRole);
    }

    @Step("Step 1 – Navigate to Login Page")
    private void step_Navigate_To_Login_Page() {
        loginSteps.navigateToLoginPage();
    }

    @Step("Step 2 – Login with username: {username}, password: {password}")
    private void step_Login(String username, String password) {
        loginSteps.login(username, password);
    }

    @Step("Step 3 – Verify dashboard is displayed after login")
    private void step_Verify_Dashboard_Page_Displayed() {
        loginSteps.verifyDashboard();
    }

    @Step("Step 4 – Navigate to Admin page")
    private void step_Navigate_To_Admin_Page() {
        adminSteps.navigateToAdminPage();
    }

    @Step("Step 5 – Select User Role: {userRole}")
    private void step_Select_User_Role(String userRole) {
        adminSteps.selectUserRole(userRole);
    }

    @Step("Step 6 – Click Search button")
    private void step_Click_Search() {
        adminSteps.clickSearch();
    }

    @Step("Step 7 – Verify at least one admin user is displayed in the result table")
    private void step_Verify_Admin_Displayed_In_Table(String userRole) {
        boolean isDisplayed = adminPage.isAdminDisplayedInTable("Admin");

        if (!isDisplayed) {
            Allure.step("No admin user found in the result table!", Status.FAILED);
            Assert.fail("No admin user with role '" + userRole + "' found in the result table!");
        }

        Allure.step("At least one admin user is displayed correctly in the result table");
    }
}
