package com.example.conta.Activitys;

import static com.example.conta.Recieves.recieve.CLIENT_ID;
import static com.example.conta.Recieves.recieve.REDIRECT_URI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.conta.DataBase.AppDataBase;
import com.example.conta.Objects.Habit;
import com.example.conta.databinding.ActivityMainBinding;
import com.spotify.android.appremote.api.ConnectionParams;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private AppDataBase db;
    List<Habit> morningUserHabits;
    List<Habit> afternoonUserHabits;
    List<Habit> nightUserHabits;

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        spotifyPermission();

        binding.btnCreatNewHabit.setOnClickListener((view -> {
            Intent intent = new Intent(this, CreatHabitActivity.class);
            startActivity(intent);
        }));

        binding.morningContainer.setOnClickListener((view -> listHabitPartDay(0)));

        binding.afternoonContainer.setOnClickListener((view -> listHabitPartDay(1)));

        binding.nightContainer.setOnClickListener((view -> listHabitPartDay(2)));

        //CHANNEL FOR NOTIFICATIONS
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("canal", "canal", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("canal de teste");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpActivity();

    }

    void setUpActivity() {
        db = AppDataBase.retrieveDatabaseInstance(this);
        morningUserHabits = db.habitDao().getPartInOrder(0);
        afternoonUserHabits = db.habitDao().getPartInOrder(1);
        nightUserHabits = db.habitDao().getPartInOrder(2);
        setViews();

    }

    void spotifyPermission() {
        new ConnectionParams.Builder(CLIENT_ID)
                .setRedirectUri(REDIRECT_URI)
                .showAuthView(true)
                .build();

    }

    void listHabitPartDay(int part) {
       Intent fullListIntent = new Intent(this, HabitsThisPartDay.class);
       HabitsThisPartDay.partToAdapter = part;
       startActivity(fullListIntent);

    }

    void setViews() {
        binding.morningContainer.setVisibility(View.GONE);
        binding.morningFirstHabitContainer.setVisibility(View.GONE);
        binding.morningSecondHabitContainer.setVisibility(View.GONE);

        binding.afternoonContainer.setVisibility(View.GONE);
        binding.afternoonFirstHabitContainer.setVisibility(View.GONE);
        binding.afternoonSecondHabitContainer.setVisibility(View.GONE);

        binding.nightContainer.setVisibility(View.GONE);
        binding.nightFirstHabitContainer.setVisibility(View.GONE);
        binding.nightSecondHabitContainer.setVisibility(View.GONE);

        if (!morningUserHabits.isEmpty()) {
            binding.morningContainer.setVisibility(View.VISIBLE);
            binding.morningFirstHabitContainer.setVisibility(View.VISIBLE);
            Habit habit = morningUserHabits.get(0);
            binding.morningFirstHabitImage.setImageResource(habit.getImage());
            binding.morningFirstHabitName.setText(habit.getName());
            binding.morningFirstHabitHour.setText(habit.getHour());
            binding.morningFirstHabitDescription.setText(habit.getDescription());
            if (morningUserHabits.size() > 1) {
                binding.morningSecondHabitContainer.setVisibility(View.VISIBLE);
                habit = morningUserHabits.get(1);
                binding.morningSecondHabitImage.setImageResource(habit.getImage());
                binding.morningSecondHabitName.setText(habit.getName());
                binding.morningSecondHabitHour.setText(habit.getHour());
                binding.morningSecondHabitDescription.setText(habit.getDescription());
            }
        }

        if (!afternoonUserHabits.isEmpty()) {
            binding.afternoonContainer.setVisibility(View.VISIBLE);
            binding.afternoonFirstHabitContainer.setVisibility(View.VISIBLE);
            Habit habit = afternoonUserHabits.get(0);
            binding.afternoonFirstHabitImage.setImageResource(habit.getImage());
            binding.afternoonFirstHabitName.setText(habit.getName());
            binding.afternoonFirstHabitHour.setText(habit.getHour());
            binding.afternoonFirstHabitDescription.setText(habit.getDescription());
            if (afternoonUserHabits.size() > 1) {
                binding.afternoonSecondHabitContainer.setVisibility(View.VISIBLE);
                habit = afternoonUserHabits.get(1);
                binding.afternoonSecondHabitImage.setImageResource(habit.getImage());
                binding.afternoonSecondHabitName.setText(habit.getName());
                binding.afternoonSecondHabitHour.setText(habit.getHour());
                binding.afternoonSecondHabitDescription.setText(habit.getDescription());
            }
        }

        if (!nightUserHabits.isEmpty()) {
            binding.nightContainer.setVisibility(View.VISIBLE);
            binding.nightFirstHabitContainer.setVisibility(View.VISIBLE);
            Habit habit = nightUserHabits.get(0);
            binding.nightFirstHabitImage.setImageResource(habit.getImage());
            binding.nightFirstHabitName.setText(habit.getName());
            binding.nightFirstHabitHour.setText(habit.getHour());
            binding.nightFirstHabitDescription.setText(habit.getDescription());
            if (nightUserHabits.size() > 1) {
                binding.nightSecondHabitContainer.setVisibility(View.VISIBLE);
                habit = afternoonUserHabits.get(1);
                binding.nightSecondHabitImage.setImageResource(habit.getImage());
                binding.nightSecondHabitName.setText(habit.getName());
                binding.nightSecondHabitHour.setText(habit.getHour());
                binding.nightSecondHabitDescription.setText(habit.getDescription());
            }
        }

    }
}