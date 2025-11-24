package org.example.hrmOrange.testcase.ui.admin;

import io.qameta.allure.*;
import io.qameta.allure.model.Status;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.Assert;
import org.testng.annotations.Test;

@Story("Search with non-existent employee name")
@Severity(SeverityLevel.NORMAL)
public class OrangeHRM_Admin_TC5_SearchWithInvalidEmployee extends BaseAdminTest {

    @Test
    @TestCaseID("OrangeHRM_TC15")
    @Description("Verify that entering a non-existent employee name shows 'Invalid' message")
    @Severity(SeverityLevel.NORMAL)
    public void searchWithInvalidEmployee() {

        final String username = "Admin";
        final String password = "admin123";
        final String nonExistentEmployeeName = "NonExistent Employee Name";

        Allure.addAttachment("Test Data",
                "Login Username: " + username + "\nLogin Password: " + password + "\nNon-existent Employee Name: " + nonExistentEmployeeName);

        step_Navigate_To_Login_Page();
        step_Login(username, password);
        step_Verify_Dashboard_Page_Displayed();
        step_Navigate_To_Admin_Page();
        step_Enter_Employee_Name(nonExistentEmployeeName);
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

    @Step("Step 5 – Enter non-existent employee name: {employeeName}")
    private void step_Enter_Employee_Name(String employeeName) {
        // Nhập trực tiếp tên không có trong hệ thống (không chọn từ autocomplete)
        webKeyword.fill("//input[@placeholder='Type for hints...']", employeeName);
    }

    @Step("Step 6 – Click Search button")
    private void step_Click_Search() {
        adminSteps.clickSearch();
    }

    @Step("Step 7 – Verify 'Invalid' message is displayed")
    private void step_Verify_No_Records_Found_Message_Displayed() {
        boolean isInvalidMessageVisible = adminPage.isEmployeeInvalidMessageVisible();

        if (!isInvalidMessageVisible) {
            Allure.step("Expected 'Invalid' message not displayed!", Status.FAILED);
            Assert.fail("Expected 'Invalid' message not displayed!");
        }

        Allure.step("'Invalid' message is displayed correctly");
    }
}
