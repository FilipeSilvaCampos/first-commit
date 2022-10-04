package com.example.conta.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.conta.DataBase.AppDataBase;
import com.example.conta.Objects.Habit;
import com.example.conta.R;
import com.example.conta.Fragments.SelectIconFragment;
import com.example.conta.Pickers.TimePicker;
import com.example.conta.Recieves.recieve;

import java.util.Calendar;
import java.util.List;

public class CreatHabitActivity extends AppCompatActivity {

    AppDataBase db;
    List<Habit> userHabits;

    EditText nameCreatHabit;
    ImageView imageCreatHabit;
    TextView hourCreatHabit;
    Switch defineCreatAlarmHabit;
    EditText descriptionCreatHabit;
    Button btnCreatHabit;

    public static int resourceIdImageCreat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_habit);

        db = AppDataBase.retrieveDatabaseInstance(this);
        userHabits = db.habitDao().getALL();

        imageCreatHabit = findViewById(R.id.imageCreatHabit);
        nameCreatHabit = findViewById(R.id.nameCreatHabit);
        hourCreatHabit = findViewById(R.id.hourCreatHabit);
        defineCreatAlarmHabit = findViewById(R.id.defineCreatAlamrHabit);
        descriptionCreatHabit = findViewById(R.id.descriptionCreatHabit);
        btnCreatHabit = findViewById(R.id.btnCreatHabit);

        SelectIconFragment test = new SelectIconFragment(imageCreatHabit);
        FragmentManager testManager = getSupportFragmentManager();

        imageCreatHabit.setOnClickListener((view -> {
            test.show(testManager,"teste");
        }));

        btnCreatHabit.setOnClickListener((view -> {
            creatHabit();
        }));

        hourCreatHabit.setOnClickListener((view -> {
            TimePicker timePicker = new TimePicker(hourCreatHabit);
            FragmentManager manager = getSupportFragmentManager();
            timePicker.show(manager,"time");
        }));
    }

    void creatHabit() {
        if (defineCreatAlarmHabit.isChecked()) {
            Habit newHabit = new Habit(nameCreatHabit.getText().toString(),
                    descriptionCreatHabit.getText().toString(),
                    hourCreatHabit.getText().toString());
            newHabit.setImageResource(resourceIdImageCreat);
            newHabit.setId(userHabits.size());
            newHabit.setStateAlarm(true);
            db.habitDao().insert(newHabit);

            String[] hourBreakApart = newHabit.getHour().split(":");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE,
                    Integer.parseInt(hourBreakApart[0]),
                    Integer.parseInt(hourBreakApart[1]),
                    0);

            Intent habitIntent = new Intent(this, recieve.class);
            habitIntent.putExtra("habitID",newHabit.getId());

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent =
                    PendingIntent.getBroadcast(this, newHabit.getId(), habitIntent, 0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

            finish();
        } else {
            Habit newHabit = new Habit(nameCreatHabit.getText().toString(),
                    descriptionCreatHabit.getText().toString(),
                    hourCreatHabit.getText().toString());
            newHabit.setImageResource(resourceIdImageCreat);
            newHabit.setId(userHabits.size());
            newHabit.setStateAlarm(false);
            db.habitDao().insert(newHabit);

            finish();
        }
    }
}