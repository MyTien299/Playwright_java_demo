package org.example.hrmOrange.page.admin;

import com.microsoft.playwright.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.hrmOrange.managers.PageManager;

public class SearchPage {
    private final Page page;
    private static final Logger logger = LogManager.getLogger(SearchPage.class);

    private final String userRoleDropdown = "//label[text()='User Role']/following-sibling::div//i";
    private final String statusDropdown = "//label[text()='Status']/following-sibling::div//i";
    private final String employeeNameInput = "//label[text()='Employee Name']/following-sibling::div//input";
    private final String usernameInput = "//label[text()='Username']/following-sibling::div//input";
    private final String searchButton = "//button[@type='submit']";
    private final String resetButton = "//button[normalize-space()='Reset']";
    private final String firstRowUsername = "//div[@class='oxd-table-body']/div[1]//div[2]";

    public SearchPage() {
        this.page = PageManager.getPage();
    }

    public void selectUserRole(String role) {
        page.click(userRoleDropdown);
        page.click("//div[@role='option']//span[text()='" + role + "']");
        logger.info("Selected User Role: " + role);
    }

    public void selectStatus(String status) {
        page.click(statusDropdown);
        page.click("//div[@role='option']//span[text()='" + status + "']");
        logger.info("Selected Status: " + status);
    }

    public void setEmployeeName(String employeeName) {
        page.fill(employeeNameInput, employeeName);
        logger.info("Entered Employee Name: " + employeeName);
    }

    public void setUsername(String username) {
        page.fill(usernameInput, username);
        logger.info("Entered Username: " + username);
    }

    public void clickSearch() {
        page.click(searchButton);
        logger.info("Clicked Search button");
    }

    public void clickReset() {
        page.click(resetButton);
        logger.info("Clicked Reset button");
    }

    public String getFirstRowUsername() {
        return page.locator(firstRowUsername).textContent().trim();
    }
}
