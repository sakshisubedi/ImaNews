package com.example.saksh.imanews;

/**
 * Created by saksh on 19-02-2018.
 */

public class MyNews {

    String titleOfNews;
    String decpofNews;
    String dateofNews;
    String addressofNews;
    String urlofNews;
    String username;
    public MyNews() {
    }

    public MyNews(String titleOfNews, String decpofNews, String dateofNews, String addressofNews, String urlofNews,String username) {
        this.titleOfNews = titleOfNews;
        this.decpofNews = decpofNews;
        this.dateofNews = dateofNews;
        this.addressofNews = addressofNews;
        this.urlofNews = urlofNews;
        this.username=username;
    }

    public String getTitleOfNews() {
        return titleOfNews;
    }

    public String getUsername() {
        return username;
    }

    public String getDecpofNews() {
        return decpofNews;
    }

    public String getDateofNews() {
        return dateofNews;
    }

    public String getAddressofNews() {
        return addressofNews;
    }

    public String getUrlofNews() {
        return urlofNews;
    }

    public void setAddressofNews(String addressofNews) {
        this.addressofNews = addressofNews;
    }

}
