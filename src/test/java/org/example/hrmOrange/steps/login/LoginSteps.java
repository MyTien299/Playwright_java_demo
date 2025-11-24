package org.example.hrmOrange.steps.login;

import io.qameta.allure.Step;
import org.example.hrmOrange.keywords.WebKeyword;
import org.example.hrmOrange.page.login.LoginPage;

public class LoginSteps {

    private final LoginPage loginPage;

    public LoginSteps(WebKeyword webKeyword) {
        this.loginPage = new LoginPage(webKeyword);
    }

    @Step("Navigate to login page")
    public void navigateToLoginPage() {
        loginPage.navigateToLogin();
    }

    @Step("Login with username: {username} and password: {password}")
    public void login(String username, String password) {
        loginPage.login(username, password);
    }

    @Step("Verify user is at dashboard page")
    public void verifyDashboard() {
        loginPage.waitForDashboard();
        if (!loginPage.isAtDashboard()) {
            throw new AssertionError("Dashboard is NOT displayed after login!");
        }
    }
}