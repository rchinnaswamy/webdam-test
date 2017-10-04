package com.rchinnaswamy.webdam.test;


import com.rchinnaswamy.webdam.base.AssetUtil;
import com.rchinnaswamy.webdam.base.CommonUtil;
import com.rchinnaswamy.webdam.testdata.AssetDataProvider;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Test(groups = "negativetests")
public class GetSearchAssetNegativeTests extends BaseTest {
    private static Logger log = LogManager.getLogger(SearchAssetTests.class);
    private AssetUtil assetUtil;

    public GetSearchAssetNegativeTests() {
        assetUtil = new AssetUtil();
    }

    //TODO: This test can be run in a separate test suite that only includes rate limit tests
    //TODO: Work in progress. Evaluating the right solution for rate limit exceeding
    @Test(groups = "ratelimit", enabled = false)
    public void testRateLimitForGetAssets() {
        Integer maxRateLimit = new Integer(60);
        int start = 1;
        while(start <= 5) {
            Headers assetHeaders = assetUtil.headAllAssets(this.accessToken);
            assertThat(assetHeaders.getValue("X-RateLimit-Limit"), equalTo(maxRateLimit.toString()));
            assertThat(assetHeaders.getValue("X-RateLimit-Remaining"), equalTo(CommonUtil.decrementRateLimit(maxRateLimit).toString()));
            start++;
        }
    }

    /*public void testRateLimitForGetAssetsById() {

    }
    public void testRateLimitForSearchAssets() {

    }*/

    @Test
    public void testGetAssetWithExpiredAccessToken() {
        String expiredAccessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjljYjlhOTA5MjBmOTFhNjkyOWVjNDg2OTM2YWJjZGUyNDBmNTZiZmVmYjdmZmY0MzQxNGRlNTIxOTM5ZDRlMTY" +
                "yZWNhODhmZGJjZDE3MjMwIn0.eyJhdWQiOiI0IiwianRpIjoiOWNiOWE5MDkyMGY5MWE2OTI5ZWM0ODY5MzZhYmNkZTI0MGY1NmJmZWZiN2ZmZjQzNDE0ZGU1Mj" +
                "E5MzlkNGUxNjJlY2E4OGZkYmNkMTcyMzAiLCJpYXQiOjE1MDcwNjU0OTAsIm5iZiI6MTUwNzA2NTQ5MCwiZXhwIjoxNTA3MDY5MDkwLCJzdWIiOiIiLCJzY29wZXMiOltdfQ" +
                ".hqlLdRQ8sJ5JUCAI8fyMFJL_GRqBwFHQLHV6EXHEDHvTGon7VboiV0wB-mOjE9oVwsKMKgeTsa_mruBjgh2yU5WC39MwdJSdsIv1ARNn2Dr4GOw7Mk9vaiABVdcPXWaGRWww07nH4vSd3bSC1sfZ3Mzs" +
                "CJpWlKMnTMTkzqBa1v1PwVTsu3y8trDPuVncAqcRUiUupzeyohL6nDWext_RVMUTENdWfHpcsEk414zupzI1ol2D7p66suPmhMvMzZuyh8wiXtCBU2WBDXjFSQnCQYAyXiarx07TAqDpk4d8c-mgI9X_" +
                "3OXSbBg2DJINfRMFZg4nW6sPcst68Tv52MdSGl8iCbg2luCXcs6b8rjoo_sMG40mlPwQ9mYDZTqGGx_TDl05FLZdAmKjH43aSdUVv_7UbUQoy4wjxIDHF2rw30t7zpNmXwSxOZIIQnEfBKOosX6fBtC" +
                "QC8Y1jWcH9Ulnm04wbQJcf1G8t1vvPzDQVi5BZOWr8f5nrRM7GfC5mOzqNKjXg_IdCDhBhrfrBMNSQMtoPN38J5wkVDcqMzSCoI-nRPHsI5qP8FkfgBWjQipwPFvphPOvI4ddsq_06fgvZzQzmCgN_gfWNE6U6Z" +
                "ZimFqfbhjeoeLR-qrX2L-qdwqyc4_PParxfEc6vWgya0CUi_YosDICaVlAMkPFnYQfd-4";
        Response assetResp = assetUtil.getAssetById(expiredAccessToken, "12341");
        assetResp.then().
                statusCode(401).
                body("error", equalTo("Unauthenticated."));
    }

    @Test
    public void testSearchAssetWithExpiredAccessToken() {
        String expiredAccessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjljYjlhOTA5MjBmOTFhNjkyOWVjNDg2OTM2YWJjZGUyNDBmNTZiZmVmYjdmZmY0MzQxNGRlNTIxOTM5ZDRlMTY" +
                "yZWNhODhmZGJjZDE3MjMwIn0.eyJhdWQiOiI0IiwianRpIjoiOWNiOWE5MDkyMGY5MWE2OTI5ZWM0ODY5MzZhYmNkZTI0MGY1NmJmZWZiN2ZmZjQzNDE0ZGU1Mj" +
                "E5MzlkNGUxNjJlY2E4OGZkYmNkMTcyMzAiLCJpYXQiOjE1MDcwNjU0OTAsIm5iZiI6MTUwNzA2NTQ5MCwiZXhwIjoxNTA3MDY5MDkwLCJzdWIiOiIiLCJzY29wZXMiOltdfQ" +
                ".hqlLdRQ8sJ5JUCAI8fyMFJL_GRqBwFHQLHV6EXHEDHvTGon7VboiV0wB-mOjE9oVwsKMKgeTsa_mruBjgh2yU5WC39MwdJSdsIv1ARNn2Dr4GOw7Mk9vaiABVdcPXWaGRWww07nH4vSd3bSC1sfZ3Mzs" +
                "CJpWlKMnTMTkzqBa1v1PwVTsu3y8trDPuVncAqcRUiUupzeyohL6nDWext_RVMUTENdWfHpcsEk414zupzI1ol2D7p66suPmhMvMzZuyh8wiXtCBU2WBDXjFSQnCQYAyXiarx07TAqDpk4d8c-mgI9X_" +
                "3OXSbBg2DJINfRMFZg4nW6sPcst68Tv52MdSGl8iCbg2luCXcs6b8rjoo_sMG40mlPwQ9mYDZTqGGx_TDl05FLZdAmKjH43aSdUVv_7UbUQoy4wjxIDHF2rw30t7zpNmXwSxOZIIQnEfBKOosX6fBtC" +
                "QC8Y1jWcH9Ulnm04wbQJcf1G8t1vvPzDQVi5BZOWr8f5nrRM7GfC5mOzqNKjXg_IdCDhBhrfrBMNSQMtoPN38J5wkVDcqMzSCoI-nRPHsI5qP8FkfgBWjQipwPFvphPOvI4ddsq_06fgvZzQzmCgN_gfWNE6U6Z" +
                "ZimFqfbhjeoeLR-qrX2L-qdwqyc4_PParxfEc6vWgya0CUi_YosDICaVlAMkPFnYQfd-4";
        Response searchResp = assetUtil.searchAssets(expiredAccessToken, "apple", "some", 10);
        searchResp.then().
                statusCode(401).
                body("error", equalTo("Unauthenticated."));
    }

    @Test
    public void testSearchAssetWithoutAccessToken() {
        Response searchResp = assetUtil.searchAssets(" ", "human", "asc", 10);
        searchResp.then().
                statusCode(401).
                body("error", equalTo("Unauthenticated."));
    }

    //Bug: Server returns 500 error when no access token is specified. Instead 401 and Unauthenticated error should be returned
    @Test
    public void testGetAllAssetsWithoutAccessToken() {
        Response assetsResp = assetUtil.getAllAssets(" ");
        assetsResp.then().
                statusCode(401).
                body("error", equalTo("Unauthenticated."));
    }

    @Test
    public void testSearchAssetExceedingAllowedLimit() {
        Response searchResp = assetUtil.searchAssets(this.accessToken, ".jpg", "asc", 25);
        searchResp.then().
                statusCode(422).
                body("limit[0]", equalTo("The limit may not be greater than 10."));

    }

    //Possible Bug or Intentional: Negative or Zero value includes all results, not sure if there is an internal restriction for max results
    @Test
    public void testSearchAssetWithInvalidMaxLimit() {
        Response searchResp = assetUtil.searchAssets(this.accessToken, ".jpg", "asc", 0);
        searchResp.then().
                statusCode(422).
                body("limit[0]", equalTo("The limit may not be greater than 10."));
    }

    @Test(dataProvider = "InvalidSortParam", dataProviderClass = AssetDataProvider.class)
    public void testSearchAssetWithInvalidSortParam(String invalidSortParam, ArrayList<String> expectedErrors) {
        Response searchResp = assetUtil.searchAssets(this.accessToken, ".jpg", invalidSortParam, 10);
        searchResp.then().
                statusCode(422);

        JsonPath jp = searchResp.jsonPath();
        List<String> errors = jp.getList("sort");
        assertThat(errors, hasSize(expectedErrors.size()));
        for(String e : errors) {
            assertThat(e, isIn(expectedErrors));
        }
    }

    @Test
    public void testSearchAssetWithAllInvalidParams() {
        String invalidQueryStr = "alskjdjkas alkskjdsaja lakjsaldlkalks alksdakljjdsksd kasdlsajjds";
        String invalidSortOrder = "abcd1298731";
        int invalidMaxResults = 500;

        String queryErrorMsg = "The query may not be greater than 50 characters.";
        String sortErrorMsg1 = "The sort may only contain letters.";
        String sortErrorMsg2 = "The sort may not be greater than 4 characters.";
        String limitErrorMsg = "The limit may not be greater than 10.";

        Response searchResp = assetUtil.searchAssets(this.accessToken, invalidQueryStr, invalidSortOrder, invalidMaxResults);
        searchResp.then().
                statusCode(422).
                body("query[0]", equalTo(queryErrorMsg)).
                body("limit[0]", equalTo(limitErrorMsg));

        JsonPath jp = searchResp.jsonPath();
        List<String> sortErrorMessages = jp.getList("sort");
        assertThat(sortErrorMsg1, isIn(sortErrorMessages));
        assertThat(sortErrorMsg2, isIn(sortErrorMessages));
    }

    //Bug: 500 error when no params are specified, should return a 400 error that indicates a bad request
    @Test
    public void testSearchAssetWithNoParams() {
        Response searchResp = assetUtil.searchAssets(this.accessToken);
        searchResp.then().
                statusCode(400);

    }



}
