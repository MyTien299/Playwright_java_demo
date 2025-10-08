package org.example.hrmOrange.page.dashboard;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class DashboardComponent {
    private Page page;
    private Locator dashboardTitle;
    private Locator adminUsername;

    // Constructor mặc định nếu cần khởi tạo thủ công
    public DashboardComponent() {}

    // Constructor chính dùng PageManager
    public DashboardComponent(Page page) {
        this.page = page;
        this.dashboardTitle = page.locator("//h6[normalize-space()='Dashboard']");
    }

    public void setPage(Page page) {
        this.page = page;
        this.dashboardTitle = page.locator("//h6[normalize-space()='Dashboard']");
    }

    public boolean isAtDashboard() {
        try {
            if (dashboardTitle == null) {
                dashboardTitle = page.locator("//h6[normalize-space()='Dashboard']");
            }
            dashboardTitle.waitFor(new Locator.WaitForOptions().setTimeout(8000));
            return dashboardTitle.isVisible();
        } catch (Exception e) {
            System.out.println("Dashboard not visible: " + e.getMessage());
            return false;
        }
    }

    public String getDashboardTitle() {
        if (dashboardTitle == null) return "";
        return dashboardTitle.textContent();
    }

    public boolean isAdminDisplayed() {
        try {
            if (adminUsername == null) {
                adminUsername = page.locator("//div[normalize-space(text())='Admin']");
            }
            adminUsername.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            return adminUsername.count() > 0;
        } catch (Exception e) {
            System.out.println("Admin not visible: " + e.getMessage());
            return false;
        }
    }
}
