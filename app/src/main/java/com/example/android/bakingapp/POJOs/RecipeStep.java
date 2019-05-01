package com.example.android.bakingapp.POJOs;

/**
 * Created by Kingdom on 5/1/2019.
 */

public class RecipeStep {
    private int id;
    private  String shortDescription;
    private String description;
    private String vidURL;
    private String thubnailURL;

    public RecipeStep(int id, String shortDescription, String description, String vidURL, String thubnailURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.vidURL = vidURL;
        this.thubnailURL = thubnailURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVidURL() {
        return vidURL;
    }

    public void setVidURL(String vidURL) {
        this.vidURL = vidURL;
    }

    public String getThubnailURL() {
        return thubnailURL;
    }

    public void setThubnailURL(String thubnailURL) {
        this.thubnailURL = thubnailURL;
    }
}
