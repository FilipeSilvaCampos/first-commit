package com.example.conta.UI;


import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.conta.Data.AppDataBase;
import com.example.conta.Domain.Habit;
import com.example.conta.R;
import com.example.conta.databinding.ActivityMainBinding;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    List<Habit> morningUserHabits;
    List<Habit> afternoonUserHabits;
    List<Habit> nightUserHabits;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
        AppDataBase db = AppDataBase.retrieveDatabaseInstance(this);
        morningUserHabits = db.habitDao().getPartInOrder(0);
        afternoonUserHabits = db.habitDao().getPartInOrder(1);
        nightUserHabits = db.habitDao().getPartInOrder(2);
        setViews();

    }

    void listHabitPartDay(int part) {
       Intent fullListIntent = new Intent(this, HabitsThisPartDayActivity.class);
       HabitsThisPartDayActivity.partToAdapter = part;
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
            Glide.with(this).load(R.drawable.morning_container_image).into(binding.morningImage);
            binding.morningContainer.setVisibility(View.VISIBLE);
            binding.morningFirstHabitContainer.setVisibility(View.VISIBLE);
            Habit habit = morningUserHabits.get(0);
            binding.morningFirstHabitImage.setImageResource(habit.getImageResource());
            binding.morningFirstHabitName.setText(habit.getTitle());
            binding.morningFirstHabitHour.setText(habit.getHour());
            binding.morningFirstHabitDescription.setText(habit.getDescription());
            if (morningUserHabits.size() > 1) {
                binding.morningSecondHabitContainer.setVisibility(View.VISIBLE);
                habit = morningUserHabits.get(1);
                binding.morningSecondHabitImage.setImageResource(habit.getImageResource());
                binding.morningSecondHabitName.setText(habit.getTitle());
                binding.morningSecondHabitHour.setText(habit.getHour());
                binding.morningSecondHabitDescription.setText(habit.getDescription());
            }
        }

        if (!afternoonUserHabits.isEmpty()) {
            Glide.with(this).load(R.drawable.afternoon_container_image).into(binding.afternoonImage);
            binding.afternoonContainer.setVisibility(View.VISIBLE);
            binding.afternoonFirstHabitContainer.setVisibility(View.VISIBLE);
            Habit habit = afternoonUserHabits.get(0);
            binding.afternoonFirstHabitImage.setImageResource(habit.getImageResource());
            binding.afternoonFirstHabitName.setText(habit.getTitle());
            binding.afternoonFirstHabitHour.setText(habit.getHour());
            binding.afternoonFirstHabitDescription.setText(habit.getDescription());
            if (afternoonUserHabits.size() > 1) {
                binding.afternoonSecondHabitContainer.setVisibility(View.VISIBLE);
                habit = afternoonUserHabits.get(1);
                binding.afternoonSecondHabitImage.setImageResource(habit.getImageResource());
                binding.afternoonSecondHabitName.setText(habit.getTitle());
                binding.afternoonSecondHabitHour.setText(habit.getHour());
                binding.afternoonSecondHabitDescription.setText(habit.getDescription());
            }
        }

        if (!nightUserHabits.isEmpty()) {
            Glide.with(this).load(R.drawable.night_container_image).into(binding.nightImage);
            binding.nightContainer.setVisibility(View.VISIBLE);
            binding.nightFirstHabitContainer.setVisibility(View.VISIBLE);
            Habit habit = nightUserHabits.get(0);
            binding.nightFirstHabitImage.setImageResource(habit.getImageResource());
            binding.nightFirstHabitName.setText(habit.getTitle());
            binding.nightFirstHabitHour.setText(habit.getHour());
            binding.nightFirstHabitDescription.setText(habit.getDescription());
            if (nightUserHabits.size() > 1) {
                binding.nightSecondHabitContainer.setVisibility(View.VISIBLE);
                habit = afternoonUserHabits.get(1);
                binding.nightSecondHabitImage.setImageResource(habit.getImageResource());
                binding.nightSecondHabitName.setText(habit.getTitle());
                binding.nightSecondHabitHour.setText(habit.getHour());
                binding.nightSecondHabitDescription.setText(habit.getDescription());
            }
        }

    }
}