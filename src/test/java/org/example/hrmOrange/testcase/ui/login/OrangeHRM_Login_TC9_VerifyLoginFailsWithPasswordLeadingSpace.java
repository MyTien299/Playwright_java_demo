package org.example.hrmOrange.testcase.ui.login;

import io.qameta.allure.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.model.Status;

@Story("Login with password formatting")
@Severity(SeverityLevel.MINOR)
public class OrangeHRM_Login_TC9_VerifyLoginFailsWithPasswordLeadingSpace extends BaseLoginTest {
    private static final Logger logger = LogManager.getLogger(OrangeHRM_Login_TC9_VerifyLoginFailsWithPasswordLeadingSpace.class);

    @Test
    @TestCaseID("OrangeHRM_TC09")
    @Description("Verify that login fails when password starts with whitespace")
    @Severity(SeverityLevel.MINOR)
    public void loginWithPasswordHavingLeadingSpace() {
        // Test Data
        final String username = "Admin";
        final String passwordWithLeadingSpace = " admin123";
        final String expectedErrorMessage = "Invalid credentials";

        // Attach test data to report
        Allure.addAttachment("Test Data", 
            "Username: " + username + "\nPassword: '" + passwordWithLeadingSpace + "' (with leading space)");

        // Steps
        logger.info("Step 1: Navigate to login page and enter password with leading space");
        performLogin(username, passwordWithLeadingSpace);

        // Verification
        logger.info("Step 2: Verify error message is displayed");
        String actualErrorMessage = loginPage.getErrorMessage();
        
        try {
            Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage),
                "Expected 'Invalid credentials' message not shown when password has leading space!");
            
            logger.info("Verification passed: Correct error message is displayed");
            Allure.step("Verification: Error message contains '" + expectedErrorMessage + "'");
        } catch (AssertionError e) {
            Allure.step("Verification failed: " + e.getMessage(), Status.FAILED);
            throw e;
        }

        logger.info("Test case passed: Login failed with password having leading space as expected");
        Allure.step("Test case passed: Login failed with password having leading space as expected");
    }
}
