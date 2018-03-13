package com.example.saksh.imanews;

import android.util.Log;

/**
 * Created by saksh on 19-02-2018.
 */

public class NewsItem {
    private String title;
    private String decription;
    private String imageUrl;

    public NewsItem(String title, String decription,String imageUrl) {
        this.title = title;
        this.decription = decription;
        Log.v("test",decription);
        this.imageUrl=imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDecription() {
        return decription;
    }
}
