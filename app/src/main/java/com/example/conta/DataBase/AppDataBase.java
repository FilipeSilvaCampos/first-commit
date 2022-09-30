package com.example.conta.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.conta.Objects.Habit;

@Database(entities = {Habit.class}, version = 4)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase AppDataBaseInstance = null;

    public abstract HabitDao habitDao();

    public static AppDataBase retrieveDatabaseInstance(Context context) {
        if (AppDataBaseInstance == null) {
            AppDataBaseInstance = Room.databaseBuilder(
                            context,
                            AppDataBase.class,
                            "AppDataBase"
                    )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return AppDataBaseInstance;
    }
}
