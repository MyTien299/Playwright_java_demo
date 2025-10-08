package org.example.hrmOrange.page.admin;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.example.hrmOrange.managers.PageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdminPage {
    private final Page page;
    private static final Logger logger = LogManager.getLogger(AdminPage.class);
    // Locators
    private final String adminMenu = "//span[normalize-space()='Admin']";
    private final String usernameField = "//label[normalize-space()='Username']/parent::div/following-sibling::div//input";
    private final String searchButton = "//button[normalize-space()='Search']";
    private final String tableRows = "//div[contains(@class,'oxd-table-row')]";

    public AdminPage() {
        this.page = PageManager.getPage();
    }

    public void navigateToAdmin() {
        page.click(adminMenu);
        page.waitForSelector(usernameField);
    }

    public void searchAdminByUsername(String username) {
        page.fill(usernameField, username);
        page.click(searchButton);

        page.waitForSelector("//div[normalize-space(text())='" + username + "']");
    }

    public boolean isAdminDisplayedInTable(String username) {
        Locator usernameCell = page.locator("//div[normalize-space(text())='" + username + "']");
        try {
            usernameCell.first().waitFor(new Locator.WaitForOptions().setTimeout(10000));
            int count = usernameCell.count();
            System.out.println("Found " + count + " record(s) with username = " + username);
            return count > 0;
        } catch (Exception e) {
            System.out.println("Username not found: " + e.getMessage());
            return false;
        }
    }
}
