package org.example.hrmOrange.testcase.ui.login;

import io.qameta.allure.*;
import io.qameta.allure.model.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.Assert;
import org.testng.annotations.Test;

@Story("Login with valid credentials")
@Severity(SeverityLevel.CRITICAL)
public class OrangeHRM_Login_TC1_VerifyThatUserCanLoginWithValidDataSuccessfully extends BaseLoginTest {

    @Test
    @TestCaseID("OrangeHRM_TC01")
    @Description("Verify that user can successfully log in with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    public void loginWithValidUser() {

        final String username = "Admin";
        final String password = "admin123";
        final String expectedTitle = "Dashboard";

        Allure.addAttachment("Test Data",
                "Username: " + username + "\nPassword: " + password);

        step_Navigate_To_Login_Page();
        step_Login(username, password);
        step_Verify_Dashboard_Page_Displayed();
        step_Verify_Dashboard_Title(expectedTitle);
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

    @Step("Step 4 – Verify dashboard title should be: {expectedTitle}")
    private void step_Verify_Dashboard_Title(String expectedTitle) {
        String actualTitle = dashboardComponent.getDashboardTitle().trim();

        if (!actualTitle.equals(expectedTitle)) {
            Allure.step("Dashboard title mismatch! Actual: " + actualTitle, Status.FAILED);
            Assert.fail("Expected: " + expectedTitle + " but found: " + actualTitle);
        }

        Allure.step("Dashboard title is correct: " + actualTitle);
    }
}

