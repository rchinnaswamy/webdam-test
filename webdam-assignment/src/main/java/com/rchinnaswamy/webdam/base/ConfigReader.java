package com.rchinnaswamy.webdam.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final String CONFIG_FILE = "config.properties";
    private static ConfigReader instance = null;
    private String base_url = null;
    private String oauth2_client_id = null;
    private String oauth2_client_secret = null;
    private String api_rate_limit = null;

    private ConfigReader() {
        InputStream input = this.getClass().getClassLoader().getResourceAsStream(CONFIG_FILE);
        Properties prop = new Properties();

        try {
            prop.load(input);
            base_url = prop.getProperty("base_url");
            oauth2_client_id = prop.getProperty("oauth2_client_id");
            oauth2_client_secret = prop.getProperty("oauth2_client_secret");
            api_rate_limit = prop.getProperty("api_rate_limit");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConfigReader getConfig() {
        if(instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    public String getBaseUrl() {
        return base_url;
    }

    public String getOAuth2ClientId() {
        return oauth2_client_id;
    }

    public String getOAuth2ClientSecret() {
        return oauth2_client_secret;
    }

    public String getApiRateLimit() { return api_rate_limit; }
}
