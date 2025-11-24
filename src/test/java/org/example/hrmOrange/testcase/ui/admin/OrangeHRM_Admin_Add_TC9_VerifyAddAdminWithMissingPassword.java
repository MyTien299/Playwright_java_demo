package org.example.hrmOrange.testcase.ui.admin;

import io.qameta.allure.*;
import io.qameta.allure.model.Status;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.Assert;
import org.testng.annotations.Test;

@Story("Add new Admin user - Negative scenario")
@Severity(SeverityLevel.CRITICAL)
public class OrangeHRM_Admin_Add_TC9_VerifyAddAdminWithMissingPassword extends BaseAdminTest {

    @Test
    @TestCaseID("OrangeHRM_TC_Add09")
    @Description("Verify that leaving Password field empty in Add Admin form shows 'Required' error")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyAddAdminWithMissingPassword() {

        final String username = "Admin";
        final String password = "admin123";
        final String newEmployeeName = "Akh";
        final String newUsername = "TestAdmin09";
        final String userRole = "Admin";
        final String status = "Enabled";

        Allure.addAttachment("Test Data",
                "Login Username: " + username + "\nLogin Password: " + password +
                        "\nEmployee Name: " + newEmployeeName +
                        "\nNew Username: " + newUsername +
                        "\nUser Role: " + userRole +
                        "\nStatus: " + status +
                        "\nPassword: (empty)");

        step_Navigate_To_Login_Page();
        step_Login(username, password);
        step_Verify_Dashboard_Page_Displayed();
        step_Navigate_To_Admin_Page();
        step_Click_Add_Button();
        step_Enter_Employee_Name(newEmployeeName);
        step_Enter_Username(newUsername);
        step_Select_User_Role(userRole);
        step_Select_Status(status);
        // Skip Password - để trống
        step_Click_Save();
        step_Verify_PasswordRequired_Error_Displayed();
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

    @Step("Step 10 – Click Save button")
    private void step_Click_Save() {
        adminSteps.clickSave();
    }

    @Step("Step 11 – Verify 'Required' error message is displayed for Password")
    private void step_Verify_PasswordRequired_Error_Displayed() {
        boolean isVisible = adminPage.isRequiredMessageVisible();

        if (!isVisible) {
            Allure.step("Expected 'Required' error message for Password not displayed!", Status.FAILED);
            Assert.fail("Expected 'Required' error message for Password, but none was displayed");
        }

        Allure.step("'Required' error message is displayed correctly for missing Password");
    }
}
