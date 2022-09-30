package com.example.conta.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.conta.DataBase.AppDataBase;
import com.example.conta.Objects.Habit;
import com.example.conta.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static AppDataBase db;
    List<Habit> morningUserHabits;
    List<Habit> afternoonUserHabits;
    List<Habit> nightUserHabits;

    CardView morningContainer;
    CardView afternoonContainer;
    CardView nightContainer;

    CardView morningFirstHabitContanier;
    ImageView morningFirstHabitImage;
    TextView morningFirstHabitName;
    TextView morningFirstHabitHour;
    TextView morningFirstHabitDescription;
    CardView morningSecondHabitContainer;
    ImageView morningSecondHabitImage;
    TextView morningSecondHabitName;
    TextView morningSecondHabitHour;
    TextView morningSecondHabitDescription;

    CardView afternoonFirstHabitContainer;
    ImageView afternoonFirstHabitImage;
    TextView afternoonFirstHabitName;
    TextView afternoonFirstHabitHour;
    TextView afternoonFirstHabitDescription;
    CardView afternoonSecondHabitContainer;
    ImageView afternoonSecondHabitImage;
    TextView afternoonSecondHabitName;
    TextView afternoonSecondHabitHour;
    TextView afternoonSecondHabitDescription;

    CardView nightFirstHabitContainer;
    ImageView nightFirstHabitImage;
    TextView nightFirstHabitName;
    TextView nightFirstHabitHour;
    TextView nightFirstHabitDescription;
    CardView nightSecondHabitContainer;
    ImageView nightSecondHabitImage;
    TextView nightSecondHabitName;
    TextView nightSecondHabitHour;
    TextView nightSecondHabitDescription;

    FloatingActionButton toNewHabit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent fullListIntent = new Intent(this, HabitsThisPartDay.class);
        morningContainer = findViewById(R.id.morningContainer);
        afternoonContainer = findViewById(R.id.afternoonContainer);
        nightContainer = findViewById(R.id.nightContainer);

        morningFirstHabitContanier = findViewById(R.id.morningFirstContainerHabit);
        morningFirstHabitImage = findViewById(R.id.mornigFirstHabitImage);
        morningFirstHabitName = findViewById(R.id.morningFirstHabitName);
        morningFirstHabitHour = findViewById(R.id.morningFirstHabitHour);
        morningFirstHabitDescription = findViewById(R.id.morningFirstHabitDescription);
        morningSecondHabitContainer = findViewById(R.id.morningSecondHabitContainer);
        morningSecondHabitImage = findViewById(R.id.mornigSecondHabitImage);
        morningSecondHabitName = findViewById(R.id.morningSecondHabitName);
        morningSecondHabitHour = findViewById(R.id.morningSecondHabitHour);
        morningSecondHabitDescription = findViewById(R.id.morningSecondHabitDescription);

        afternoonFirstHabitContainer = findViewById(R.id.afternoonFirstHabitContainer);
        afternoonFirstHabitImage = findViewById(R.id.afternoonFirstHabitImage);
        afternoonFirstHabitName = findViewById(R.id.afternoonFirstHabitName);
        afternoonFirstHabitHour = findViewById(R.id.afternoonFirstHabitHour);
        afternoonFirstHabitDescription = findViewById(R.id.afternoonFirstHabitDescription);
        afternoonSecondHabitContainer = findViewById(R.id.afternoonSecondHabitContainer);
        afternoonSecondHabitImage = findViewById(R.id.afternoonSecondHabitImage);
        afternoonSecondHabitName = findViewById(R.id.afternoonSecondHabitName);
        afternoonSecondHabitHour = findViewById(R.id.afternoonSecondHabitHour);
        afternoonSecondHabitDescription = findViewById(R.id.afternoonSecondHabitDescription);

        nightFirstHabitContainer = findViewById(R.id.nightFirstHabitContanier);
        nightFirstHabitImage = findViewById(R.id.nightFirstHabitImage);
        nightFirstHabitName = findViewById(R.id.nightFirstHabitName);
        nightFirstHabitHour = findViewById(R.id.nightFirstHabitHour);
        nightFirstHabitDescription = findViewById(R.id.nightFirstHabitDescription);
        nightSecondHabitContainer = findViewById(R.id.nightSecondHabitContainer);
        nightSecondHabitImage = findViewById(R.id.nightSecondHabitImage);
        nightSecondHabitName = findViewById(R.id.nightSecondHabitName);
        nightSecondHabitHour = findViewById(R.id.nightSecondHabitHour);
        nightSecondHabitDescription = findViewById(R.id.nightSecondHabitDescription);

        toNewHabit = findViewById(R.id.btnCreatNewHabit);

        toNewHabit.setOnClickListener((view -> {
            Intent intent = new Intent(this, CreatHabitActivity.class);
            startActivity(intent);
        }));

        morningContainer.setOnClickListener((view -> {
            HabitsThisPartDay.partToAdapter = 0;
            startActivity(fullListIntent);
        }));

        afternoonContainer.setOnClickListener((view -> {
            HabitsThisPartDay.partToAdapter = 1;
            startActivity(fullListIntent);
        }));

        nightContainer.setOnClickListener((view -> {
            HabitsThisPartDay.partToAdapter = 2;
            startActivity(fullListIntent);
        }));

        //CANAL PARA NOTIFICAÇÕES
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
        db = AppDataBase.retrieveDatabaseInstance(this);
        morningUserHabits = db.habitDao().getPartInOrder(0);
        afternoonUserHabits = db.habitDao().getPartInOrder(1);
        nightUserHabits = db.habitDao().getPartInOrder(2);
        setViews();
    }

    void setViews() {
        morningFirstHabitContanier.setVisibility(View.GONE);
        morningSecondHabitContainer.setVisibility(View.GONE);
        afternoonFirstHabitContainer.setVisibility(View.GONE);
        afternoonSecondHabitContainer.setVisibility(View.GONE);
        nightFirstHabitContainer.setVisibility(View.GONE);
        nightSecondHabitContainer.setVisibility(View.GONE);

        if (!morningUserHabits.isEmpty()) {
            morningFirstHabitContanier.setVisibility(View.VISIBLE);
            Habit habit = morningUserHabits.get(0);
            morningFirstHabitImage.setImageResource(habit.getImage());
            morningFirstHabitName.setText(habit.getName());
            morningFirstHabitHour.setText(habit.getHour());
            morningFirstHabitDescription.setText(habit.getDescription());
            if (morningUserHabits.size() > 1) {
                morningSecondHabitContainer.setVisibility(View.VISIBLE);
                habit = morningUserHabits.get(1);
                morningSecondHabitImage.setImageResource(habit.getImage());
                morningSecondHabitName.setText(habit.getName());
                morningSecondHabitHour.setText(habit.getHour());
                morningSecondHabitDescription.setText(habit.getDescription());
            }
        }

        if (!afternoonUserHabits.isEmpty()) {
            afternoonFirstHabitContainer.setVisibility(View.VISIBLE);
            Habit habit = afternoonUserHabits.get(0);
            afternoonFirstHabitImage.setImageResource(habit.getImage());
            afternoonFirstHabitName.setText(habit.getName());
            afternoonFirstHabitHour.setText(habit.getHour());
            afternoonFirstHabitDescription.setText(habit.getDescription());
            if (afternoonUserHabits.size() > 1) {
                afternoonSecondHabitContainer.setVisibility(View.VISIBLE);
                habit = afternoonUserHabits.get(1);
                afternoonSecondHabitImage.setImageResource(habit.getImage());
                afternoonSecondHabitName.setText(habit.getName());
                afternoonSecondHabitHour.setText(habit.getHour());
                afternoonSecondHabitDescription.setText(habit.getDescription());
            }
        }

        if (!nightUserHabits.isEmpty()) {
            nightFirstHabitContainer.setVisibility(View.VISIBLE);
            Habit habit = afternoonUserHabits.get(0);
            nightFirstHabitImage.setImageResource(habit.getImage());
            nightFirstHabitName.setText(habit.getName());
            nightFirstHabitHour.setText(habit.getHour());
            nightFirstHabitDescription.setText(habit.getDescription());
            if (afternoonUserHabits.size() > 1) {
                nightSecondHabitContainer.setVisibility(View.VISIBLE);
                habit = afternoonUserHabits.get(1);
                nightSecondHabitImage.setImageResource(habit.getImage());
                nightSecondHabitName.setText(habit.getName());
                nightSecondHabitHour.setText(habit.getHour());
                nightSecondHabitDescription.setText(habit.getDescription());
            }
        }

    }
}