package org.example.hrmOrange.testcase.ui.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.Description;
import org.example.hrmOrange.common.BaseTest;
import org.example.hrmOrange.page.dashboard.DashboardComponent;
import org.example.hrmOrange.page.login.LoginPage;
import org.example.hrmOrange.steps.login.LoginSteps;
import org.example.hrmOrange.page.admin.AdminPage;
import org.example.hrmOrange.steps.admin.AdminSteps;
import org.testng.annotations.BeforeMethod;

@Epic("Administration")
@Feature("Admin Module")
public class BaseAdminTest extends BaseTest {

    protected DashboardComponent dashboardComponent;
    protected LoginSteps loginSteps;
    protected LoginPage loginPage;
    protected AdminPage adminPage;
    protected AdminSteps adminSteps;

    @BeforeMethod
    @Override
    public void setUp() {
        super.setUp();
        dashboardComponent = new DashboardComponent(webKeyword.getPage());
        loginSteps = new LoginSteps(webKeyword);
        loginPage = new LoginPage(webKeyword);
        adminPage = new AdminPage(webKeyword);
        adminSteps = new AdminSteps(webKeyword);
    }
}
