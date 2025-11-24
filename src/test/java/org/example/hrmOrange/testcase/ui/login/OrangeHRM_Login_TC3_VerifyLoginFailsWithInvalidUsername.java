package org.example.hrmOrange.testcase.ui.login;

import io.qameta.allure.*;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.model.Status;

@Story("Login with invalid credentials")
@Severity(SeverityLevel.NORMAL)
public class OrangeHRM_Login_TC3_VerifyLoginFailsWithInvalidUsername extends BaseLoginTest {

    @Test
    @TestCaseID("OrangeHRM_TC03")
    @Description("Verify that login fails when using an invalid username with a valid password")
    @Severity(SeverityLevel.NORMAL)
    public void loginWithInvalidUsername() {
        final String invalidUsername = "WrongUser";
        final String password = "admin123";
        final String expectedErrorMessage = "Invalid credentials";

        Allure.addAttachment("Test Data",
                "Username: " + invalidUsername + "\nPassword: " + password);

        step_Navigate_To_Login_Page();
        step_Login(invalidUsername, password);
        step_Verify_Error_Message_Displayed(expectedErrorMessage);
    }

    @Step("Step 1 – Navigate to Login Page")
    private void step_Navigate_To_Login_Page() {
        loginSteps.navigateToLoginPage();
    }

    @Step("Step 2 – Login with invalid username: {username}, password: {password}")
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
