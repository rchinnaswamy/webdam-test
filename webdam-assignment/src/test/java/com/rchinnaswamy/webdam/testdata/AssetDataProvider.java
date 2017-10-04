package com.rchinnaswamy.webdam.testdata;

import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by rchinnaswamy on 10/3/17.
 */
public class AssetDataProvider {

    @DataProvider(name="GetAssetData")
    public static Object[][] getAssetData() {
        AssetData asset1 = createAssetData("12341",
                new String[] {"apple","red", "pink"},
                "an apple a day keeps the doctor away",
                "http://interview-testing-api.webdamdb.com/images/apples.jpg",
                1494287713,
                1483275313
                );
        AssetData asset2 = createAssetData("12344",
                new String[]{"apple", "cherry", "red"},
                "Esa es una bonita Manzana",
                "http://interview-testing-api.webdamdb.com/images/red-apple.jpg",
                1483285713,
                1494285713
                );

        return new Object[][] { {asset1}, {asset2} };
    }

    @DataProvider(name="TextSearchCriteriaAndData")
    public static Object[][] getCriteriaAndAssetsForTextSearch() {

        /*AssetData asset1 = createAssetData("12341",
                new String[] {"apple","red", "pink"},
                "an apple a day keeps the doctor away",
                "http://interview-testing-api.webdamdb.com/images/apples.jpg",
                1494287713,
                1483275313
        );
        AssetData asset2 = createAssetData("12342",
                new String[]{"human", "happy", "pink"},
                "a lovely old man smiling",
                "http://interview-testing-api.webdamdb.com/images/old-man-smile.jpg",
                1493285713,
                1494287713
        );
        AssetData asset3 = createAssetData("12343",
                new String[] {"boat", "cherry", "red"},
                "This cherry red boat from 1986 is one of a kind",
                "http://interview-testing-api.webdamdb.com/images/red-boat.jpg",
                1483285713,
                1483275313
        );
        AssetData asset4 = createAssetData("12344",
                new String[]{"apple", "cherry", "red"},
                "Esa es una bonita Manzana",
                "http://interview-testing-api.webdamdb.com/images/red-apple.jpg",
                1483285713,
                1494285713
        );
        AssetData asset5 = createAssetData("12345",
                new String[]{"human", "sleepy"},
                "¿Estás cansado?",
                "http://interview-testing-api.webdamdb.com/images/sleepy-human.jpg",
                1483275313,
                1494286666
        );

        ArrayList<AssetData> assetList1 = new ArrayList<AssetData>();
        assetList1.add(asset1);
        ArrayList<AssetData> assetList2 = new ArrayList<AssetData>();
        assetList2.add(asset5);
        ArrayList<AssetData>assetList3 = new ArrayList<AssetData>();
        assetList3.add(asset2);
        assetList3.add(asset4);*/

        return new Object[][] {
                { "an apple a day", "asc", 5},
                { "¿Estás", null, 5},
                { "old man", "desc", 5}
        };
    }

    @DataProvider(name="KeywordSearchCriteria")
    public static Object[][] getCriteriaForKeywordSearch() {
        return new Object[][] {
                { "human", "asc", 10 },
                { "apple", "asc", 10},
                { "pink", "asc", 10}
        };
    }

    @DataProvider(name="MaxSearchCriteria")
    public static Object[][] getCriteriaForMaxSearchResults() {
        return new Object[][] {
                { "interview-testing-api", 2 },
                { "123", 4},
                { ".jpg", 1}
        };
    }

    @DataProvider(name="InvalidSortParam")
    public static Object[][] getSearchCriteriaWithInvalidSortParam() {
        ArrayList<String> errorMessages1 = new ArrayList<String>();
        errorMessages1.add("The sort may not be greater than 4 characters.");

        ArrayList<String> errorMessages2 = new ArrayList<String>();
        errorMessages2.add("The sort may only contain letters.");

        ArrayList<String> errorMessages3 = new ArrayList<String>();
        errorMessages3.addAll(errorMessages1);
        errorMessages3.addAll(errorMessages2);

        return new Object[][] {
                {"1234", errorMessages2},
                {"ab12", errorMessages2},
                {"a&*1", errorMessages2},
                {"ascending", errorMessages1},
                {"345descending", errorMessages3},
                {"random", errorMessages1}
        };
    }

    private static AssetData createAssetData(String assetId, String[] keywords, String text, String thumbnail,
                                             int dateCreated, int dateModifed) {
        AssetData asset = new AssetData();
        asset.setAssetId(assetId);
        asset.setKeywords(keywords);
        asset.setText(text);
        asset.setThumbnailUrl(thumbnail);
        asset.setDateCreated(dateCreated);
        asset.setDateModified(dateModifed);
        return asset;

    }
}
