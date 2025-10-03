package org.example.hrmOrange.common;

import org.example.hrmOrange.constants.AppConfig;
import org.example.hrmOrange.managers.BrowserFactory;
import org.example.hrmOrange.managers.PageManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    @BeforeMethod
    public void setUp() {
        //Khởi tạo browser
        BrowserFactory browserFactory = new BrowserFactory();
        browserFactory.createBrowser(AppConfig.BROWSER);

        //Mở URL web
        PageManager.getPage().navigate(AppConfig.URL);
    }

    @AfterMethod
    public void tearDown() {
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
}
