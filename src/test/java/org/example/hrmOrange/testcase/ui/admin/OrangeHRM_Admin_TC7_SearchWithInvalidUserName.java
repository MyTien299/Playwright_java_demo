package org.example.hrmOrange.testcase.ui.admin;

import io.qameta.allure.*;
import io.qameta.allure.model.Status;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.Assert;
import org.testng.annotations.Test;

@Story("Search with invalid username")
@Severity(SeverityLevel.NORMAL)
public class OrangeHRM_Admin_TC7_SearchWithInvalidUserName extends BaseAdminTest {

    @Test
    @TestCaseID("OrangeHRM_TC17")
    @Description("Verify that searching with invalid username shows 'No Records Found'")
    @Severity(SeverityLevel.NORMAL)
    public void searchWithInvalidEmployee() {

        final String username = "Admin";
        final String password = "admin123";
        final String invalidUsername = "Invalid Name XYZ";

        Allure.addAttachment("Test Data",
                "Login Username: " + username + "\nLogin Password: " + password + "\nInvalid Username: " + invalidUsername);

        step_Navigate_To_Login_Page();
        step_Login(username, password);
        step_Verify_Dashboard_Page_Displayed();
        step_Navigate_To_Admin_Page();
        step_Enter_Invalid_Username(invalidUsername);
        step_Click_Search();
        step_Verify_No_Records_Found_Message_Displayed();
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

    @Step("Step 5 – Enter invalid username: {invalidUsername}")
    private void step_Enter_Invalid_Username(String invalidUsername) {
        adminSteps.fillUsername(invalidUsername);
    }

    @Step("Step 6 – Click Search button")
    private void step_Click_Search() {
        adminSteps.clickSearch();
    }

    @Step("Step 7 – Verify 'No Records Found' message is displayed")
    private void step_Verify_No_Records_Found_Message_Displayed() {
        boolean isNoRecordFound = adminPage.isNoRecordFound();

        if (!isNoRecordFound) {
            Allure.step("Expected 'No Records Found' message not displayed!", Status.FAILED);
            Assert.fail("Expected 'No Records Found' message not displayed!");
        }

        Allure.step("'No Records Found' message is displayed correctly");
    }
}
