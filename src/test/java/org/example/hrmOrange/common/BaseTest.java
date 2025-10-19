package org.example.hrmOrange.common;

import org.example.hrmOrange.allure.AllureReportUtils;
import org.example.hrmOrange.constants.AppConfig;
import org.example.hrmOrange.helpers.ConfigReader;
import org.example.hrmOrange.managers.BrowserFactory;
import org.example.hrmOrange.managers.PageManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
    @BeforeMethod
    public void setUp() {
        BrowserFactory browserFactory = new BrowserFactory();
        browserFactory.createBrowser(AppConfig.BROWSER);
        
        // Set browser version for Allure report
        String browserVersion = AppConfig.BROWSER + " (Playwright)";
        ConfigReader.BROWSER_VERSION = browserVersion;
        
        PageManager.getPage().navigate(AppConfig.URL);
    }

    @AfterMethod
    public void tearDown() {
        closeBrowserResources();
    }

    private static void closeBrowserResources() {
        if(PageManager.getBrowserContext() != null){
            PageManager.getBrowserContext().close();
        }

        if(PageManager.getBrowser() != null){
            PageManager.getBrowser().close();
        }

        if(PageManager.getPlaywright() != null){
            PageManager.getPlaywright().close();
        }
    }

    private static String suiteName;

    public static String getSuiteName() {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String packageName = className.substring(0, className.lastIndexOf("."));
        String folderName = packageName.substring(packageName.lastIndexOf(".") + 1);
        System.out.println(folderName);
        return folderName;
    }

    @BeforeSuite
    public static void beforeSuite() {
        AllureReportUtils.deleteAllureResult();
    }

    @AfterSuite
    public static void afterSuite() {
        AllureReportUtils.generateAllureReport(getSuiteName());
    }
}