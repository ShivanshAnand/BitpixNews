package com.bitpix.bitpixnews.getnews;

public class StructN18 {

    public String link;
    public String title;
    public String pubTime;
    public String imgUrl;

    StructN18( String link, String title, String pubTime, String imgUrl) {
        this.link = link;
        this.title = title;
        this.imgUrl = imgUrl;
        this.pubTime = pubTime;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public String getPubTime() { return pubTime; }

    public String getImgUrl() { return imgUrl; }
}
