package org.example.hrmOrange.testcase.ui.admin;

import io.qameta.allure.*;
import io.qameta.allure.model.Status;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.Assert;
import org.testng.annotations.Test;

@Story("Search admin by username")
@Severity(SeverityLevel.CRITICAL)
public class OrangeHRM_Admin_TC1_VerifySearchAdminByUsername extends BaseAdminTest {

    @Test
    @TestCaseID("OrangeHRM_TC11")
    @Description("Verify that admin search by username 'Admin' returns correct data")
    @Severity(SeverityLevel.CRITICAL)
    public void verifySearchAdminByUsername() {

        final String username = "Admin";
        final String password = "admin123";
        final String searchUsername = "Admin";

        Allure.addAttachment("Test Data",
                "Login Username: " + username + "\nLogin Password: " + password + "\nSearch Username: " + searchUsername);

        step_Navigate_To_Login_Page();
        step_Login(username, password);
        step_Verify_Dashboard_Page_Displayed();
        step_Navigate_To_Admin_Page();
        step_Search_Admin_By_Username(searchUsername);
        step_Verify_Admin_Displayed_In_Table(searchUsername);
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

    @Step("Step 5 – Search admin by username: {username}")
    private void step_Search_Admin_By_Username(String username) {
        adminPage.searchAdminByUsername(username);
    }

    @Step("Step 6 – Verify admin user '{username}' is displayed in the result table")
    private void step_Verify_Admin_Displayed_In_Table(String username) {
        boolean isDisplayed = adminPage.isAdminDisplayedInTable(username);

        if (!isDisplayed) {
            Allure.step("Admin user not found in the result table!", Status.FAILED);
            Assert.fail("Admin user '" + username + "' not found in the result table!");
        }

        Allure.step("Admin user '" + username + "' is displayed correctly in the result table");
    }
}
