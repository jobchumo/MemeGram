package com.jobchumo.memegram;

public class MemePosts {
    private int mImageResource;
    private String mUsername;
    private String mCaption;

    public MemePosts(int mImageResource, String mUsername, String mCaption) {
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
