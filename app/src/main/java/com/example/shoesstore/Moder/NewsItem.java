package com.example.shoesstore.Moder;

public class NewsItem {
    private String title;
    private String description;
    private String link;
    private String linkImage;

    public NewsItem(String title, String description, String link, String linkImage) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.linkImage = linkImage;
    }

    public NewsItem() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }
}
