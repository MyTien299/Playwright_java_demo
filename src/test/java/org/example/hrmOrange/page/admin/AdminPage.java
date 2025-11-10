package org.example.hrmOrange.page.admin;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.example.hrmOrange.managers.PageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.hrmOrange.keywords.WebKeyword;

public class AdminPage {
    private final WebKeyword webKeyword;

    // --- Locators ---
    private final String adminMenu = "//span[normalize-space()='Admin']";
    private final String usernameField = "//label[normalize-space()='Username']/parent::div/following-sibling::div//input";
    private final String userRoleDropdown = "//label[normalize-space()='User Role']/following::div[contains(@class,'oxd-select-text--after')][1]";
    private final String dropdownOptions = "//div[@role='listbox']//span";
    private final String employeeNameInput = "//label[normalize-space()='Employee Name']/parent::div/following-sibling::div//input";
    private final String statusDropdown = "//label[normalize-space()='Status']/following-sibling::div//div[contains(@class,'oxd-select-text')]";
    private final String searchButton = "//button[normalize-space()='Search']";
    private final String resetButton = "//button[normalize-space()='Reset']";
    private final String tableRows = "//div[contains(@class,'oxd-table-row')]";
    private final String firstRowUsername = "//div[@class='oxd-table-body']/div[1]//div[2]";

    public AdminPage(WebKeyword webKeyword) {
        this.webKeyword = webKeyword;
    }

    // --- Navigation ---
    public void navigateToAdmin() {
        webKeyword.click(adminMenu);
        webKeyword.waitUntilVisible(usernameField, 5000);
    }

    // --- Actions ---
    public void searchAdminByUsername(String username) {
        try {
            webKeyword.fill(usernameField, username);
            webKeyword.click(searchButton);

            // Chờ bảng kết quả hiển thị hoặc "No Records Found"
            webKeyword.waitUntilVisible("//div[@role='table'] | //span[normalize-space()='No Records Found']", 10000);

        } catch (Exception e) {
            throw new RuntimeException("Error while searching user by username: " + username, e);
        }
    }

    public void selectUserRole(String role) {
        try {
            webKeyword.click(userRoleDropdown);
            String optionXpath = "//div[@role='option']//span[normalize-space()='" + role + "']";
            webKeyword.click(optionXpath);

        } catch (Exception e) {
            throw new RuntimeException("Error while searching user by username: " + role, e);
        }
    }

    public void selectStatus(String status) {
        try {
            webKeyword.click("//label[normalize-space()='Status']/ancestor::div[contains(@class,'oxd-input-group')]//div[@class='oxd-select-text-input']");

            String optionXpath = String.format("//div[@role='listbox']//span[normalize-space()='%s']", status);
            webKeyword.click(optionXpath);
        } catch (Exception e) {
            throw new RuntimeException("Failed to select Status '" + status + "': " + e.getMessage(), e);
        }
    }



    public void clickSearch() {
        webKeyword.click(searchButton);
    }

    public void clickReset() {
        webKeyword.click(resetButton);
    }


    public boolean isAdminDisplayedInTable(String username) {
        try {
            String xpath = "//div[normalize-space(text())='" + username + "']";
            webKeyword.waitUntilVisible(xpath, 10000);
            int count = webKeyword.count(xpath);
            return count > 0;
        } catch (Exception e) {
            throw new RuntimeException("Username not found: " + e.getMessage());
        }

    }

    public void enterEmployeeName(String employeeName) {
        String inputXpath = "//input[@placeholder='Type for hints...']";
        webKeyword.fill(inputXpath, employeeName);

        String suggestionXpath = String.format(
                "//div[@role='listbox']//span[contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')]",
                employeeName.toLowerCase()
        );

        if (webKeyword.count(suggestionXpath) > 0) {
            webKeyword.click(suggestionXpath);
        } else {
            throw new RuntimeException("No suggestion found for employee name: " + employeeName);
        }

    }



    public boolean isEmployeeDisplayedInTable(String employeeName) {
        try {
            String xpath = "//div[@role='rowgroup']//div[@role='row']//div[@role='cell' and contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '"
                    + employeeName.toLowerCase() + "')]";
            webKeyword.waitUntilVisible(xpath, 10000);
            return webKeyword.isVisible(xpath);
        } catch (Exception e) {
            throw new RuntimeException("Error verifying employee name: " + e.getMessage());
        }
    }

    public boolean isNoRecordFound() {
        try {
            String xpath = "//span[normalize-space()='No Records Found']";
            webKeyword.waitUntilVisible(xpath, 5000);
            return webKeyword.isVisible(xpath);
        } catch (Exception e) {
            throw new RuntimeException("No 'No Records Found' message detected: " + e.getMessage());
        }

    }

    public boolean isEmployeeInvalidMessageVisible() {
        String xpath = "//span[contains(@class, 'oxd-input-field-error-message') and text()='Invalid']";
        try {
            webKeyword.waitUntilVisible(xpath, 5000);
            return webKeyword.isVisible(xpath);
        } catch (Exception e) {
            throw new RuntimeException("No 'Invalid' message detected: " + e.getMessage());
        }
    }

    public boolean areAllFiltersCleared() {
        String usernameValue = webKeyword.getInputValue("//label[normalize-space()='Username']/parent::div/following-sibling::div//input");
        String employeeValue = webKeyword.getInputValue("//input[contains(@placeholder,'Type for hints...')]");
        String userRoleText = webKeyword.getText("//label[normalize-space()='User Role']/parent::div/following-sibling::div//div[contains(@class,'oxd-select-text--after')]/preceding-sibling::div").trim();
        String statusText = webKeyword.getText("//label[normalize-space()='Status']/parent::div/following-sibling::div//div[contains(@class,'oxd-select-text--after')]/preceding-sibling::div").trim();

        boolean allCleared = usernameValue.isEmpty()
                && employeeValue.isEmpty()
                && userRoleText.equals("-- Select --")
                && statusText.equals("-- Select --");

        return allCleared;
    }

    public boolean isResultTableVisible() {
        return webKeyword.isVisible("//div[contains(@class,'orangehrm-container')]");
    }

    public boolean verifyEmployeeNameInResults(String expectedEmployeeName) {
        try {
            String shortName = expectedEmployeeName.split(" ")[0];
            String xpath = String.format(
                    "//div[@role='rowgroup']//div[@role='row']//div[@role='cell' and contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')]",
                    shortName.toLowerCase()
            );
            webKeyword.waitUntilVisible(xpath, 10000);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error verifying employee name: " + e.getMessage());
        }
    }

}
