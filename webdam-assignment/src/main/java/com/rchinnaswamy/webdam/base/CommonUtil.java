package com.rchinnaswamy.webdam.base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CommonUtil extends BaseUtil {
    private static final String ACCESS_TOKEN_PATH = "/oauth/token";
    private static final String LOGIN_PATH = "/api/v1/login";
    private static final String LOGOUT_PATH = "/api/v1/logout";
    private static Logger log = LogManager.getLogger(CommonUtil.class);
    private String accessToken = null;

    public static Integer decrementRateLimit(Integer rateLimit) {
        int newratelimit = rateLimit--;
        return new Integer(newratelimit);
        //return rateLimit;
    }

    public String getAccessToken() {

        if(this.accessToken != null) {
            log.info("Checking if existing access token is valid...");
            Response loginResp = testLogin(accessToken);
            if(validateLoginResponse(loginResp)) {
                log.info("Existing access token is valid");
                return accessToken;
            }
        }

        log.info("Generating new access token...");
        RestAssured.baseURI = getRestAssuredBaseURI();
        JSONObject authParams = new JSONObject();
        authParams.put("grant_type", "client_credentials");
        authParams.put("client_id", config.getOAuth2ClientId());
        authParams.put("client_secret", config.getOAuth2ClientSecret());

        Response authResp = given().log().uri().log().method().log().headers().log().body().log().parameters().
                formParams(authParams).
                when().post(ACCESS_TOKEN_PATH).
                then().
                statusCode(200).
                body("token_type", equalTo("Bearer")).
                body("access_token", notNullValue()).
                extract().response();
        responseFormatter(authResp);
        accessToken = authResp.body().path("access_token");
        return accessToken;
    }

    public Response testLogin(String accessToken) {
        RestAssured.baseURI = getRestAssuredBaseURI();

        Response loginResponse = given().log().uri().log().method().log().headers().log().body().
                header("Authorization", "Bearer " + accessToken).
                when().get(LOGIN_PATH).then().extract().response();
        responseFormatter(loginResponse);
        return loginResponse;
    }

    public boolean validateLoginResponse(Response resp) {
        if(resp.statusCode() == 202 && resp.body().path("logged_in").equals(true))
            return true;
        else
            return false;
    }




}
