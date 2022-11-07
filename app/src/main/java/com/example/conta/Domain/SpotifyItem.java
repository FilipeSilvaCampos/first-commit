package com.example.conta.Domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class SpotifyItem {
    @SerializedName("name")
    private String name;

    @SerializedName("uri")
    private String uri;

    @SerializedName("type")
    private String type;

    @SerializedName("images")
    private List<SpotifyImage> images;

    private boolean selected;

    public String getName() {return name;}

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public List<SpotifyImage> getImages() {
        return images;
    }

}
