package com.killer.storytime;

public class StoryData {
    private String imageUrl, moral, story, title;

    public StoryData(String imageUrl, String moral, String story, String title) {
        this.imageUrl = imageUrl;
        this.moral = moral;
        this.story = story;
        this.title = title;


    }

    public StoryData() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMoral() {
        return moral;
    }

    public void setMoral(String moral) {
        this.moral = moral;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
