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
public class OrangeHRM_Login_TC6_VerifyLoginFailsWithEmptyPassword extends BaseLoginTest {
    private static final Logger logger = LogManager.getLogger(OrangeHRM_Login_TC6_VerifyLoginFailsWithEmptyPassword.class);

    @Test
    @TestCaseID("OrangeHRM_TC06")
    @Description("Verify that login fails when password field is left empty")
    @Severity(SeverityLevel.NORMAL)
    public void loginWithEmptyPassword() {
        // Test Data
        final String username = "Admin";
        final String emptyPassword = "";
        final String expectedErrorMessage = "Required";

        // Attach test data to report
        Allure.addAttachment("Test Data", 
            "Username: " + username + "\nPassword: [EMPTY]");

        // Steps
        logger.info("Step 1: Navigate to login page and leave password empty");
        performLogin(username, emptyPassword);

        // Verification
        logger.info("Step 2: Verify required field message is displayed");
        String actualErrorMessage = loginPage.getRequiredMessage();
        
        try {
            Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage),
                "Expected 'Required' message not shown when password is empty!");
            
            logger.info("Verification passed: Required field message is displayed");
            Allure.step("Verification: Error message contains '" + expectedErrorMessage + "'");
        } catch (AssertionError e) {
            Allure.step("Verification failed: " + e.getMessage(), Status.FAILED);
            throw e;
        }

        logger.info("Test case passed: Login failed with empty password as expected");
        Allure.step("Test case passed: Login failed with empty password as expected");
    }
}
