package com.example.conta.Recieves;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.conta.DataBase.AppDataBase;
import com.example.conta.Objects.Habit;

public class recieve extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            AppDataBase db = AppDataBase.retrieveDatabaseInstance(context);
            Habit habit = db.habitDao().getId(intent.getExtras().getInt("habitID"));

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"canal")
                    .setSmallIcon(habit.getImage())
                    .setContentTitle(habit.getName())
                    .setContentText(habit.getDescription())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat manager = NotificationManagerCompat.from(context);
            manager.notify(habit.getId(),builder.build());

        } catch (Exception e) {
            Log.e("EROC",e.toString());
        }
    }
}
