package org.example.hrmOrange.testcase.ui.login;

import io.qameta.allure.*;
import io.qameta.allure.model.Status;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.Assert;
import org.testng.annotations.Test;

@Story("Login with username formatting")
@Severity(SeverityLevel.MINOR)
public class OrangeHRM_Login_TC8_VerifyThatUserCanLoginWithUsernameHavingLeadingSpace extends BaseLoginTest {

    @Test
    @TestCaseID("OrangeHRM_TC08")
    @Description("Verify that login fails when username starts with whitespace")
    @Severity(SeverityLevel.MINOR)
    public void loginWithUsernameHavingLeadingSpace() {
        final String usernameWithLeadingSpace = " Admin";
        final String password = "admin123";
        final String expectedErrorMessage = "Invalid credentials";

        Allure.addAttachment("Test Data",
                "Username: '" + usernameWithLeadingSpace + "' (with leading space)\nPassword: " + password);

        step_Navigate_To_Login_Page();
        step_Login(usernameWithLeadingSpace, password);
        step_Verify_Error_Message_Displayed(expectedErrorMessage);
    }

    @Step("Step 1 – Navigate to Login Page")
    private void step_Navigate_To_Login_Page() {
        loginSteps.navigateToLoginPage();
    }

    @Step("Step 2 – Login with username (leading space): {username}, password: {password}")
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
