package org.example.hrmOrange.testcase.ui.admin;

import io.qameta.allure.*;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.annotations.Test;

@Story("Reset button functionality")
@Severity(SeverityLevel.NORMAL)
public class OrangeHRM_Admin_TC8_VerifyResetButtonClearsFilters extends BaseAdminTest {

    @Test
    @TestCaseID("OrangeHRM_TC18")
    @Description("Verify that Reset button clears all filters in Admin search form")
    @Severity(SeverityLevel.NORMAL)
    public void verifyResetButtonClearsFilters() {

        final String username = "Admin";
        final String password = "admin123";
        final String searchUsername = "Admin";
        final String userRole = "ESS";
        final String employeeName = "Johana rincon Birinciddd";
        final String status = "Enabled";

        Allure.addAttachment("Test Data",
                "Login Username: " + username + "\nLogin Password: " + password +
                        "\nSearch Username: " + searchUsername + "\nUser Role: " + userRole +
                        "\nEmployee Name: " + employeeName + "\nStatus: " + status);

        step_Navigate_To_Login_Page();
        step_Login(username, password);
        step_Verify_Dashboard_Page_Displayed();
        step_Navigate_To_Admin_Page();
        step_Fill_Admin_Search_Filters(searchUsername, userRole, employeeName, status);
        step_Click_Reset_Button();
        step_Verify_All_Filters_Are_Cleared();
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

    @Step("Step 5 – Fill Admin search filters with username: {username}, role: {userRole}, employee: {employeeName}, status: {status}")
    private void step_Fill_Admin_Search_Filters(String username, String userRole, String employeeName, String status) {
        adminSteps.fillUsername(username);
        adminSteps.selectUserRole(userRole);
        adminSteps.enterEmployeeName(employeeName);
        adminSteps.selectStatus(status);
    }

    @Step("Step 6 – Click Reset button")
    private void step_Click_Reset_Button() {
        adminSteps.clickReset();
    }

    @Step("Step 7 – Verify all filters are cleared")
    private void step_Verify_All_Filters_Are_Cleared() {
        adminSteps.verifyFiltersCleared();
    }
}
