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
    private final String statusDropdown = "(//label[normalize-space()='Status']/parent::div/following-sibling::div//div[contains(@class,'oxd-select-text')])[1]";
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
    public void fillUsernameOnly(String username) {
        webKeyword.fill(usernameField, username);
    }

    public void searchAdminByUsername(String username) {
        try {
            webKeyword.fill(usernameField, username);
            webKeyword.click(searchButton);

            webKeyword.waitUntilVisible("//div[@class='oxd-table-body']", 10000);

            Thread.sleep(1500);

        } catch (Exception e) {
            throw new RuntimeException("Error while searching user by username: " + username, e);
        }
    }

    public void selectUserRole(String role) {
        try {
            webKeyword.click(userRoleDropdown);
            String optionXpath = String.format("//div[@role='option']//span[normalize-space()='%s']", role);
            webKeyword.click(optionXpath);
        } catch (Exception e) {
            throw new RuntimeException("Failed to select User Role '" + role + "': " + e.getMessage(), e);
        }
    }

    public void selectStatus(String status) {
        try {
            // Open the Status dropdown using the defined locator
            webKeyword.click(statusDropdown);
            // Wait for the listbox to appear
            webKeyword.waitUntilVisible("//div[@role='listbox']", 1000);
            // Click the desired option from the listbox
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

            webKeyword.waitUntilVisible("//div[@class='oxd-table-body']", 10000);

            Thread.sleep(2000);

            String xpath = "//div[@class='oxd-table-body']//div[text()='" + username + "']";
            int count = webKeyword.count(xpath);
            
            return count > 0;
        } catch (Exception e) {
            throw new RuntimeException("Error checking username in table: " + e.getMessage());
        }

    }

    public void enterEmployeeName(String employeeName) {
        String inputXpath = "//input[@placeholder='Type for hints...']";
        

        webKeyword.click(inputXpath);
        

        webKeyword.fill(inputXpath, "");
        

        // Nhập 3-4 ký tự đầu để trigger autocomplete nhanh hơn
        String searchText = employeeName.length() > 3 ? employeeName.substring(0, 3) : employeeName;
        webKeyword.type(inputXpath, searchText);
        

        try {
            Thread.sleep(2000); // Đợi autocomplete load lâu hơn
            webKeyword.waitUntilVisible("//div[@role='listbox']", 10000);
        } catch (Exception e) {
            throw new RuntimeException("No suggestion dropdown appeared for employee name: " + employeeName);
        }

        String[] suggestionXpaths = {
            "//div[@role='listbox']//div[@role='option'][1]",
            "(//div[@role='listbox']//span)[1]",
            "(//div[contains(@class, 'oxd-autocomplete-option')])[1]",
            "(//div[contains(@class, 'oxd-autocomplete-dropdown')]//div)[1]"
        };

        for (String xpath : suggestionXpaths) {
            if (webKeyword.count(xpath) > 0) {
                webKeyword.click(xpath);
                
                // Đợi dropdown đóng
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return;
            }
        }
        
        throw new RuntimeException("No suggestion found for employee name: " + employeeName);
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

    public boolean isRequiredMessageVisible() {
        String xpath = "//span[contains(@class, 'oxd-input-field-error-message') and text()='Required']";
        try {
            webKeyword.waitUntilVisible(xpath, 5000);
            return webKeyword.isVisible(xpath);
        } catch (Exception e) {
            throw new RuntimeException("No 'Required' message detected: " + e.getMessage());
        }
    }

    public boolean isMinLengthMessageVisible() {
        try {
            Thread.sleep(2000); // Đợi lâu hơn để message xuất hiện
            
            // Tìm bất kỳ span nào có class oxd-input-group__message
            String xpath = "//span[contains(@class, 'oxd-input-group__message')]";
            
            if (webKeyword.count(xpath) > 0) {
                // Lấy text của message để debug
                String messageText = webKeyword.getText(xpath);
                System.out.println("Found message: " + messageText);
                
                // Kiểm tra xem có chứa "5" hoặc "character" không
                if (messageText != null && (messageText.contains("5") || messageText.toLowerCase().contains("character"))) {
                    return true;
                }
            }
            
            throw new RuntimeException("No minimum length error message detected");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted: " + e.getMessage());
        }
    }

    public boolean isMaxLengthMessageVisible() {
        try {
            Thread.sleep(2000); // Đợi message xuất hiện
            
            // Tìm message có chứa "40" hoặc "exceed"
            String xpath = "//span[contains(@class, 'oxd-input-group__message')]";
            
            if (webKeyword.count(xpath) > 0) {
                String messageText = webKeyword.getText(xpath);
                System.out.println("Found message: " + messageText);
                
                // Kiểm tra có chứa "40" hoặc "exceed" không
                if (messageText != null && (messageText.contains("40") || messageText.toLowerCase().contains("exceed"))) {
                    return true;
                }
            }
            
            throw new RuntimeException("No maximum length error message detected");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted: " + e.getMessage());
        }
    }

    public boolean isPasswordMismatchMessageVisible() {
        try {
            Thread.sleep(3000); // Đợi lâu hơn để message xuất hiện
            
            // Tìm bất kỳ span nào có class error message
            String xpath = "//span[contains(@class, 'oxd-input-group__message') or contains(@class, 'oxd-input-field-error-message')]";
            
            int count = webKeyword.count(xpath);
            System.out.println("Found " + count + " error messages");
            
            if (count > 0) {
                // Lấy text của tất cả messages để debug
                for (int i = 1; i <= count; i++) {
                    String indexedXpath = "(" + xpath + ")[" + i + "]";
                    try {
                        String messageText = webKeyword.getText(indexedXpath);
                        System.out.println("Message " + i + ": " + messageText);
                        
                        // Kiểm tra có chứa "password" và "match" không (case-insensitive)
                        if (messageText != null && 
                            messageText.toLowerCase().contains("password") && 
                            messageText.toLowerCase().contains("match")) {
                            return true;
                        }
                    } catch (Exception e) {
                        // Skip nếu không lấy được text
                    }
                }
            }
            
            throw new RuntimeException("No 'Passwords do not match' message detected");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted: " + e.getMessage());
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

    // Add admin
    public void clickAddButton() {
        try {
            String addButton = "//button[normalize-space()='Add']";
            webKeyword.click(addButton);
            
            // Đợi form Add User xuất hiện
            try {
                Thread.sleep(1000);
                webKeyword.waitUntilVisible("//h6[normalize-space()='Add User']", 10000);
            } catch (Exception e) {
                // Nếu không tìm thấy heading, thử đợi form xuất hiện
                webKeyword.waitUntilVisible("//form", 5000);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to click Add Admin button: " + e.getMessage(), e);
        }
    }

    public void selectAddUserRole(String role) {
        try {
            String addUserRoleDropdown = "//label[normalize-space()='User Role']/following::div[contains(@class,'oxd-select-text--after')][1]";
            webKeyword.click(addUserRoleDropdown);
            String optionXpath = String.format("//div[@role='option']//span[normalize-space()='%s']", role);
            webKeyword.click(optionXpath);
        } catch (Exception e) {
            throw new RuntimeException("Failed to select Add User Role '" + role + "': " + e.getMessage(), e);
        }
    }

    public void enterAddEmployeeName(String employeeName) {
        try {
            String inputXpath = "//input[@placeholder='Type for hints...']";
            webKeyword.click(inputXpath);
            webKeyword.fill(inputXpath, "");

            // Nhập 3-4 ký tự đầu để trigger autocomplete nhanh hơn
            String searchText = employeeName.length() > 3 ? employeeName.substring(0, 3) : employeeName;
            webKeyword.type(inputXpath, searchText);

            Thread.sleep(2000); // Đợi autocomplete load lâu hơn
            webKeyword.waitUntilVisible("//div[@role='listbox']", 10000);

            String[] suggestionXpaths = {
                    "//div[@role='listbox']//div[@role='option'][1]",
                    "(//div[@role='listbox']//span)[1]",
                    "(//div[contains(@class, 'oxd-autocomplete-option')])[1]",
                    "(//div[contains(@class, 'oxd-autocomplete-dropdown')]//div)[1]"
            };

            for (String xpath : suggestionXpaths) {
                if (webKeyword.count(xpath) > 0) {
                    webKeyword.click(xpath);
                    Thread.sleep(500);
                    return;
                }
            }

            throw new RuntimeException("No suggestion found for employee name: " + employeeName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to enter Add Employee Name '" + employeeName + "': " + e.getMessage(), e);
        }
    }

    public void enterAddUsername(String username) {
        try {
            String usernameInput = "//label[normalize-space()='Username']/parent::div/following-sibling::div//input";
            webKeyword.fill(usernameInput, username);
            
            // Click ra ngoài để trigger validation
            webKeyword.click("//h6[normalize-space()='Add User']");
            Thread.sleep(500); // Đợi validation message xuất hiện
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to enter Add Username '" + username + "': " + e.getMessage(), e);
        }
    }

    public void selectAddStatus(String status) {
        try {
            String statusDropdownAdd = "(//label[normalize-space()='Status']/parent::div/following-sibling::div//div[contains(@class,'oxd-select-text')])[1]";
            webKeyword.click(statusDropdownAdd);
            webKeyword.waitUntilVisible("//div[@role='listbox']", 1000);
            String optionXpath = String.format("//div[@role='listbox']//span[normalize-space()='%s']", status);
            webKeyword.click(optionXpath);
        } catch (Exception e) {
            throw new RuntimeException("Failed to select Add Status '" + status + "': " + e.getMessage(), e);
        }
    }

    public void enterPassword(String password) {
        try {
            String passwordInput = "//label[normalize-space()='Password']/parent::div/following-sibling::div//input";
            webKeyword.fill(passwordInput, password);
        } catch (Exception e) {
            throw new RuntimeException("Failed to enter Password: " + e.getMessage(), e);
        }
    }

    public void enterConfirmPassword(String confirmPassword) {
        try {
            String confirmPasswordInput = "//label[normalize-space()='Confirm Password']/parent::div/following-sibling::div//input";
            webKeyword.fill(confirmPasswordInput, confirmPassword);
            
            // Click ra ngoài để trigger validation
            webKeyword.click("//h6[normalize-space()='Add User']");
            Thread.sleep(1000); // Đợi validation message xuất hiện
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to enter Confirm Password: " + e.getMessage(), e);
        }
    }

    public void clickSaveButton() {
        try {
            String saveButton = "//button[contains(@class,'orangehrm-left-space') and @type='submit']\n";
            webKeyword.click(saveButton);

            Thread.sleep(20000);

            String toastMessage = "//div[contains(@class,'oxd-toast-content')]";
            if (webKeyword.count(toastMessage) > 0) {
                Thread.sleep(1000);
            }
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to click Save button: " + e.getMessage(), e);
        }
    }

    public void waitUntilTableVisible(int timeoutMs) {
        try {
            String tableXpath = "//div[@class='oxd-table-body']";
            webKeyword.waitUntilVisible(tableXpath, timeoutMs);
            // Optional: thêm sleep ngắn để bảng render đầy đủ
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException("Table did not become visible within " + timeoutMs + "ms", e);
        }
    }
    public String getEmployeeNameErrorMessage() {
        String xpath = "//span[contains(@class, 'oxd-input-field-error-message')]";
        try {
            webKeyword.waitUntilVisible(xpath, 5000);
            return webKeyword.getText(xpath);
        } catch (Exception e) {
            throw new RuntimeException("Error getting Employee Name error message: " + e.getMessage(), e);
        }
    }

    public String getRequiredFieldErrorMessage() {
        String xpath = "//span[contains(@class,'oxd-input-field-error-message') and text()='Required']";
        try {
            webKeyword.waitUntilVisible(xpath, 5000);
            return webKeyword.getText(xpath);
        } catch (Exception e) {
            throw new RuntimeException("No 'Required' message detected: " + e.getMessage(), e);
        }
    }



}
