package com.rchinnaswamy.webdam.test;

import com.rchinnaswamy.webdam.base.CommonUtil;
import com.rchinnaswamy.webdam.base.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    private static Logger log = LogManager.getLogger(BaseTest.class);
    protected String accessToken;
    protected ConfigReader configObj;
    protected CommonUtil commonUtil;

    public BaseTest() {
        configObj = ConfigReader.getConfig();
        commonUtil = new CommonUtil();
    }

    @BeforeMethod(alwaysRun = true)
    public void getAccessToken() {
        log.info("Getting Access Token for test");
        accessToken = commonUtil.getAccessToken();
    }

}
