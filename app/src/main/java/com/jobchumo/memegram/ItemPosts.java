package com.jobchumo.memegram;

public class ItemPosts {
    private int mImageResource;
    private String mUsername;
    private String mCaption;

    public ItemPosts(int mImageResource, String mUsername, String mCaption) {
        this.mImageResource = mImageResource;
        this.mUsername = mUsername;
        this.mCaption = mCaption;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public String getmUsername() {
        return mUsername;
    }

    public String getmCaption() {
        return mCaption;
    }

}
