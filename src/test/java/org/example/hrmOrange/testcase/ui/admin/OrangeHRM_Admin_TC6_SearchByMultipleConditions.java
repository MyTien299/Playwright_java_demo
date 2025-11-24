package org.example.hrmOrange.testcase.ui.admin;

import io.qameta.allure.*;
import io.qameta.allure.model.Status;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.Assert;
import org.testng.annotations.Test;

@Story("Search by multiple conditions")
@Severity(SeverityLevel.CRITICAL)
public class OrangeHRM_Admin_TC6_SearchByMultipleConditions extends BaseAdminTest {

    @Test
    @TestCaseID("OrangeHRM_TC16")
    @Description("Verify admin can search users by Username + Role + Status")
    @Severity(SeverityLevel.CRITICAL)
    public void searchByUsernameRoleStatus() {

        final String loginUsername = "Admin";
        final String loginPassword = "admin123";
        final String searchUsername = "Admin";
        final String userRole = "Admin";
        final String status = "Enabled";

        Allure.addAttachment("Test Data",
                "Login Username: " + loginUsername + "\nLogin Password: " + loginPassword +
                        "\nSearch Username: " + searchUsername + "\nUser Role: " + userRole + "\nStatus: " + status);

        step_Navigate_To_Login_Page();
        step_Login(loginUsername, loginPassword);
        step_Verify_Dashboard_Page_Displayed();
        step_Navigate_To_Admin_Page();
        step_Fill_Search_Filters(searchUsername, userRole, status);
        step_Click_Search();
        step_Verify_Results_Displayed(searchUsername);
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

    @Step("Step 5 – Fill search filters with username: {username}, role: {role}, status: {status}")
    private void step_Fill_Search_Filters(String username, String role, String status) {
        adminSteps.fillUsername(username);
        adminSteps.selectUserRole(role);
        adminSteps.selectStatus(status);
    }

    @Step("Step 6 – Click Search button")
    private void step_Click_Search() {
        adminSteps.clickSearch();
    }

    @Step("Step 7 – Verify user '{username}' is displayed in results")
    private void step_Verify_Results_Displayed(String username) {
        boolean isDisplayed = adminPage.isAdminDisplayedInTable(username);

        if (!isDisplayed) {
            Allure.step("Expected user '" + username + "' not found in result table!", Status.FAILED);
            Assert.fail("Expected user '" + username + "' not found in result table!");
        }

        Allure.step("User '" + username + "' is displayed correctly in the result table");
    }
}
