package org.example.hrmOrange.testcase.ui.admin;

import io.qameta.allure.*;
import io.qameta.allure.model.Status;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.Assert;
import org.testng.annotations.Test;

@Story("Add new Admin user - Negative scenario")
@Severity(SeverityLevel.NORMAL)
public class OrangeHRM_Admin_Add_TC6_VerifyAddAdminWithShortUsername extends BaseAdminTest {

    @Test
    @TestCaseID("OrangeHRM_TC_Add06")
    @Description("Verify that entering Username with less than 5 characters shows 'Should be at least 5 characters' error")
    @Severity(SeverityLevel.NORMAL)
    public void verifyAddAdminWithShortUsername() {

        final String username = "Admin";
        final String password = "admin123";
        final String newEmployeeName = "Akh";
        final String newUsername = "Test"; // only 4 characters
        final String userRole = "Admin";
        final String status = "Enabled";

        Allure.addAttachment("Test Data",
                "Login Username: " + username + "\nLogin Password: " + password +
                        "\nEmployee Name: " + newEmployeeName +
                        "\nNew Username: " + newUsername + " (4 characters)" +
                        "\nUser Role: " + userRole + "\nStatus: " + status);

        step_Navigate_To_Login_Page();
        step_Login(username, password);
        step_Verify_Dashboard_Page_Displayed();
        step_Navigate_To_Admin_Page();
        step_Click_Add_Button();
        step_Enter_Employee_Name(newEmployeeName);
        step_Enter_Username(newUsername);
        step_Verify_MinLength_Error_Displayed(); // verify after enter username
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

    @Step("Step 5 – Click Add button")
    private void step_Click_Add_Button() {
        adminSteps.clickAddButton();
    }

    @Step("Step 6 – Enter Employee Name: {employeeName}")
    private void step_Enter_Employee_Name(String employeeName) {
        adminSteps.enterAddEmployeeName(employeeName);
    }

    @Step("Step 7 – Enter Username: {username}")
    private void step_Enter_Username(String username) {
        adminSteps.enterAddUsername(username);
    }

    @Step("Step 8 – Verify 'Should be at least 5 characters' error message is displayed")
    private void step_Verify_MinLength_Error_Displayed() {
        boolean isVisible = adminPage.isMinLengthMessageVisible();

        if (!isVisible) {
            Allure.step("Expected 'Should be at least 5 characters' error message not displayed!", Status.FAILED);
            Assert.fail("Expected 'Should be at least 5 characters' error message for Username field, but none was displayed");
        }

        Allure.step("'Should be at least 5 characters' error message is displayed correctly");
    }
}
