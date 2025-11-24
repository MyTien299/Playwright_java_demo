package org.example.hrmOrange.testcase.ui.login;

import io.qameta.allure.*;
import io.qameta.allure.model.Status;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.Assert;
import org.testng.annotations.Test;

@Story("Login with password formatting")
@Severity(SeverityLevel.MINOR)
public class OrangeHRM_Login_TC9_VerifyLoginFailsWithPasswordLeadingSpace extends BaseLoginTest {

    @Test
    @TestCaseID("OrangeHRM_TC09")
    @Description("Verify that login fails when password starts with whitespace")
    @Severity(SeverityLevel.MINOR)
    public void loginWithPasswordHavingLeadingSpace() {
        final String username = "Admin";
        final String passwordWithLeadingSpace = " admin123";
        final String expectedErrorMessage = "Invalid credentials";

        Allure.addAttachment("Test Data",
                "Username: " + username + "\nPassword: '" + passwordWithLeadingSpace + "' (with leading space)");

        step_Navigate_To_Login_Page();
        step_Login(username, passwordWithLeadingSpace);
        step_Verify_Error_Message_Displayed(expectedErrorMessage);
    }

    @Step("Step 1 – Navigate to Login Page")
    private void step_Navigate_To_Login_Page() {
        loginSteps.navigateToLoginPage();
    }

    @Step("Step 2 – Login with username: {username}, password (leading space): {password}")
    private void step_Login(String username, String password) {
        loginSteps.login(username, password);
    }

    @Step("Step 3 – Verify error message: {expectedErrorMessage}")
    private void step_Verify_Error_Message_Displayed(String expectedErrorMessage) {
        String actualErrorMessage = loginPage.getErrorMessage();

        if (!actualErrorMessage.contains(expectedErrorMessage)) {
            Allure.step("Error message mismatch! Actual: " + actualErrorMessage, Status.FAILED);
            Assert.fail("Expected: " + expectedErrorMessage + " but found: " + actualErrorMessage);
        }

        Allure.step("Error message is correct: " + actualErrorMessage);
    }
}
