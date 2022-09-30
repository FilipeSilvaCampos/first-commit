package com.example.conta.Objects;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Habits")
public class Habit {

    @PrimaryKey()
    private int id;

    @ColumnInfo(name = "image")
    private int image;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "hour")
    private String hour;

    @ColumnInfo(name = "partOfTheDay")
    private int partOfTheDay;

    @ColumnInfo(name = "hourSun")
    private int hourSun;

    public Habit(String name,String description,String hour) {
        this.name = name;
        this.hour = hour;
        this.description = description;

        int foramtedeHour = Integer.parseInt(hour.split(":")[0]);
        if (foramtedeHour > 4 && foramtedeHour < 12) {
            this.partOfTheDay = 0;
        } else if (foramtedeHour >= 12 && foramtedeHour < 18) {
            this.partOfTheDay = 1;
        } else {
            this.partOfTheDay = 2;
        }

        int hourSun = Integer.parseInt(hour.split(":")[0]);
        int minuteSun = Integer.parseInt(hour.split(":")[1]);

        this.hourSun = hourSun + minuteSun;
    }

    public void setId(int id) {this.id = id;}

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public void setHour(String hour) {
        this.hour = hour;

        int hourSun = Integer.parseInt(hour.split(":")[0]);
        int minuteSun = Integer.parseInt(hour.split(":")[1]);

        this.hourSun = hourSun + minuteSun;
    }

    public void setHourSun(int hourSun) {
        this.hourSun = hourSun;
    }

    public void setPartOfTheDay(int partOfTheDay) {this.partOfTheDay = partOfTheDay;}

    public void setPartOfTheDayByHour(int hour) {
        if (hour > 4 && hour < 12) {
            this.partOfTheDay = 0;
        } else if (hour >= 12 && hour < 18) {
            this.partOfTheDay = 1;
        } else {
            this.partOfTheDay = 2;
        }
    }

    /* Obs:metodo ainda imcompleto(planejamento): definido como padrão*/
    public void setImage(int integer) {this.image = integer;}

    public void setImageResource(int id) {this.image = id;}

    public int getId() {return (Integer) id;}

    public String getName() {return (String) name;}

    public int getImage() {return (Integer) image;}

    public String getDescription() {return (String) description;}

    public String getHour() {return (String) hour;}

    public int getPartOfTheDay() {return (int) partOfTheDay;}

    public int getHourSun() {return (int) hourSun;}
}
