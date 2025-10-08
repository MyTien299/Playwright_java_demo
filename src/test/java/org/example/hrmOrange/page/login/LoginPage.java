package org.example.hrmOrange.page.login;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.hrmOrange.constants.AppConfig;
import org.example.hrmOrange.managers.PageManager;



public class LoginPage {
    private final Page page;
    private static final Logger logger = LogManager.getLogger(LoginPage.class);
    private final String usernameField = "//input[@name='username']";
    private final String passwordField = "//input[@name='password']";
    private final String loginButton = "//button[@type='submit']";
    private final String errorMessage = "//div[@role='alert']";
    private final String dashboardHeader = "//h6[normalize-space()='Dashboard']";
    private final String requiredError = "//span[contains(@class,'oxd-input-field-error-message')]";

    public LoginPage() {
        this.page = PageManager.getPage();
    }

    public void navigateToLogin() {
        page.navigate(AppConfig.URL);
    }

    /**
     * Chỉ login, KHÔNG wait Dashboard
     */
    public void login(String username, String password) {
        page.waitForSelector(usernameField);
        page.fill(usernameField, username);
        page.fill(passwordField, password);
        page.click(loginButton);
    }

    /**
     * Wait dashboard xuất hiện, chỉ dùng cho login thành công
     */
    public void waitForDashboard() {
        page.locator(dashboardHeader)
                .waitFor(new Locator.WaitForOptions().setTimeout(20000));
    }

    public String getErrorMessage() {
        // wait lỗi xuất hiện trước khi lấy
        page.locator(errorMessage).waitFor();
        return page.textContent(errorMessage).trim();
    }

    public String getRequiredMessage() {
        return page.textContent(requiredError).trim();
    }

    public String getUsernameRequiredMessage() {
        return page.locator("//input[@name='username']/ancestor::div[contains(@class,'oxd-input-group')]//span")
                .textContent().trim();
    }

    public String getPasswordRequiredMessage() {
        return page.locator("//input[@name='password']/ancestor::div[contains(@class,'oxd-input-group')]//span")
                .textContent().trim();
    }

    public boolean isAtDashboard() {
        return page.locator(dashboardHeader).isVisible();
    }
}
