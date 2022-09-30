package com.example.conta.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.conta.Objects.Habit;

import java.util.List;

@Dao
public interface HabitDao {
    @Query("SELECT * FROM Habits")
    List<Habit> getALL();

    @Query("SELECT * FROM Habits WHERE partOfTheDay = :partToSearch ORDER BY hourSun")
    List<Habit> getPartInOrder(int partToSearch);

    @Query("SELECT * FROM Habits WHERE partOfTheDay = :partSearch")
    List<Habit> getFromPart(int partSearch);

    @Query("SELECT * FROM Habits WHERE id = :idSearch")
    Habit getId(int idSearch);

    @Insert
    void insert(Habit habit);

    @Delete
    void delete(Habit habit);
}
