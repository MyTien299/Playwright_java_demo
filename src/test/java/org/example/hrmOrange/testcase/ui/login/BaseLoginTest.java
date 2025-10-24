package org.example.hrmOrange.testcase.ui.login;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.Description;
import org.example.hrmOrange.common.BaseTest;
import org.example.hrmOrange.managers.PageManager;
import org.example.hrmOrange.page.dashboard.DashboardComponent;
import org.example.hrmOrange.page.login.LoginPage;
import org.testng.annotations.BeforeMethod;

@Epic("Authentication")
@Feature("Login Functionality")
public class BaseLoginTest extends BaseTest {
    protected LoginPage loginPage;
    protected DashboardComponent dashboardComponent;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage();
        dashboardComponent = new DashboardComponent(PageManager.getPage());
    }

    protected void performLogin(String username, String password) {
        loginPage.navigateToLogin();
        loginPage.login(username, password);
    }

    protected void verifySuccessfulLogin() {
        loginPage.waitForDashboard();
        dashboardComponent.isAtDashboard();
    }
}
