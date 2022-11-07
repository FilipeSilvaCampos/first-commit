package com.example.conta.Domain;

import com.google.gson.annotations.SerializedName;


public class SpotifyImage {
    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
