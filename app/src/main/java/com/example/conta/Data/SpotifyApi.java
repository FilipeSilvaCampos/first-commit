package com.example.conta.Data;

import com.example.conta.Domain.Items;
import com.example.conta.Domain.SpotifyTypeItems;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface SpotifyApi {
    @GET("me/playlists")
    Call<Items> getPlaylists(@Header("Authorization") String token);

    @GET("search?type=track,album,artist,playlist")
    Call<SpotifyTypeItems> getTracksSearch(@Header("Authorization") String token, @Query("q") String search);
}
