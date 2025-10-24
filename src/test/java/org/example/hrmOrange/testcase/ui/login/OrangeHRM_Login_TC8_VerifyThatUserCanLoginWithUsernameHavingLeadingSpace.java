package org.example.hrmOrange.testcase.ui.login;

import io.qameta.allure.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.model.Status;

@Story("Login with username formatting")
@Severity(SeverityLevel.MINOR)
public class OrangeHRM_Login_TC8_VerifyThatUserCanLoginWithUsernameHavingLeadingSpace extends BaseLoginTest {
    private static final Logger logger = LogManager.getLogger(OrangeHRM_Login_TC8_VerifyThatUserCanLoginWithUsernameHavingLeadingSpace.class);

    @Test
    @TestCaseID("OrangeHRM_TC08")
    @Description("Verify that login fails when username starts with whitespace")
    @Severity(SeverityLevel.MINOR)
    public void loginWithUsernameHavingLeadingSpace() {
        // Test Data
        final String usernameWithLeadingSpace = " Admin";
        final String password = "admin123";
        final String expectedErrorMessage = "Invalid credentials";

        // Attach test data to report
        Allure.addAttachment("Test Data", 
            "Username: '" + usernameWithLeadingSpace + "' (with leading space)\nPassword: " + password);

        // Steps
        logger.info("Step 1: Navigate to login page and enter username with leading space");
        performLogin(usernameWithLeadingSpace, password);

        // Verification
        logger.info("Step 2: Verify error message is displayed");
        String actualErrorMessage = loginPage.getErrorMessage();
        
        try {
            Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage),
                "Expected 'Invalid credentials' message not shown when username has leading space!");
            
            logger.info("Verification passed: Correct error message is displayed");
            Allure.step("Verification: Error message contains '" + expectedErrorMessage + "'");
        } catch (AssertionError e) {
            Allure.step("Verification failed: " + e.getMessage(), Status.FAILED);
            throw e;
        }

        logger.info("Test case passed: Login failed with username having leading space as expected");
        Allure.step("Test case passed: Login failed with username having leading space as expected");
    }
}
