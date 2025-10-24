package org.example.hrmOrange.testcase.ui.login;

import io.qameta.allure.*;
import io.qameta.allure.model.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.Assert;
import org.testng.annotations.Test;


@Story("Login with password formatting")
@Severity(SeverityLevel.MINOR)
public class OrangeHRM_Login_TC10_VerifyLoginFailsWithPasswordTrailingSpace extends BaseLoginTest {
    private static final Logger logger = LogManager.getLogger(OrangeHRM_Login_TC10_VerifyLoginFailsWithPasswordTrailingSpace.class);

    @Test
    @TestCaseID("OrangeHRM_TC10")
    @Description("Verify that login fails when password ends with whitespace")
    @Severity(SeverityLevel.MINOR)
    public void loginWithPasswordTrailingSpace() {
        // Test Data
        final String username = "Admin";
        final String passwordWithTrailingSpace = "admin123 ";
        final String expectedErrorMessage = "Invalid credentials";

        // Attach test data to report
        Allure.addAttachment("Test Data", 
            "Username: " + username + "\nPassword: '" + passwordWithTrailingSpace + "' (with trailing space)");

        // Steps
        logger.info("Step 1: Navigate to login page and enter password with trailing space");
        performLogin(username, passwordWithTrailingSpace);

        // Verification
        logger.info("Step 2: Verify error message is displayed");
        String actualErrorMessage = loginPage.getErrorMessage();
        
        try {
            Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage),
                "Expected 'Invalid credentials' message not shown when password has trailing space!");
            
            logger.info("Verification passed: Correct error message is displayed");
            Allure.step("Verification: Error message contains '" + expectedErrorMessage + "'");
        } catch (AssertionError e) {
            Allure.step("Verification failed: " + e.getMessage(), Status.FAILED);
            throw e;
        }

        logger.info("Test case passed: Login failed with password having trailing space as expected");
        Allure.step("Test case passed: Login failed with password having trailing space as expected");
    }
}
