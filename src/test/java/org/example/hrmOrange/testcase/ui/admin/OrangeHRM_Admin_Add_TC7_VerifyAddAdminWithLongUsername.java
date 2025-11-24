package org.example.hrmOrange.testcase.ui.admin;

import io.qameta.allure.*;
import io.qameta.allure.model.Status;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.Assert;
import org.testng.annotations.Test;

@Story("Add new Admin user - Negative scenario")
@Severity(SeverityLevel.NORMAL)
public class OrangeHRM_Admin_Add_TC7_VerifyAddAdminWithLongUsername extends BaseAdminTest {

    @Test
    @TestCaseID("OrangeHRM_TC_Add07")
    @Description("Verify that entering Username with more than 40 characters shows 'Should not exceed 40 characters' error")
    @Severity(SeverityLevel.NORMAL)
    public void verifyAddAdminWithLongUsername() {

        final String username = "Admin";
        final String password = "admin123";
        final String newEmployeeName = "Akhil Jose";
        final String newUsername = "ThisIsAVeryLongUsernameWithMoreThan40Characters123"; // 51 characters
        final String userRole = "Admin";
        final String status = "Enabled";

        Allure.addAttachment("Test Data",
                "Login Username: " + username + "\nLogin Password: " + password +
                        "\nEmployee Name: " + newEmployeeName +
                        "\nNew Username: " + newUsername + " (" + newUsername.length() + " characters)" +
                        "\nUser Role: " + userRole + "\nStatus: " + status);

        step_Navigate_To_Login_Page();
        step_Login(username, password);
        step_Verify_Dashboard_Page_Displayed();
        step_Navigate_To_Admin_Page();
        step_Click_Add_Button();
        step_Enter_Employee_Name(newEmployeeName);
        step_Enter_Username(newUsername);
        step_Verify_MaxLength_Error_Displayed();
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

    @Step("Step 8 – Verify 'Should not exceed 40 characters' error message is displayed")
    private void step_Verify_MaxLength_Error_Displayed() {
        boolean isVisible = adminPage.isMaxLengthMessageVisible();

        if (!isVisible) {
            Allure.step("Expected 'Should not exceed 40 characters' error message not displayed!", Status.FAILED);
            Assert.fail("Expected 'Should not exceed 40 characters' error message for Username field, but none was displayed");
        }

        Allure.step("'Should not exceed 40 characters' error message is displayed correctly");
    }
}
