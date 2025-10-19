package org.example.hrmOrange.testcase.ui.login;

import io.qameta.allure.*;
import io.qameta.allure.model.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.Assert;
import org.testng.annotations.Test;
@Story("Login with valid credentials")
@Severity(SeverityLevel.CRITICAL)
public class OrangeHRM_Login_TC1_VerifyThatUserCanLoginWithValidDataSuccessfully extends BaseLoginTest {
    private static final Logger logger = LogManager.getLogger(OrangeHRM_Login_TC1_VerifyThatUserCanLoginWithValidDataSuccessfully.class);

    @Test
    @TestCaseID("OrangeHRM_TC01")
    @Description("Verify that user can successfully log in with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    public void loginWithValidUser() {
        // Test Data
        final String username = "Admin";
        final String password = "admin123";
        final String expectedTitle = "Dashboard";

        // Attach test data to report
        Allure.addAttachment("Test Data", 
            "Username: " + username + "\nPassword: " + password);

        // Steps
        logger.info("Step 1: Navigate to login page and enter credentials");
        performLogin(username, password);

        logger.info("Step 2: Verify successful login");
        verifySuccessfulLogin();

        // Verification
        logger.info("Verification: Check dashboard title");
        String actualTitle = dashboardComponent.getDashboardTitle().trim();
        
        // Soft assertion for better reporting
        try {
            Assert.assertEquals(actualTitle, expectedTitle, "Dashboard title mismatch!");
            logger.info("Verification passed: Dashboard title is as expected");
            Allure.step("Verification: Dashboard title is '" + actualTitle + "' as expected");
        } catch (AssertionError e) {
            Allure.step("Verification failed: " + e.getMessage(), Status.FAILED);
            throw e;
        }

        logger.info("Test case passed: User successfully logged in and dashboard verified");
        Allure.step("Test case passed: User successfully logged in and dashboard verified");
    }
}
