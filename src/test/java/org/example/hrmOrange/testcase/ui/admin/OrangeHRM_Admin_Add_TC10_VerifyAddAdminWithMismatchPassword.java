package org.example.hrmOrange.testcase.ui.admin;

import io.qameta.allure.*;
import io.qameta.allure.model.Status;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.Assert;
import org.testng.annotations.Test;

@Story("Add new Admin user - Negative scenario")
@Severity(SeverityLevel.CRITICAL)
public class OrangeHRM_Admin_Add_TC10_VerifyAddAdminWithMismatchPassword extends BaseAdminTest {

    @Test
    @TestCaseID("OrangeHRM_TC_Add10")
    @Description("Verify that entering mismatched Password and Confirm Password shows 'Passwords do not match' error")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyAddAdminWithMismatchPassword() {

        final String username = "Admin";
        final String password = "admin123";
        final String newEmployeeName = "Akh";
        final String newUsername = "TestAdmin10";
        final String userRole = "Admin";
        final String status = "Enabled";
        final String newPassword = "Password123";
        final String confirmPassword = "DifferentPassword456"; // dont match

        Allure.addAttachment("Test Data",
                "Login Username: " + username + "\nLogin Password: " + password +
                        "\nEmployee Name: " + newEmployeeName +
                        "\nNew Username: " + newUsername +
                        "\nUser Role: " + userRole +
                        "\nStatus: " + status +
                        "\nPassword: " + newPassword +
                        "\nConfirm Password: " + confirmPassword + " (mismatch)");

        step_Navigate_To_Login_Page();
        step_Login(username, password);
        step_Verify_Dashboard_Page_Displayed();
        step_Navigate_To_Admin_Page();
        step_Click_Add_Button();
        step_Enter_Employee_Name(newEmployeeName);
        step_Enter_Username(newUsername);
        step_Select_User_Role(userRole);
        step_Select_Status(status);
        step_Enter_Password(newPassword);
        step_Enter_Confirm_Password(confirmPassword);
        step_Click_Save();
        step_Verify_PasswordMismatch_Error_Displayed();
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

    @Step("Step 8 – Select User Role: {userRole}")
    private void step_Select_User_Role(String userRole) {
        adminSteps.selectAddUserRole(userRole);
    }

    @Step("Step 9 – Select Status: {status}")
    private void step_Select_Status(String status) {
        adminSteps.selectAddStatus(status);
    }

    @Step("Step 10 – Enter Password: {password}")
    private void step_Enter_Password(String password) {
        adminSteps.enterPassword(password);
    }

    @Step("Step 11 – Enter Confirm Password: {confirmPassword}")
    private void step_Enter_Confirm_Password(String confirmPassword) {
        adminSteps.enterConfirmPassword(confirmPassword);
    }

    @Step("Step 12 – Click Save button")
    private void step_Click_Save() {
        adminSteps.clickSave();
    }

    @Step("Step 13 – Verify 'Passwords do not match' error message is displayed")
    private void step_Verify_PasswordMismatch_Error_Displayed() {
        boolean isVisible = adminPage.isPasswordMismatchMessageVisible();

        if (!isVisible) {
            Allure.step("Expected 'Passwords do not match' error message not displayed!", Status.FAILED);
            Assert.fail("Expected 'Passwords do not match' error message, but none was displayed");
        }

        Allure.step("'Passwords do not match' error message is displayed correctly");
    }
}
