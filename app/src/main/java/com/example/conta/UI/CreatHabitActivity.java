package com.example.conta.UI;

import static com.example.conta.UI.Recieve.CLIENT_ID;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.conta.Data.AppDataBase;
import com.example.conta.Domain.Habit;
import com.example.conta.Domain.SpotifyItem;
import com.example.conta.R;
import com.example.conta.UI.Fragments.SelectIconFragment;
import com.example.conta.UI.Fragments.TimePickerFragment;
import com.example.conta.databinding.ActivityCreatHabitBinding;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;


public class CreatHabitActivity extends AppCompatActivity {
    private AppDataBase db;
    private FragmentManager manager;

    public static int resourceImage = R.drawable.coffee;
    private boolean spotifyAlarm;
    private boolean defineAlarm;
    public static SpotifyItem spotifyItem;

    private static final int REQUEST_CODE = 1337;
    private static final String REDIRECT_URI = Recieve.REDIRECT_URI;
    public static String token;

    private ActivityCreatHabitBinding binding;
    public static TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatHabitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        textView = binding.tvSoundAlarm;

        db = AppDataBase.retrieveDatabaseInstance(this);
        manager = getSupportFragmentManager();

        requireSpotifyPermission();


        SelectIconFragment selectIcon = new SelectIconFragment(binding.imageCreatHabit);

        binding.imageCreatHabit.setOnClickListener((view -> selectIcon.show(manager, "teste")));

        binding.btnCreatHabit.setOnClickListener((view -> createHabit()));

        binding.tvHourCreat.setOnClickListener((view -> {
            TimePickerFragment timePickerFragment = new TimePickerFragment(binding.tvHourCreat);
            timePickerFragment.show(manager, "time");
        }));

        binding.tvSoundAlarm.setOnClickListener((view -> changeAlarmSound()));

        binding.btnAlarmState.setOnClickListener((view -> activeAlarm()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);

            switch (response.getType()) {
                case TOKEN:
                    token = "Bearer " + response.getAccessToken();

                    break;

                // Auth flow returned an error
                case ERROR:
                    // Handle error response
                    break;

                // Most likely auth flow was cancelled
                default:
                    // Handle other cases
            }
        }

    }

    void activeAlarm() {
        if (defineAlarm) {
            defineAlarm = false;
            binding.btnAlarmState.setAlpha(0.5f);
            binding.tvSoundAlarm.setAlpha(0.5f);
        } else {
            defineAlarm = true;
            binding.btnAlarmState.setAlpha(1f);
            binding.tvSoundAlarm.setAlpha(1f);
        }

    }

    void changeAlarmSound() {
        if (spotifyAlarm) {
            spotifyAlarm = false;
            binding.tvSoundAlarm.setText(R.string.txt_default_ringtone);
            binding.tvSoundAlarm.setTextColor(Color.rgb(41, 102, 195));
            binding.btnAlarmState.setColorFilter(Color.rgb(41,102,195));

        } else {
            spotifyAlarm = true;
            binding.tvSoundAlarm.setText(R.string.txt_spotify_ringtone);
            binding.tvSoundAlarm.setTextColor(Color.rgb(50, 205, 50));
            binding.btnAlarmState.setColorFilter(Color.rgb(50,205,50));
            Intent intent = new Intent(this, SelectSpotifyItemActivity.class);
            intent.putExtra("idView",binding.tvSoundAlarm.getId());
            startActivity(intent);

        }

    }

    void createHabit() {
        Habit newHabit;
        if (defineAlarm) {
            newHabit = new Habit(resourceImage,
                    db.habitDao().getALL().size(),
                    binding.edtTitleCreat.getText().toString(),
                    binding.edtDescriptionCreat.getText().toString(),
                    binding.tvHourCreat.getText().toString(),
                    true,
                    spotifyAlarm,
                    this);

        } else {
            newHabit = new Habit(resourceImage,
                    db.habitDao().getALL().size(),
                    binding.edtTitleCreat.getText().toString(),
                    binding.edtDescriptionCreat.getText().toString(),
                    binding.tvHourCreat.getText().toString(),
                    false,
                    spotifyAlarm);

        }
        if (spotifyAlarm) {
            newHabit.setPlaylistName(spotifyItem.getName());
            newHabit.setPlaylistUri(spotifyItem.getUri());
        }
        db.habitDao().insert(newHabit);
        finish();

    }

    void requireSpotifyPermission() {
        AuthorizationRequest.Builder builder =
                new AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{"user-library-read", "app-remote-control"});
        AuthorizationRequest request = builder.build();

        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request);

    }

}