package com.example.conta.Domain;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class SpotifyTypeItems {
    @SerializedName("tracks")
    Items tracks;

    @SerializedName("albums")
    Items albums;

    @SerializedName("artists")
    Items artists;

    @SerializedName("playlists")
    Items playlists;

    public Items getTracks() {
        return tracks;
    }

    public void setTracks(Items tracks) {
        this.tracks = tracks;
    }

    public Items getAlbums() {
        return albums;
    }

    public void setAlbums(Items albums) {
        this.albums = albums;
    }

    public Items getArtists() {
        return artists;
    }

    public void setArtists(Items artists) {
        this.artists = artists;
    }

    public Items getPlaylists() {
        return playlists;
    }

    public void setPlaylists(Items playlists) {
        this.playlists = playlists;
    }

    public List<SpotifyItem> getUniqueList() {
        List<SpotifyItem> list = new ArrayList<>();

        addToList(playlists.getSpotifyItems(), list);
        addToList(tracks.getSpotifyItems(), list);
        addToList(artists.getSpotifyItems(), list);
        addToList(albums.getSpotifyItems(), list);

        return list;
    }

    private void addToList(List<SpotifyItem> getter, List<SpotifyItem> receptor) {
        receptor.addAll(getter);
    }
}
