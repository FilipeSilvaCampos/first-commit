package com.example.conta.Domain;


import android.app.AlarmManager;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.conta.R;
import com.example.conta.UI.Recieve;

import java.util.Calendar;

@Entity(tableName = "Habits")
public class Habit {

    @PrimaryKey()
    private int id;

    @ColumnInfo(name = "imageResource")
    private int imageResource;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "hour")
    private String hour;

    @ColumnInfo(name = "partOfTheDay")
    private int partOfTheDay;

    //Make it easier to sort: getPartInOrder - habitDao.class
    @ColumnInfo(name = "hourSun")
    private int hourSun;

    @ColumnInfo(name = "stateAlarm")
    private Boolean stateAlarm;

    @ColumnInfo(name = "isSpotify")
    private boolean isSpotify;

    @ColumnInfo(name = "plalistName")
    private String playlistName;

    @ColumnInfo(name = "playlistUri")
    private String playlistUri;

    @Ignore
    private Context context;


    public Habit(int imageResource, int id, String title, String description, String hour, boolean stateAlarm, boolean isSpotify) {
        setImageResource(imageResource);
        setId(id);
        setTitle(title);
        setDescription(description);
        setHour(hour);
        setStateAlarm(stateAlarm);
        setIsSpotify(isSpotify);

    }

    public Habit(int imageResource, int id, String title, String description, String hour, boolean stateAlarm, boolean isSpotify, Context context) {
        setImageResource(imageResource);
        setId(id);
        setTitle(title);
        setDescription(description);
        setHour(hour);
        setContext(context);
        setStateAlarm(stateAlarm);
        setAlarmManager();
        setIsSpotify(isSpotify);

    }

    public void setImageResource(int integer) {this.imageResource = integer;}

    public void setId(int id) {this.id = id;}

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public void setHour(String hour) {
        this.hour = hour;

        int integerHour = Integer.parseInt(hour.split(":")[0]);
        int integerMinute = Integer.parseInt(hour.split(":")[1]);
        this.hourSun = integerHour + integerMinute;

        if (integerHour > 4 && integerHour < 12) {
            this.partOfTheDay = 0;
        } else if (integerHour >= 12 && integerHour < 18) {
            this.partOfTheDay = 1;
        } else {
            this.partOfTheDay = 2;
        }

    }

    public void setStateAlarm (boolean state) {
        this.stateAlarm = state;
    }

    public void setHourSun(int hourSun) {this.hourSun = hourSun;}

    public void setPartOfTheDay(int partOfTheDay) {this.partOfTheDay = partOfTheDay;}

    public void setIsSpotify(boolean isSpotify) {this.isSpotify = isSpotify;}

    public void setPlaylistName(String playlistName) {this.playlistName = playlistName;}

    public void setPlaylistUri(String playlistUri) {this.playlistUri = playlistUri;}

    public int[] getIntegerHour() {
        String[] hourBreakApart = hour.split(":");

        return new int[]{Integer.parseInt(hourBreakApart[0]),Integer.parseInt(hourBreakApart[0])};

    }

    public int getId() {return id;}

    public String getTitle() {return title;}

    public int getImageResource() {return imageResource;}

    public String getDescription() {return description;}

    public String getHour() {return hour;}

    public int getPartOfTheDay() {return partOfTheDay;}

    public int getHourSun() {return hourSun;}

    public boolean getStateAlarm() {return stateAlarm;}

    public boolean getIsSpotify() {return isSpotify;}

    public String getPlaylistName() {return playlistName;}

    public String getPlaylistUri() {return playlistUri;}

    public void setAlarmManager() {
        if (stateAlarm) {
            int[] hourBreakApart = getIntegerHour();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE,
                    hourBreakApart[0],
                    hourBreakApart[1],
                    0);

            Intent habitIntent = new Intent(context, Recieve.class);
            habitIntent.putExtra("habitID", id);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            PendingIntent pendingIntent =
                    PendingIntent.getBroadcast(context, id, habitIntent, 0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

            Toast toast = Toast.makeText(context, R.string.txt_on_alarm,Toast.LENGTH_LONG);
            toast.show();

        } else {
            Intent toCancel = new Intent(context, Recieve.class);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(PendingIntent.getBroadcast(context, id,toCancel,0));

            Toast toast = Toast.makeText(context, R.string.txt_off_alarm,Toast.LENGTH_LONG);
            toast.show();

        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
