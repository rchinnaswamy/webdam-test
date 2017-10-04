package com.rchinnaswamy.webdam.base;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BaseUtil {

    private static Logger log = LogManager.getLogger(BaseUtil.class);
    protected ConfigReader config = ConfigReader.getConfig();

    protected String getRestAssuredBaseURI() {
        return config.getBaseUrl();
    }

    protected void responseFormatter(Response response) {
        System.out.println();
        System.out.println("Response info:");
        System.out.println("Response status: ");
        response.then().log().status();
        System.out.println("Response body:");
        response.then().log().body();
    }

    protected void headersFormatter(Headers headers) {
        List<Header> headerNames = headers.asList();
        System.out.println();
        System.out.println("Response Headers info:");
        for(Header headerName : headerNames) {
            System.out.println("Header - " + headerName.getName() + " : Value - " + headers.getValue(headerName.getName()));
        }
    }

}
