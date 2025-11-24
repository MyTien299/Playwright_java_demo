package org.example.hrmOrange.testcase.ui.login;

import io.qameta.allure.*;
import io.qameta.allure.model.Status;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.Assert;
import org.testng.annotations.Test;

@Story("Login with invalid credentials")
@Severity(SeverityLevel.NORMAL)
public class OrangeHRM_Login_TC4_VerifyLoginFailsWithInvalidData extends BaseLoginTest {

    @Test
    @TestCaseID("OrangeHRM_TC04")
    @Description("Verify that login fails when both username and password are invalid")
    @Severity(SeverityLevel.NORMAL)
    public void loginWithInvalidUsernameAndPassword() {
        final String invalidUsername = "wrongUser";
        final String invalidPassword = "wrongPass";
        final String expectedErrorMessage = "Invalid credentials";

        Allure.addAttachment("Test Data",
                "Username: " + invalidUsername + "\nPassword: " + invalidPassword);

        step_Navigate_To_Login_Page();
        step_Login(invalidUsername, invalidPassword);
        step_Verify_Error_Message_Displayed(expectedErrorMessage);
        Allure.step("Test case passed: Login failed with invalid credentials as expected");
    }

    @Step("Step 1 – Navigate to Login Page")
    private void step_Navigate_To_Login_Page() {
        loginSteps.navigateToLoginPage();
    }

    @Step("Step 2 – Login with username: {username}, password: {password}")
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
