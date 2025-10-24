package org.example.hrmOrange.testcase.ui.login;

import io.qameta.allure.*;
import org.apache.logging.log4j.LogManager;
import io.qameta.allure.model.Status;
import org.apache.logging.log4j.Logger;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.Assert;
import org.testng.annotations.Test;

@Story("Login with invalid credentials")
@Severity(SeverityLevel.NORMAL)
public class OrangeHRM_Login_TC2_VerifyLoginFailsWithInvalidPassword extends BaseLoginTest {
    private static final Logger logger = LogManager.getLogger(OrangeHRM_Login_TC2_VerifyLoginFailsWithInvalidPassword.class);

    @Test
    @TestCaseID("OrangeHRM_TC02")
    @Description("Verify that login fails when using a valid username but invalid password")
    @Severity(SeverityLevel.NORMAL)
    public void loginWithInvalidPassword() {
        // Test Data
        final String username = "Admin";
        final String invalidPassword = "wrongpass";
        final String expectedErrorMessage = "Invalid credentials";

        // Attach test data to report
        Allure.addAttachment("Test Data", 
            "Username: " + username + "\nPassword: " + invalidPassword);

        // Steps
        logger.info("Step 1: Navigate to login page and enter invalid password");
        performLogin(username, invalidPassword);

        // Verification
        logger.info("Step 2: Verify error message is displayed");
        String actualErrorMessage = loginPage.getErrorMessage();
        
        try {
            Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage),
                "Expected error message not shown when logging in with wrong password!");
            
            logger.info("Verification passed: Correct error message is displayed");
            Allure.step("Verification: Error message contains '" + expectedErrorMessage + "'");
        } catch (AssertionError e) {
            Allure.step("Verification failed: " + e.getMessage(), Status.FAILED);
            throw e;
        }

        logger.info("Test case passed: Login failed with invalid password as expected");
        Allure.step("Test case passed: Login failed with invalid password as expected");
    }
}
