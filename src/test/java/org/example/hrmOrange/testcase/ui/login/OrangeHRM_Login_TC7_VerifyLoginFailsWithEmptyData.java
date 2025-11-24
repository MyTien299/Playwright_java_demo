package org.example.hrmOrange.testcase.ui.login;

import io.qameta.allure.*;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.model.Status;

@Story("Login with empty fields")
@Severity(SeverityLevel.NORMAL)
public class OrangeHRM_Login_TC7_VerifyLoginFailsWithEmptyData extends BaseLoginTest {

    @Test
    @TestCaseID("OrangeHRM_TC07")
    @Description("Verify that login fails when both username and password fields are empty")
    @Severity(SeverityLevel.NORMAL)
    public void loginWithEmptyUsernameAndPassword() {
        final String emptyUsername = "";
        final String emptyPassword = "";
        final String expectedErrorMessage = "Required";

        Allure.addAttachment("Test Data",
                "Username: [EMPTY]\nPassword: [EMPTY]");

        step_Navigate_To_Login_Page();
        step_Login_With_Empty_Credentials(emptyUsername, emptyPassword);
        step_Verify_Both_Required_Messages(expectedErrorMessage);
    }

    @Step("Step 1 – Navigate to login page")
    private void step_Navigate_To_Login_Page() {
        loginSteps.navigateToLoginPage();
    }

    @Step("Step 2 – Try to login with EMPTY username and EMPTY password")
    private void step_Login_With_Empty_Credentials(String emptyUsername, String emptyPassword) {
        loginSteps.login(emptyUsername, emptyPassword);
    }

    @Step("Step 3 – Verify both username and password show 'Required' messages")
    private void step_Verify_Both_Required_Messages(String expectedErrorMessage) {
        String usernameError = loginPage.getUsernameRequiredMessage();
        String passwordError = loginPage.getPasswordRequiredMessage();

        if (!usernameError.contains(expectedErrorMessage)) {
            Allure.step("Username required message NOT displayed! Actual: " + usernameError, Status.FAILED);
            Assert.fail("Expected 'Required' on username but got: " + usernameError);
        }

        if (!passwordError.contains(expectedErrorMessage)) {
            Allure.step("Password required message NOT displayed! Actual: " + passwordError, Status.FAILED);
            Assert.fail("Expected 'Required' on password but got: " + passwordError);
        }

        Allure.step("Both required messages are displayed: username='" + usernameError + "', password='" + passwordError + "'");
    }
}
