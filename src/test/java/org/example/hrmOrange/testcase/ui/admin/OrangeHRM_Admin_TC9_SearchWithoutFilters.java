package org.example.hrmOrange.testcase.ui.admin;

import io.qameta.allure.*;
import io.qameta.allure.model.Status;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.Assert;
import org.testng.annotations.Test;

@Story("Search without filters")
@Severity(SeverityLevel.NORMAL)
public class OrangeHRM_Admin_TC9_SearchWithoutFilters extends BaseAdminTest {

    @Test
    @TestCaseID("OrangeHRM_TC19")
    @Description("Verify that searching without filters displays all users")
    @Severity(SeverityLevel.NORMAL)
    public void searchWithoutAnyFilters() {

        final String username = "Admin";
        final String password = "admin123";

        Allure.addAttachment("Test Data",
                "Login Username: " + username + "\nLogin Password: " + password + "\nFilters: None");

        step_Navigate_To_Login_Page();
        step_Login(username, password);
        step_Verify_Dashboard_Page_Displayed();
        step_Navigate_To_Admin_Page();
        step_Click_Search_Without_Filters();
        step_Verify_User_List_Displayed();
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

    @Step("Step 5 – Click Search button without entering any filters")
    private void step_Click_Search_Without_Filters() {
        adminSteps.clickSearch();
    }

    @Step("Step 6 – Verify user list is displayed")
    private void step_Verify_User_List_Displayed() {
        boolean isTableVisible = adminPage.isResultTableVisible();

        if (!isTableVisible) {
            Allure.step("User list not displayed!", Status.FAILED);
            Assert.fail("User list not displayed!");
        }

        Allure.step("User list is displayed correctly");
    }
}
