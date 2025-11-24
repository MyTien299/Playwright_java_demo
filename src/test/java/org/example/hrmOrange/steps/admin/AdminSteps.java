package org.example.hrmOrange.steps.admin;

import io.qameta.allure.Step;
import org.example.hrmOrange.keywords.WebKeyword;
import org.example.hrmOrange.page.admin.AdminPage;

public class AdminSteps {

    private final AdminPage adminPage;

    public AdminSteps(WebKeyword webKeyword) {
        this.adminPage = new AdminPage(webKeyword);
    }

    @Step("Navigate to Admin page")
    public void navigateToAdminPage() {
        adminPage.navigateToAdmin();
    }

    @Step("Fill Admin filters: username={username}, role={userRole}, employee={employeeName}, status={status}")
    public void fillAdminFilters(String username, String userRole, String employeeName, String status) {
        adminPage.searchAdminByUsername(username);
        adminPage.selectUserRole(userRole);
        adminPage.enterEmployeeName(employeeName);
        adminPage.selectStatus(status);
    }

    @Step("Fill username: {username}")
    public void fillUsername(String username) {
        adminPage.fillUsernameOnly(username);
    }

    @Step("Select user role: {userRole}")
    public void selectUserRole(String userRole) {
        adminPage.selectUserRole(userRole);
    }

    @Step("Enter employee name: {employeeName}")
    public void enterEmployeeName(String employeeName) {
        adminPage.enterEmployeeName(employeeName);
    }

    @Step("Select status: {status}")
    public void selectStatus(String status) {
        adminPage.selectStatus(status);
    }

    @Step("Click Search button")
    public void clickSearch() {
        adminPage.clickSearch();
    }

    @Step("Click Reset on Admin filters")
    public void clickReset() {
        adminPage.clickReset();
    }

    @Step("Verify all Admin filters are cleared")
    public void verifyFiltersCleared() {
        if (!adminPage.areAllFiltersCleared()) {
            throw new AssertionError("Filters were not cleared after clicking Reset!");
        }
    }

    // Add admin
    @Step("Click Add Admin button")
    public void clickAddButton() {
        adminPage.clickAddButton();
    }

    @Step("Select Add User Role: {userRole}")
    public void selectAddUserRole(String userRole) {
        adminPage.selectAddUserRole(userRole);
    }

    @Step("Enter Add Employee Name: {employeeName}")
    public void enterAddEmployeeName(String employeeName) {
        adminPage.enterAddEmployeeName(employeeName);
    }

    @Step("Enter Add Username: {username}")
    public void enterAddUsername(String username) {
        adminPage.enterAddUsername(username);
    }

    @Step("Select Add Status: {status}")
    public void selectAddStatus(String status) {
        adminPage.selectAddStatus(status);
    }

    @Step("Enter Password: ******")
    public void enterPassword(String password) {
        adminPage.enterPassword(password);
    }

    @Step("Enter Confirm Password: ******")
    public void enterConfirmPassword(String confirmPassword) {
        adminPage.enterConfirmPassword(confirmPassword);
    }

    @Step("Click Save when adding new Admin")
    public void clickSave() {
        adminPage.clickSaveButton();
    }

}
