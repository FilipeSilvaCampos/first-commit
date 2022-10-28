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

    //Make it easier to sort: getPartInOrder - habitDao.class
    @ColumnInfo(name = "hourSun")
    private int hourSun;

    @ColumnInfo(name = "stateAlarm")
    private Boolean stateAlarm;

    @ColumnInfo(name = "typeSound")
    private boolean typeSound;

    public Habit(int image,int id,String name,String description,String hour,boolean stateAlarm,boolean typeSound) {
        setImage(image);
        setId(id);
        setName(name);
        setDescription(description);
        setHour(hour);
        setStateAlarm(stateAlarm);
        setTypeSound(typeSound);

    }

    public void setImage(int integer) {this.image = integer;}

    public void setId(int id) {this.id = id;}

    public void setName(String name) {
        this.name = name;
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

    public void setStateAlarm (boolean state) {this.stateAlarm = state;}

    public void setHourSun(int hourSun) {this.hourSun = hourSun;}

    public void setPartOfTheDay(int partOfTheDay) {this.partOfTheDay = partOfTheDay;}

    public void setTypeSound(boolean type) {this.typeSound = type;}


    public int[] getIntegerHour() {
        String[] hourBreakApart = hour.split(":");

        return new int[]{Integer.parseInt(hourBreakApart[0]),Integer.parseInt(hourBreakApart[0])};

    }

    public int getId() {return id;}

    public String getName() {return name;}

    public int getImage() {return image;}

    public String getDescription() {return description;}

    public String getHour() {return hour;}

    public int getPartOfTheDay() {return partOfTheDay;}

    public int getHourSun() {return hourSun;}

    public boolean getStateAlarm() {return stateAlarm;}

    public boolean getTypeSound() {return typeSound;}
}
