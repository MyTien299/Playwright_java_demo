package org.example.hrmOrange.testcase.ui.admin;

import io.qameta.allure.*;
import io.qameta.allure.model.Status;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.Assert;
import org.testng.annotations.Test;

@Story("Search with valid username and mismatched role")
@Severity(SeverityLevel.NORMAL)
public class OrangeHRM_Admin_TC10_SearchWithValidUsernameAndInvalidRole extends BaseAdminTest {

    @Test
    @TestCaseID("OrangeHRM_TC21")
    @Description("Verify that searching with a valid username but mismatched role returns no results")
    @Severity(SeverityLevel.NORMAL)
    public void searchWithValidUsernameAndInvalidRole() {

        final String username = "Admin";
        final String password = "admin123";
        final String searchUsername = "Admin";
        final String mismatchedRole = "ESS";

        Allure.addAttachment("Test Data",
                "Login Username: " + username + "\nLogin Password: " + password +
                        "\nSearch Username: " + searchUsername + "\nMismatched Role: " + mismatchedRole);

        step_Navigate_To_Login_Page();
        step_Login(username, password);
        step_Verify_Dashboard_Page_Displayed();
        step_Navigate_To_Admin_Page();
        step_Fill_Username_And_Role(searchUsername, mismatchedRole);
        step_Click_Search();
        step_Verify_No_Records_Found();
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

    @Step("Step 5 – Fill username: {username} and select role: {role}")
    private void step_Fill_Username_And_Role(String username, String role) {
        adminSteps.fillUsername(username);
        adminSteps.selectUserRole(role);
    }

    @Step("Step 6 – Click Search button")
    private void step_Click_Search() {
        adminSteps.clickSearch();
    }

    @Step("Step 7 – Verify 'No Records Found' message is displayed")
    private void step_Verify_No_Records_Found() {
        boolean isNoRecordFound = adminPage.isNoRecordFound();

        if (!isNoRecordFound) {
            Allure.step("Unexpected records found for valid username + mismatched role!", Status.FAILED);
            Assert.fail("Unexpected records found for valid username + mismatched role!");
        }

        Allure.step("'No Records Found' message is displayed correctly");
    }
}
