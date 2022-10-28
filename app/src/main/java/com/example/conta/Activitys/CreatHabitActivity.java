package com.example.conta.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.conta.DataBase.AppDataBase;
import com.example.conta.Objects.Habit;
import com.example.conta.R;
import com.example.conta.Fragments.SelectIconFragment;
import com.example.conta.Pickers.TimePicker;
import com.example.conta.Recieves.recieve;
import com.example.conta.databinding.ActivityCreatHabitBinding;

import java.util.Calendar;
import java.util.List;

public class CreatHabitActivity extends AppCompatActivity {
    private AppDataBase db;
    private List<Habit> userHabits;

    public static int resourceIdImageCreat = R.drawable.coffee;
    private boolean spotifySound;
    private boolean defineAlarm;

    ActivityCreatHabitBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatHabitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = AppDataBase.retrieveDatabaseInstance(this);
        userHabits = db.habitDao().getALL();

        SelectIconFragment test = new SelectIconFragment(binding.imageCreatHabit);
        FragmentManager testManager = getSupportFragmentManager();

        binding.imageCreatHabit.setOnClickListener((view -> test.show(testManager, "teste")));

        binding.btnCreatHabit.setOnClickListener((view -> creatHabit()));

        binding.tvHourCreat.setOnClickListener((view -> {
            TimePicker timePicker = new TimePicker(binding.tvHourCreat);
            FragmentManager manager = getSupportFragmentManager();
            timePicker.show(manager, "time");
        }));

        binding.tvSoundAlarm.setOnClickListener((view -> addSpotifyAlarm()));

        binding.btnAlarmState.setOnClickListener((view -> activeAlarm()));
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

    void addSpotifyAlarm() {
        if (spotifySound) {
            spotifySound = false;
            binding.tvSoundAlarm.setText(R.string.txt_default_ringtone);
            binding.tvSoundAlarm.setTextColor(Color.rgb(41, 102, 195));
            binding.btnAlarmState.setColorFilter(Color.rgb(41,102,195));
        } else {
            spotifySound = true;
            binding.tvSoundAlarm.setText(R.string.txt_spotify_ringtone);
            binding.tvSoundAlarm.setTextColor(Color.rgb(50, 205, 50));
            binding.btnAlarmState.setColorFilter(Color.rgb(50,205,50));
        }
    }

    void creatHabit() {
        Habit newHabit;
        if (defineAlarm) {
            newHabit = new Habit(resourceIdImageCreat,
                    userHabits.size(),
                    binding.edtNameCreat.getText().toString(),
                    binding.edtDescriptionCreat.getText().toString(),
                    binding.tvHourCreat.getText().toString(),
                    true,
                    spotifySound);
            db.habitDao().insert(newHabit);

            int[] hourBreakApart = newHabit.getIntegerHour();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, Calendar.MONTH, Calendar.DATE,
                    hourBreakApart[0],
                    hourBreakApart[1],
                    0);

            Intent habitIntent = new Intent(this, recieve.class);
            habitIntent.putExtra("habitID", newHabit.getId());

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent =
                    PendingIntent.getBroadcast(this, newHabit.getId(), habitIntent, 0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        } else {
            newHabit = new Habit(resourceIdImageCreat,
                    userHabits.size(),
                    binding.edtNameCreat.getText().toString(),
                    binding.edtDescriptionCreat.getText().toString(),
                    binding.tvHourCreat.getText().toString(),
                    false,
                    spotifySound);
            db.habitDao().insert(newHabit);

        }
        finish();
    }
}