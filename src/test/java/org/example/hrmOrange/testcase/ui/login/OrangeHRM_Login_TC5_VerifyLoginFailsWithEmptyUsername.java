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
public class OrangeHRM_Login_TC5_VerifyLoginFailsWithEmptyUsername extends BaseLoginTest {
    private static final Logger logger = LogManager.getLogger(OrangeHRM_Login_TC5_VerifyLoginFailsWithEmptyUsername.class);

    @Test
    @TestCaseID("OrangeHRM_TC05")
    @Description("Verify that login fails when username field is left empty")
    @Severity(SeverityLevel.NORMAL)
    public void loginWithEmptyUsername() {
        // Test Data
        final String emptyUsername = "";
        final String password = "admin123";
        final String expectedErrorMessage = "Required";

        // Attach test data to report
        Allure.addAttachment("Test Data", 
            "Username: [EMPTY]\nPassword: " + password);

        // Steps
        logger.info("Step 1: Navigate to login page and leave username empty");
        performLogin(emptyUsername, password);

        // Verification
        logger.info("Step 2: Verify required field message is displayed");
        String actualErrorMessage = loginPage.getRequiredMessage();
        
        try {
            Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage),
                "Expected 'Required' message not shown when username is empty!");
            
            logger.info("Verification passed: Required field message is displayed");
            Allure.step("Verification: Error message contains '" + expectedErrorMessage + "'");
        } catch (AssertionError e) {
            Allure.step("Verification failed: " + e.getMessage(), Status.FAILED);
            throw e;
        }

        logger.info("Test case passed: Login failed with empty username as expected");
        Allure.step("Test case passed: Login failed with empty username as expected");
    }
}
