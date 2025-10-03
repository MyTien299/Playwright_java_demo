package org.example.hrmOrange.page.login;

import com.microsoft.playwright.Page;
import org.example.hrmOrange.constants.AppConfig;
import org.example.hrmOrange.managers.PageManager;

public class LoginPage {
    private final Page page;

    private final String usernameField = "input[name='username']";
    private final String passwordField = "input[name='password']";
    private final String loginButton = "button[type='submit']";
    private final String errorMessage = "div[role='alert']";
    private final String dashboardHeader = "h6:has-text('Dashboard')";
    private final String requiredError = "span.oxd-input-field-error-message";


    public LoginPage() {
        this.page = PageManager.getPage();
    }

    public void navigateToLogin() {
        page.navigate(AppConfig.URL);
    }

    public void login(String username, String password) {
        // Chờ input username load xong
        page.waitForSelector(usernameField);

        page.fill(usernameField, username);
        page.fill(passwordField, password);
        page.click(loginButton);
    }

    public String getErrorMessage() {
        return page.textContent(errorMessage);
    }

    public String getRequiredMessage() {
        return page.textContent(requiredError);
    }


    public boolean isAtDashboard() {
        return page.locator(dashboardHeader).isVisible();
    }
}
