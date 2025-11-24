package org.example.hrmOrange.testcase.ui.login;

import io.qameta.allure.*;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.model.Status;

@Story("Login with empty fields")
@Severity(SeverityLevel.NORMAL)
public class OrangeHRM_Login_TC6_VerifyLoginFailsWithEmptyPassword extends BaseLoginTest {

    @Test
    @TestCaseID("OrangeHRM_TC06")
    @Description("Verify that login fails when password field is left empty")
    @Severity(SeverityLevel.NORMAL)
    public void loginWithEmptyPassword() {
        final String username = "Admin";
        final String emptyPassword = "";
        final String expectedErrorMessage = "Required";

        Allure.addAttachment("Test Data",
                "Username: " + username + "\nPassword: [EMPTY]");

        step_Navigate_To_Login_Page();
        step_Login_With_Empty_Password(username, emptyPassword);
        step_Verify_Required_Message_Displayed(expectedErrorMessage);
    }

    @Step("Step 1 – Navigate to login page")
    private void step_Navigate_To_Login_Page() {
        loginSteps.navigateToLoginPage();
    }

    @Step("Step 2 – Try to login with username: {username}, EMPTY password")
    private void step_Login_With_Empty_Password(String username, String emptyPassword) {
        loginSteps.login(username, emptyPassword);
    }

    @Step("Step 3 – Verify 'Required' message is displayed on password field")
    private void step_Verify_Required_Message_Displayed(String expectedErrorMessage) {
        String actualMessage = loginPage.getRequiredMessage();

        if (!actualMessage.contains(expectedErrorMessage)) {
            Allure.step("Required message NOT displayed! Actual: " + actualMessage, Status.FAILED);
            Assert.fail("Expected 'Required' but got: " + actualMessage);
        }

        Allure.step("Required message displayed: " + actualMessage);
    }
}
