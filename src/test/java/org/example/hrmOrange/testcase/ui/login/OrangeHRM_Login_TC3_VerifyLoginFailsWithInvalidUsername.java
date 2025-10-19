package org.example.hrmOrange.testcase.ui.login;

import io.qameta.allure.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.model.Status;

@Story("Login with invalid credentials")
@Severity(SeverityLevel.NORMAL)
public class OrangeHRM_Login_TC3_VerifyLoginFailsWithInvalidUsername extends BaseLoginTest {
    private static final Logger logger = LogManager.getLogger(OrangeHRM_Login_TC3_VerifyLoginFailsWithInvalidUsername.class);

    @Test
    @TestCaseID("OrangeHRM_TC03")
    @Description("Verify that login fails when using an invalid username with a valid password")
    @Severity(SeverityLevel.NORMAL)
    public void loginWithInvalidUsername() {
        // Test Data
        final String invalidUsername = "WrongUser";
        final String password = "admin123";
        final String expectedErrorMessage = "Invalid credentials";

        // Attach test data to report
        Allure.addAttachment("Test Data", 
            "Username: " + invalidUsername + "\nPassword: " + password);

        // Steps
        logger.info("Step 1: Navigate to login page and enter invalid username");
        performLogin(invalidUsername, password);

        // Verification
        logger.info("Step 2: Verify error message is displayed");
        String actualErrorMessage = loginPage.getErrorMessage();
        
        try {
            Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage),
                "Expected error message not shown when logging in with invalid username!");
            
            logger.info("Verification passed: Correct error message is displayed");
            Allure.step("Verification: Error message contains '" + expectedErrorMessage + "'");
        } catch (AssertionError e) {
            Allure.step("Verification failed: " + e.getMessage(), Status.FAILED);
            throw e;
        }

        logger.info("Test case passed: Login failed with invalid username as expected");
        Allure.step("Test case passed: Login failed with invalid username as expected");
    }
}
