package org.example.hrmOrange.testcase.ui.login;

import io.qameta.allure.*;
import io.qameta.allure.model.Status;
import org.example.hrmOrange.annotation.TestCaseID;
import org.testng.Assert;
import org.testng.annotations.Test;


 @Story("Login with password formatting")
 @Severity(SeverityLevel.MINOR)
 public class OrangeHRM_Login_TC10_VerifyLoginFailsWithPasswordTrailingSpace extends BaseLoginTest {

     @Test
     @TestCaseID("OrangeHRM_TC10")
     @Description("Verify that login fails when password ends with whitespace")
     @Severity(SeverityLevel.MINOR)
     public void loginWithPasswordTrailingSpace() {
         final String username = "Admin";
         final String passwordWithTrailingSpace = "admin123 ";
         final String expectedErrorMessage = "Invalid credentials";

         Allure.addAttachment("Test Data",
                 "Username: " + username + "\nPassword: '" + passwordWithTrailingSpace + "' (with trailing space)");

         step_Navigate_To_Login_Page();
         step_Login(username, passwordWithTrailingSpace);
         step_Verify_Error_Message_Displayed(expectedErrorMessage);
     }

     @Step("Step 1 – Navigate to Login Page")
     private void step_Navigate_To_Login_Page() {
         loginSteps.navigateToLoginPage();
     }

     @Step("Step 2 – Login with username: {username}, password (trailing space): {password}")
     private void step_Login(String username, String password) {
         loginSteps.login(username, password);
     }

     @Step("Step 3 – Verify error message: {expectedErrorMessage}")
     private void step_Verify_Error_Message_Displayed(String expectedErrorMessage) {
         String actualErrorMessage = loginPage.getErrorMessage();

         if (!actualErrorMessage.contains(expectedErrorMessage)) {
             Allure.step("Error message mismatch! Actual: " + actualErrorMessage, Status.FAILED);
             Assert.fail("Expected: " + expectedErrorMessage + " but found: " + actualErrorMessage);
         }

         Allure.step("Error message is correct: " + actualErrorMessage);
     }
 }
