package org.example.hrmOrange.testcase.ui.login;

import io.qameta.allure.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.model.Status;

@Story("Login with empty fields")
@Severity(SeverityLevel.NORMAL)
public class OrangeHRM_Login_TC7_VerifyLoginFailsWithEmptyData extends BaseLoginTest {
    private static final Logger logger = LogManager.getLogger(OrangeHRM_Login_TC7_VerifyLoginFailsWithEmptyData.class);

    @Test
    @TestCaseID("OrangeHRM_TC07")
    @Description("Verify that login fails when both username and password fields are empty")
    @Severity(SeverityLevel.NORMAL)
    public void loginWithEmptyUsernameAndPassword() {
        // Test Data
        final String emptyUsername = "";
        final String emptyPassword = "";
        final String expectedErrorMessage = "Required";

        // Attach test data to report
        Allure.addAttachment("Test Data", 
            "Username: [EMPTY]\nPassword: [EMPTY]");

        // Steps
        logger.info("Step 1: Navigate to login page and leave both fields empty");
        performLogin(emptyUsername, emptyPassword);

        // Verification
        logger.info("Step 2: Verify required field messages are displayed");
        String usernameError = loginPage.getUsernameRequiredMessage();
        String passwordError = loginPage.getPasswordRequiredMessage();
        
        try {
            // Verify username error message
            Assert.assertTrue(usernameError.contains(expectedErrorMessage),
                "Expected 'Required' message not shown for empty username!");
            Allure.step("Verification: Username error message contains '" + expectedErrorMessage + "'");
            
            // Verify password error message
            Assert.assertTrue(passwordError.contains(expectedErrorMessage),
                "Expected 'Required' message not shown for empty password!");
            Allure.step("Verification: Password error message contains '" + expectedErrorMessage + "'");
            
            logger.info("Verification passed: Required field messages are displayed");
        } catch (AssertionError e) {
            Allure.step("Verification failed: " + e.getMessage(), Status.FAILED);
            throw e;
        }

        logger.info("Test case passed: Login failed with empty username and password as expected");
        Allure.step("Test case passed: Login failed with empty username and password as expected");
    }
}
