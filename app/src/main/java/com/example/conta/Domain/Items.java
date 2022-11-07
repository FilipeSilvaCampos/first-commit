package com.example.conta.Domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Items {
    @SerializedName(value = "items")
    List<SpotifyItem> spotifyItems;

    public List<SpotifyItem> getSpotifyItems() {
        return spotifyItems;
    }

    public void setSpotifyItems(List<SpotifyItem> spotifyItems) {
        this.spotifyItems = spotifyItems;
    }

}
