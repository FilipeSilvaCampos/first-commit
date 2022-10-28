package com.example.conta.Recieves;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.conta.DataBase.AppDataBase;
import com.example.conta.Objects.Habit;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

public class recieve extends BroadcastReceiver {
    public static final String CLIENT_ID = "6306cba62027448a902d0c50291f7a09";
    public static final String REDIRECT_URI = "http://com.example.conta/callback";
    SpotifyAppRemote mSpotifyAppRemote;

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

            if (habit.getTypeSound()) {
                spotifyNotification(context);
            }

        } catch (Exception e) {
            Log.e("EROC",e.toString());
        }
    }

    void spotifyNotification(Context context) {
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .build();

        SpotifyAppRemote.connect(context,connectionParams,
                new Connector.ConnectionListener() {

                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("success", "Connected! Yay!");

                        mSpotifyAppRemote.getPlayerApi().resume();

                    }

                    public void onFailure(Throwable throwable) {
                        Log.e("failure", throwable.getMessage(), throwable);

                    }
                });
    }

}

