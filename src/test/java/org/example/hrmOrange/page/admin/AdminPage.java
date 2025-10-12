package org.example.hrmOrange.page.admin;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.example.hrmOrange.managers.PageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdminPage {
    private final Page page;
    private static final Logger logger = LogManager.getLogger(AdminPage.class);

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

    public AdminPage() {
        this.page = PageManager.getPage();
    }

    // --- Navigation ---
    public void navigateToAdmin() {
        page.click(adminMenu);
        page.waitForSelector(usernameField);
        logger.info("Navigated to Admin page");
    }

    // --- Actions ---
    public void searchAdminByUsername(String username) {
        page.fill(usernameField, username);
        page.click(searchButton);

        try {
            page.locator("//button[normalize-space()='Search']").click();
            logger.info("Clicked Search button");

            // Chờ bảng kết quả hiển thị hoặc "No Records Found"
            page.waitForSelector("//div[@role='table'] | //span[normalize-space()='No Records Found']",
                    new Page.WaitForSelectorOptions().setTimeout(10000));

        } catch (Exception e) {
            logger.error("Error while searching user by username: " + username, e);
        }
    }

    public void selectUserRole(String role) {
        try {
            Locator dropdown = page.locator(userRoleDropdown);
            dropdown.waitFor(new Locator.WaitForOptions().setTimeout(8000));
            dropdown.click();

            Locator option = page.locator("//div[@role='option']//span[normalize-space()='" + role + "']");
            option.waitFor(new Locator.WaitForOptions().setTimeout(5000));
            option.click();

            logger.info("Selected User Role = " + role);
        } catch (Exception e) {
            logger.error("Failed to select User Role '" + role + "': " + e.getMessage());
            throw e;
        }
    }

    public void selectStatus(String status) {
        try {
            Locator statusDropdown = page.locator("//label[normalize-space()='Status']/ancestor::div[contains(@class,'oxd-input-group')]//div[@class='oxd-select-text-input']");

            statusDropdown.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            statusDropdown.click();

            Locator option = page.locator(String.format("//div[@role='listbox']//span[normalize-space()='%s']", status));
            option.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            option.click();

            logger.info("Selected Status: " + status);
        } catch (Exception e) {
            logger.error("Failed to select Status '" + status + "': " + e.getMessage(), e);
            throw e;
        }
    }



    public void clickSearch() {
        page.click(searchButton);
        logger.info("Clicked Search button");
    }

    public void clickReset() {
        page.click(resetButton);
        logger.info("Clicked Reset button");
    }


    public boolean isAdminDisplayedInTable(String username) {
        Locator usernameCell = page.locator("//div[normalize-space(text())='" + username + "']");
        try {
            usernameCell.first().waitFor(new Locator.WaitForOptions().setTimeout(10000));
            int count = usernameCell.count();
            logger.info("Found " + count + " record(s) with username = " + username);
            return count > 0;
        } catch (Exception e) {
            logger.warn("Username not found: " + e.getMessage());
            return false;
        }
    }

    public void enterEmployeeName(String employeeName) {
        Locator input = page.locator("//input[@placeholder='Type for hints...']");
        input.fill(employeeName);

        Locator suggestion = page.locator(String.format(
                "//div[@role='listbox']//span[contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')]",
                employeeName.toLowerCase()
        ));

        if (suggestion.count() > 0) {
            suggestion.first().click();
            logger.info("Selected employee suggestion: " + employeeName);
        } else {
            logger.warn("No suggestion found for employee name: " + employeeName);
        }
    }



    public boolean isEmployeeDisplayedInTable(String employeeName) {
        try {
            String xpath = "//div[@role='rowgroup']//div[@role='row']//div[@role='cell' and contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '"
                    + employeeName.toLowerCase() + "')]";
            Locator cell = page.locator(xpath).first();
            cell.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            logger.info("Found employee name matching: " + employeeName);
            return cell.isVisible();
        } catch (Exception e) {
            logger.error("Error verifying employee name: " + e.getMessage());
            return false;
        }
    }

    public boolean isNoRecordFound() {
        try {
            Locator noRecordText = page.locator("//span[normalize-space()='No Records Found']");
            noRecordText.waitFor(new Locator.WaitForOptions().setTimeout(5000));
            return noRecordText.isVisible();
        } catch (Exception e) {
            logger.error("No 'No Records Found' message detected.", e);
            return false;
        }
    }

    public boolean isEmployeeInvalidMessageVisible() {
        Locator invalidMsg = page.locator("//span[contains(@class, 'oxd-input-field-error-message') and text()='Invalid']");
        try {
            invalidMsg.waitFor(new Locator.WaitForOptions().setTimeout(5000));
            return invalidMsg.isVisible();
        } catch (Exception e) {
            logger.error("No 'Invalid' message detected.");
            return false;
        }
    }

    public boolean areAllFiltersCleared() {
        String usernameValue = page.inputValue("//label[normalize-space()='Username']/parent::div/following-sibling::div//input");
        String employeeValue = page.inputValue("//input[contains(@placeholder,'Type for hints...')]");
        String userRoleText = page.locator("//label[normalize-space()='User Role']/parent::div/following-sibling::div//div[contains(@class,'oxd-select-text--after')]/preceding-sibling::div").innerText().trim();
        String statusText = page.locator("//label[normalize-space()='Status']/parent::div/following-sibling::div//div[contains(@class,'oxd-select-text--after')]/preceding-sibling::div").innerText().trim();

        boolean allCleared = usernameValue.isEmpty()
                && employeeValue.isEmpty()
                && userRoleText.equals("-- Select --")
                && statusText.equals("-- Select --");

        logger.info("Filters cleared check -> Username: '{}', Employee: '{}', Role: '{}', Status: '{}'",
                usernameValue, employeeValue, userRoleText, statusText);

        return allCleared;
    }

    public boolean isResultTableVisible() {
        return page.isVisible("//div[contains(@class,'orangehrm-container')]");
    }

    public boolean verifyEmployeeNameInResults(String expectedEmployeeName) {
        try {
            String shortName = expectedEmployeeName.split(" ")[0]; // lấy từ đầu, vd: "manda"
            String locator = String.format(
                    "//div[@role='rowgroup']//div[@role='row']//div[@role='cell' and contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')]",
                    shortName.toLowerCase()
            );
            page.locator(locator).first().waitFor(new Locator.WaitForOptions().setTimeout(10000));
            logger.info("Employee name found in result table: " + shortName);
            return true;
        } catch (Exception e) {
            logger.error("Error verifying employee name: " + e.getMessage());
            return false;
        }
    }

}
