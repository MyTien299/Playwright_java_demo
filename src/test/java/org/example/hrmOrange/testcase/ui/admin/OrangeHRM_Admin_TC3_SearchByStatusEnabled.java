package org.example.hrmOrange.testcase.ui.admin;

import io.qameta.allure.*;
import io.qameta.allure.model.Status;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.Assert;
import org.testng.annotations.Test;

@Story("Search by Status")
@Severity(SeverityLevel.CRITICAL)
public class OrangeHRM_Admin_TC3_SearchByStatusEnabled extends BaseAdminTest {

    @Test
    @TestCaseID("OrangeHRM_TC13")
    @Description("Verify that admin search by Status 'Enabled' returns correct data")
    @Severity(SeverityLevel.CRITICAL)
    public void searchByStatusEnabled() {

        final String username = "Admin";
        final String password = "admin123";
        final String status = "Enabled";

        Allure.addAttachment("Test Data",
                "Login Username: " + username + "\nLogin Password: " + password + "\nStatus: " + status);

        step_Navigate_To_Login_Page();
        step_Login(username, password);
        step_Verify_Dashboard_Page_Displayed();
        step_Navigate_To_Admin_Page();
        step_Select_Status(status);
        step_Click_Search();
        step_Verify_Result_Table_Has_Data();
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

    @Step("Step 5 – Select Status: {status}")
    private void step_Select_Status(String status) {
        adminSteps.selectStatus(status);
    }

    @Step("Step 6 – Click Search button")
    private void step_Click_Search() {
        adminSteps.clickSearch();
    }

    @Step("Step 7 – Verify at least one result is displayed in the table")
    private void step_Verify_Result_Table_Has_Data() {
        boolean hasData = adminPage.isResultTableVisible();

        if (!hasData) {
            Allure.step("No enabled users found in the result table!", Status.FAILED);
            Assert.fail("No enabled users found in the result table!");
        }

        Allure.step("At least one enabled user is displayed in the result table");
    }
}
