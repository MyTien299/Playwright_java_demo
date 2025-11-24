package org.example.hrmOrange.testcase.ui.admin;

import io.qameta.allure.*;
import io.qameta.allure.model.Status;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.Assert;
import org.testng.annotations.Test;

@Story("Add new Admin user")
@Severity(SeverityLevel.CRITICAL)
public class OrangeHRM_Admin_Add_TC1_VerifyAddNewAdmin extends BaseAdminTest {
    @Test
    @TestCaseID("OrangeHRM_TC_Add01")
    @Description("Verify that adding a new Admin user works correctly")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyAddNewAdmin() {

        final String username = "Admin";
        final String password = "admin123";
        final String newEmployeeName = "Akh";
        final String newUsername = "TestAdmin01";
        final String userRole = "Admin";
        final String status = "Enabled";

        Allure.addAttachment("Test Data",
                "Login Username: " + username + "\nLogin Password: " + password +
                        "\nNew Employee Name: " + newEmployeeName +
                        "\nNew Username: " + newUsername +
                        "\nUser Role: " + userRole + "\nStatus: " + status);

        step_Navigate_To_Login_Page();
        step_Login(username, password);
        step_Verify_Dashboard_Page_Displayed();
        step_Navigate_To_Admin_Page();
        step_Click_Add_Button();
        step_Fill_Add_Admin_Form(newEmployeeName, newUsername, userRole, status, password);
        step_Click_Save();
        step_Search_New_Admin(newUsername);
        step_Verify_New_Admin_Displayed(newUsername);
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

    @Step("Step 8 – Search for new admin user: {username}")
    private void step_Search_New_Admin(String username) {
        adminPage.waitUntilTableVisible(20000);
        adminPage.searchAdminByUsername(username);
    }

    @Step("Step 9 – Verify new admin user '{username}' is displayed in the result table")
    private void step_Verify_New_Admin_Displayed(String username) {
        adminPage.waitUntilTableVisible(10000);
        boolean isDisplayed = adminPage.isAdminDisplayedInTable(username);

        if (!isDisplayed) {
            Allure.step("New Admin user not found in the result table!", Status.FAILED);
            Assert.fail("New Admin user '" + username + "' not found in the result table!");
        }

        Allure.step("New Admin user '" + username + "' is displayed correctly in the result table");
    }
}
