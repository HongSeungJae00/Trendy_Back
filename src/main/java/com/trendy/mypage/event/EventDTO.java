package com.trendy.mypage.event;

public class EventDTO {
    private String title;
    private String releasePrice;
    private String recentPrice;
    private String releaseDate;
    private String imageUrl;

    public EventDTO(String title, String releasePrice, String recentPrice, String releaseDate, String imageUrl) {
        this.title = title;
        this.releasePrice = releasePrice;
        this.recentPrice = recentPrice;
        this.releaseDate = releaseDate;
        this.imageUrl = imageUrl;
    }

    // Getter 및 Setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleasePrice() {
        return releasePrice;
    }

    public void setReleasePrice(String releasePrice) {
        this.releasePrice = releasePrice;
    }

    public String getRecentPrice() {
        return recentPrice;
    }

    public void setRecentPrice(String recentPrice) {
        this.recentPrice = recentPrice;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
