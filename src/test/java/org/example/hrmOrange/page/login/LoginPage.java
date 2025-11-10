package org.example.hrmOrange.page.login;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.hrmOrange.constants.AppConfig;
import org.example.hrmOrange.managers.PageManager;
import org.example.hrmOrange.keywords.WebKeyword;


public class LoginPage {
    private final WebKeyword webKeyword;

    private final String usernameField = "//input[@name='username']";
    private final String passwordField = "//input[@name='password']";
    private final String loginButton = "//button[@type='submit']";
    private final String errorMessage = "//div[@role='alert']";
    private final String dashboardHeader = "//h6[normalize-space()='Dashboard']";
    private final String requiredError = "//span[contains(@class,'oxd-input-field-error-message')]";


    public LoginPage(WebKeyword webKeyword) {
        this.webKeyword = webKeyword;
    }

    public void navigateToLogin() {
        webKeyword.navigateToUrl(AppConfig.URL);
    }


    public void login(String username, String password) {
        webKeyword.waitUntilVisible(usernameField, 5000);
        webKeyword.fill(usernameField, username);
        webKeyword.fill(passwordField, password);
        webKeyword.click(loginButton);
    }


    public void waitForDashboard() {
        webKeyword.waitUntilVisible(dashboardHeader, 20000);
    }

    public String getErrorMessage() {
        webKeyword.waitUntilVisible(errorMessage, 5000);
        return webKeyword.getText(errorMessage);
    }

    public String getRequiredMessage() {
        webKeyword.waitUntilVisible(requiredError, 5000);
        return webKeyword.getText(requiredError);
    }

    public String getUsernameRequiredMessage() {
        return webKeyword.getText("//input[@name='username']/ancestor::div[contains(@class,'oxd-input-group')]//span");
    }

    public String getPasswordRequiredMessage() {
        return webKeyword.getText("//input[@name='password']/ancestor::div[contains(@class,'oxd-input-group')]//span");
    }

    public boolean isAtDashboard() {

        return webKeyword.isVisible(dashboardHeader);
    }
}
