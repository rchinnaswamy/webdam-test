package com.rchinnaswamy.webdam.testdata;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by rchinnaswamy on 10/3/17.
 */
public class AssetData {
    private String assetId;
    private String text;
    private String[] keywords;
    private String thumbnailUrl;
    private int dateCreated;
    private int dateModified;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(int timeInMilliSecs) {
        this.dateCreated = timeInMilliSecs;
    }

    public int getDateModified() {
        return dateModified;
    }

    public void setDateModified(int timeInMilliSecs) {
        this.dateModified = timeInMilliSecs;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }
}
