package org.example.hrmOrange.testcase.ui.admin;

import io.qameta.allure.*;
import io.qameta.allure.model.Status;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.Assert;
import org.testng.annotations.Test;

@Story("Add new Admin user - Negative scenario")
@Severity(SeverityLevel.CRITICAL)
public class OrangeHRM_Admin_Add_TC2_VerifyAddAdminWithInvalidEmployee extends BaseAdminTest {

    @Test
    @TestCaseID("OrangeHRM_TC_Add02")
    @Description("Verify that adding a new Admin user with an invalid/non-existing Employee Name shows proper error")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyAddAdminWithInvalidEmployee() {

        final String username = "Admin";
        final String password = "admin123";
        final String invalidEmployeeName = "InvalidEmployeeXYZ"; // nhân viên không tồn tại
        final String newUsername = "TestAdminInvalid01";
        final String userRole = "Admin";
        final String status = "Enabled";

        Allure.addAttachment("Test Data",
                "Login Username: " + username + "\nLogin Password: " + password +
                        "\nInvalid Employee Name: " + invalidEmployeeName +
                        "\nNew Username: " + newUsername +
                        "\nUser Role: " + userRole + "\nStatus: " + status);

        step_Navigate_To_Login_Page();
        step_Login(username, password);
        step_Verify_Dashboard_Page_Displayed();
        step_Navigate_To_Admin_Page();
        step_Click_Add_Button();
        step_Fill_Add_Admin_Form(invalidEmployeeName, newUsername, userRole, status, password);
        step_Click_Save();
        step_Verify_InvalidEmployee_Error_Displayed();
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

    @Step("Step 6 – Fill Add Admin form with employee: {employeeName}, username: {username}, role: {role}, status: {status}")
    private void step_Fill_Add_Admin_Form(String employeeName, String username, String role, String status, String password) {
        adminSteps.enterAddEmployeeName(employeeName);
        adminSteps.enterAddUsername(username);
        adminSteps.selectAddUserRole(role);
        adminSteps.selectAddStatus(status);
        adminSteps.enterPassword(password);
        adminSteps.enterConfirmPassword(password);
    }

    @Step("Step 7 – Click Save button")
    private void step_Click_Save() {
        adminSteps.clickSave();
    }

    @Step("Step 8 – Verify error message is displayed for invalid Employee Name")
    private void step_Verify_InvalidEmployee_Error_Displayed() {
        String errorMessage = adminPage.getEmployeeNameErrorMessage();
        Assert.assertTrue(errorMessage.contains("Invalid"),
                "Expected 'Invalid' error, but got: " + errorMessage);
        Allure.step("Proper error message displayed for invalid Employee Name: " + errorMessage);
    }
}
