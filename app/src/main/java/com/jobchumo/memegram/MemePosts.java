package com.jobchumo.memegram;

public class MemePosts {
    private String mImageResource;
    private String mUsername;
    private String mCaption;
    private String mCategory;

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public MemePosts() {
    }

    public String getmImageResource() {
        return mImageResource;
    }

    public void setmImageResource(String mImageResource) {
        this.mImageResource = mImageResource;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmCaption() {
        return mCaption;
    }

    public void setmCaption(String mCaption) {
        this.mCaption = mCaption;
    }
}
