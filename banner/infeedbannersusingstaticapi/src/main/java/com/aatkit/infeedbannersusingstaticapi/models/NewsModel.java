package com.aatkit.infeedbannersusingstaticapi.models;

public class NewsModel {

    private final String title;
    private final String news;

    public NewsModel(String title, String news) {
        this.title = title;
        this.news = news;
    }

    public String getTitle() {
        return title;
    }

    public String getNews() {
        return news;
    }
}
