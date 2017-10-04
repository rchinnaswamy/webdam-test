package com.rchinnaswamy.webdam.test;

import com.rchinnaswamy.webdam.base.AssetUtil;
import com.rchinnaswamy.webdam.testdata.AssetData;
import com.rchinnaswamy.webdam.testdata.AssetDataProvider;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Test(groups = "searchtests")
public class SearchAssetTests extends BaseTest {
    private static Logger log = LogManager.getLogger(SearchAssetTests.class);
    private AssetUtil assetUtil;

    public SearchAssetTests() {
        assetUtil = new AssetUtil();
    }

    @Test
    public void testSearchAssetsByAssetId() {
        String assetId = "12343";
        Response searchResp = assetUtil.searchAssets(this.accessToken, assetId, null, 5);
        searchResp.then().
                statusCode(200).
                body("$", hasSize(1)).
                body("[0].asset_id", equalTo(assetId));
    }

    @Test(dataProvider = "TextSearchCriteriaAndData", dataProviderClass = AssetDataProvider.class)
    public void testSearchAssetsByText(String text, String sortOrder, int maxResults) {
        //String text = "an apple a day";
        Response searchResp = assetUtil.searchAssets(this.accessToken, text, sortOrder, maxResults);
        searchResp.then().
                statusCode(200).
                body("$", hasSize(greaterThan(0)));

        JsonPath jp = searchResp.jsonPath();
        List<String> textList = jp.getList("text");
        for(String t : textList) {
            assertThat(t, containsString(text));
        }
    }

    @Test(dataProvider = "KeywordSearchCriteria", dataProviderClass = AssetDataProvider.class)
    public void testSearchAssetsByKeyword(String keyword, String sortOrder, int maxResults) {
        Response searchResp = assetUtil.searchAssets(this.accessToken, keyword, sortOrder, maxResults);
        searchResp.then().
                statusCode(200).
                body("$", hasSize(greaterThan(1))).
                body("$", hasSize(lessThanOrEqualTo(maxResults)));

        JsonPath jp = searchResp.jsonPath();
        List<List<String>> keyWordsList = jp.getList("keywords");
        int count = 0;
        for(List<String> keywords : keyWordsList) {
            count++;
            log.info("Keywords for Asset#" + count + " : " + keywords);
            assertThat(keyword, isIn(keywords));
            log.info("Match found for Keyword - " + keyword);
        }

    }

    @Test
    public void testSearchResultsSortOrderAsc() {
        String assetId = "123";
        Response searchResp = assetUtil.searchAssets(this.accessToken, assetId, "asc", 10);
        searchResp.then().
                statusCode(200).
                body("$", hasSize(greaterThan(1))).
                body("$", hasSize(lessThanOrEqualTo(10)));

        JsonPath jp = searchResp.jsonPath();
        List<String> assetIdList = jp.getList("asset_id");
        log.info("List of asset ids: " + assetIdList);
        boolean isAscending = true;
        for(int i=1; i<assetIdList.size(); i++) {
            if(assetIdList.get(i-1).compareTo(assetIdList.get(i)) > 0) {
                isAscending = false;
                break;
            }
        }
        assertThat(isAscending, equalTo(true));
    }

    @Test
    public void testSearchResultsSortOrderDesc() {
        String assetId = "123";
        Response searchResp = assetUtil.searchAssets(this.accessToken, assetId, "desc", 10);
        searchResp.then().
                statusCode(200).
                body("$", hasSize(greaterThan(1))).
                body("$", hasSize(lessThanOrEqualTo(10)));

        JsonPath jp = searchResp.jsonPath();
        List<String> assetIdList = jp.getList("asset_id");
        log.info("List of asset ids: " + assetIdList);
        boolean isDescending = true;
        for(int i=1; i<assetIdList.size(); i++) {
            if(assetIdList.get(i-1).compareTo(assetIdList.get(i)) < 0) {
                isDescending = false;
                break;
            }
        }
        assertThat(isDescending, equalTo(true));

    }

    @Test(dataProvider = "MaxSearchCriteria", dataProviderClass = AssetDataProvider.class)
    public void testMaxSearchResults(String searchTxt, int maxResults) {
        Response searchResp = assetUtil.searchAssets(this.accessToken, searchTxt, "rand", maxResults);
        searchResp.then().
                statusCode(200).
                body("$", hasSize(maxResults));
    }
}
