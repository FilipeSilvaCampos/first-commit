package com.example.conta.UI;

import static com.example.conta.UI.CreatHabitActivity.token;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.conta.UI.Adapters.ItemSpotifyAdapter;
import com.example.conta.Data.SpotifyApi;
import com.example.conta.Domain.Items;
import com.example.conta.Domain.SpotifyTypeItems;
import com.example.conta.databinding.ActivitySelectSpotifyItemBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectSpotifyItemActivity extends AppCompatActivity {
    private ActivitySelectSpotifyItemBinding binding;
    private SpotifyApi spotifyApi;
    private ItemSpotifyAdapter spotifyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectSpotifyItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpHttpClient();
        setList(null);

        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                setList(editable.toString());
            }
        });


    }

    void setUpHttpClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spotify.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        spotifyApi = retrofit.create(SpotifyApi.class);

    }

    void setList(String search) {
        if (search == null) {
            spotifyApi.getPlaylists(token).enqueue(new Callback<Items>() {
                @Override
                public void onResponse(Call<Items> call, Response<Items> response) {
                    if (response.body() != null) {
                        spotifyAdapter = new ItemSpotifyAdapter(response.body().getSpotifyItems());
                        binding.rvSpotifyList.setAdapter(spotifyAdapter);
                    }
                }

                @Override
                public void onFailure(Call<Items> call, Throwable t) {

                }
            });
        } else {
            spotifyApi.getTracksSearch(token, search).enqueue(new Callback<SpotifyTypeItems>() {
                @Override
                public void onResponse(Call<SpotifyTypeItems> call, Response<SpotifyTypeItems> response) {
                    if (response.body() != null) {
                        spotifyAdapter = new ItemSpotifyAdapter(response.body().getUniqueList());
                        binding.rvSpotifyList.setAdapter(spotifyAdapter);
                    }

                }

                @Override
                public void onFailure(Call<SpotifyTypeItems> call, Throwable t) {

                }
            });
        }
    }
}