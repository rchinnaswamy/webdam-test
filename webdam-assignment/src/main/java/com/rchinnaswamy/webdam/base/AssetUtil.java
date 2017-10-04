package com.rchinnaswamy.webdam.base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Headers;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by rchinnaswamy on 10/1/17.
 */
public class AssetUtil extends BaseUtil {
    private static final String ASSET_PATH = "/api/v1/asset";
    private static final String SEARCH_PATH = "/api/v1/search";

    public Response getAllAssets(String accessToken) {
        RestAssured.baseURI = getRestAssuredBaseURI();
        Response allAssetsResp = given().log().uri().log().method().log().headers().log().body().
                header("Authorization", "Bearer " + accessToken).
                when().get(ASSET_PATH).
                then().statusCode(equalTo(200)).
                extract().response();
        responseFormatter(allAssetsResp);
        return allAssetsResp;
    }

    public Headers headAllAssets(String accessToken) {
        RestAssured.baseURI = getRestAssuredBaseURI();
        Headers allAssetsHeaders = given().log().uri().log().method().log().headers().log().body().
                header("Accept", "application/json").
                header("Authorization", "Bearer " + accessToken).
                when().head(ASSET_PATH).
                then().extract().headers();
        headersFormatter(allAssetsHeaders);
        return allAssetsHeaders;
    }

    public Response getAssetById(String accessToken, String assetId) {
        RestAssured.baseURI = getRestAssuredBaseURI();
        Response assetResp = given().log().uri().log().method().log().headers().log().body().log().parameters().
                header("Accept", "application/json").
                header("Authorization", "Bearer " + accessToken).
                pathParam("id", assetId).
                when().get(ASSET_PATH + "/{id}").
                then().extract().response();
        responseFormatter(assetResp);
        return assetResp;
    }

    public Headers headAssetById(String accessToken, String assetId) {
        RestAssured.baseURI = getRestAssuredBaseURI();
        Headers assetByIdHeaders = given().log().uri().log().method().log().headers().log().body().log().parameters().
                header("Accept", "application/json").
                header("Authorization", "Bearer " + accessToken).
                pathParam("id", assetId).
                when().head(ASSET_PATH + "/{id}").
                then().extract().headers();
        headersFormatter(assetByIdHeaders);
        return assetByIdHeaders;
    }

    public Response searchAssets(String accessToken, String searchStr, String sortOrder, int maxResults) {
        RestAssured.baseURI = getRestAssuredBaseURI();
        RequestSpecBuilder reqBuild = new RequestSpecBuilder().
                addHeader("Accept", "application/json").
                addHeader("Authorization", "Bearer " + accessToken).
                addQueryParam("query", searchStr).
                addQueryParam("limit", maxResults);
        if(sortOrder != null && !sortOrder.isEmpty())
            reqBuild.addQueryParam("sort", sortOrder);

        RequestSpecification reqSpec = reqBuild.build();
        Response searchResponse = given().log().uri().log().method().log().headers().log().body().log().parameters().
                spec(reqSpec).
                when().get(SEARCH_PATH).
                then().extract().response();
        responseFormatter(searchResponse);
        return searchResponse;
    }

    public Response searchAssets(String accessToken) {
        RestAssured.baseURI = getRestAssuredBaseURI();
        Response searchResponse = given().log().uri().log().method().log().headers().log().body().log().parameters().
                when().get(SEARCH_PATH).
                then().extract().response();
        responseFormatter(searchResponse);
        return searchResponse;
    }






}
