package org.example.hrmOrange.testcase.ui.login;

import io.qameta.allure.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.model.Status;
import org.example.hrmOrange.page.login.LoginPage;
//
////@Story("Login with empty fields")
////@Severity(SeverityLevel.NORMAL)
////public class OrangeHRM_Login_TC5_VerifyLoginFailsWithEmptyUsername extends BaseLoginTest {
////    private static final Logger logger = LogManager.getLogger(OrangeHRM_Login_TC5_VerifyLoginFailsWithEmptyUsername.class);
////
////    @Test
////    @TestCaseID("OrangeHRM_TC05")
////    @Description("Verify that login fails when username field is left empty")
////    @Severity(SeverityLevel.NORMAL)
////    public void loginWithEmptyUsername() {
////        // Test Data
////        final String emptyUsername = "";
////        final String password = "admin123";
////        final String expectedErrorMessage = "Required";
////
////        // Attach test data to report
////        Allure.addAttachment("Test Data",
////            "Username: []\nPassword: " + password);
////
////        // Steps
////        logger.info("Step 1: Navigate to login page and leave username empty");
////        performLogin(emptyUsername, password);
////
////        // Verification
////        logger.info("Step 2: Verify required field message is displayed");
////        String actualErrorMessage = loginPage.getRequiredMessage();
////
////        try {
////            Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage),
////                "Expected 'Required' message not shown when username is empty!");
////
////            logger.info("Verification passed: Required field message is displayed");
////            Allure.step("Verification: Error message contains '" + expectedErrorMessage + "'");
////        } catch (AssertionError e) {
////            Allure.step("Verification failed: " + e.getMessage(), Status.FAILED);
////            throw e;
////        }
////
////        logger.info("Test case passed: Login failed with empty username as expected");
////        Allure.step("Test case passed: Login failed with empty username as expected");
////    }
////}
@Story("Login with empty fields")
@Severity(SeverityLevel.NORMAL)
public class OrangeHRM_Login_TC5_VerifyLoginFailsWithEmptyUsername extends BaseLoginTest {

    @Test
    @TestCaseID("OrangeHRM_TC05")
    @Description("Verify that login fails when username field is left empty")
    @Severity(SeverityLevel.NORMAL)
    public void loginWithEmptyUsername() {

        final String emptyUsername = "";
        final String password = "admin123";
        final String expectedErrorMessage = "Required";

        Allure.addAttachment("Test Data",
                "Username: [EMPTY]\nPassword: " + password);

        step_Navigate_To_Login_Page();
        step_Login_With_Empty_Username(emptyUsername, password);
        step_Verify_Required_Message_Displayed(expectedErrorMessage);
    }

    @Step("Step 1 – Navigate to login page")
    private void step_Navigate_To_Login_Page() {
        loginSteps.navigateToLoginPage();
    }

    @Step("Step 2 – Try to login with EMPTY username, password: {password}")
    private void step_Login_With_Empty_Username(String emptyUsername, String password) {
        loginSteps.login(emptyUsername, password);
    }

    @Step("Step 3 – Verify 'Required' message is displayed on username field")
    private void step_Verify_Required_Message_Displayed(String expectedErrorMessage) {
        String actualMessage = loginPage.getRequiredMessage();

        if (!actualMessage.contains(expectedErrorMessage)) {
            Allure.step("Required message NOT displayed! Actual: " + actualMessage, Status.FAILED);
            Assert.fail("Expected 'Required' but got: " + actualMessage);
        }

        Allure.step("Required message displayed: " + actualMessage);
    }
}