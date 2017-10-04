package com.rchinnaswamy.webdam.test;

import com.rchinnaswamy.webdam.base.AssetUtil;
import com.rchinnaswamy.webdam.base.CommonUtil;
import com.rchinnaswamy.webdam.testdata.AssetData;
import com.rchinnaswamy.webdam.testdata.AssetDataProvider;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Test (groups = "getassettests")
public class GetAssetTests extends BaseTest {

    private static Logger log = LogManager.getLogger(GetAssetTests.class);
    private static Integer rateLimit = null;
    private AssetUtil assetUtil;

    public GetAssetTests() {
        assetUtil = new AssetUtil();
        rateLimit = new Integer(configObj.getApiRateLimit());
    }

    @Test()
    public void testGetAllAssets() {
        Response assetResp = assetUtil.getAllAssets(this.accessToken);
        log.info("Rate Limit Remaining: " + assetResp.then().extract().header("X-RateLimit-Remaining"));
        assetResp.then().
                statusCode(equalTo(200)).
                body("$", hasSize(lessThan(10))).
                body("$", hasSize(5)).
                body("asset_id", containsInAnyOrder("12341", "12342", "12343", "12344", "12345"));
                //body("keywords", hasItems("human"));
    }

    @Test(enabled=false)
    public void testHeadAllAssets() {
        Integer rateLimit = new Integer(60);
        Headers assetHeaders = assetUtil.headAllAssets(this.accessToken);
        log.info("Rate Limit Remaining: " + assetHeaders.getValue("X-RateLimit-Remaining"));
        assertThat(assetHeaders.getValue("X-RateLimit-Limit"), equalTo(rateLimit.toString()));
        assertThat(assetHeaders.getValue("X-RateLimit-Remaining"), equalTo(CommonUtil.decrementRateLimit(rateLimit).toString()));
        assertThat(assetHeaders.getValue("Content-Type"), equalTo("application/json"));
    }

    @Test(dataProvider = "GetAssetData", dataProviderClass = AssetDataProvider.class)
    public void testGetAssetById(AssetData asset) {
        Response assetResp = assetUtil.getAssetById(this.accessToken, asset.getAssetId());
        log.info("Rate Limit Remaining: " + assetResp.then().extract().header("X-RateLimit-Remaining"));
        assetResp.then().
                statusCode(equalTo(200)).
                body("$", hasSize(1)).
                body("[0].asset_id", equalTo(asset.getAssetId())).
                body("[0].thumbnail", equalTo(asset.getThumbnailUrl())).
                body("[0].text", equalTo(asset.getText())).
                body("[0].date_created", equalTo(asset.getDateCreated())).
                body("[0].keywords", hasSize(asset.getKeywords().length));

        JsonPath jp = assetResp.jsonPath();
        List<String> actualKeywords = jp.getList("[0].keywords");
        for(String keyword : actualKeywords) {
            assertThat(keyword, isIn(Arrays.asList(asset.getKeywords())));
            log.info("Found a match for keyword: " + keyword);
        }
    }

    @Test
    public void testGetAssertForNonExistentId() {
        String id = "192319820";
        Response assetResp = assetUtil.getAssetById(this.accessToken, id);
        log.info("Rate Limit Remaining: " + assetResp.then().extract().header("X-RateLimit-Remaining"));
        assetResp.then().
                statusCode(200).
                body("isEmpty()", is(true));
    }

    //TODO: Figure out a better solution to determine remaining rate limit
    @Test(dataProvider = "GetAssetData", dataProviderClass = AssetDataProvider.class, enabled = false)
    public void testHeadAssetById(AssetData asset) {
        //Integer rateLimit = new Integer(60);
        Headers assetHeaders = assetUtil.headAssetById(this.accessToken, asset.getAssetId());
        log.info("Rate Limit Remaining: " + assetHeaders.getValue("X-RateLimit-Remaining"));
        assertThat(assetHeaders.getValue("X-RateLimit-Limit"), equalTo(rateLimit.toString()));
        assertThat(assetHeaders.getValue("X-RateLimit-Remaining"), equalTo(CommonUtil.decrementRateLimit(rateLimit).toString()));
        assertThat(assetHeaders.getValue("Content-Type"), equalTo("application/json"));
    }

}
