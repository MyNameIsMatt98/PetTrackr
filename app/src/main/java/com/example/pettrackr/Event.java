package com.example.pettrackr;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Event_table")
public class Event {

    @PrimaryKey(autoGenerate = true)
    private int ID;

    private String name;

    private boolean recurring;

    private String date;

    private String time;

    private String pet;

    public int getID() {
        return ID;
    }

    public Event(String name, boolean recurring, String date, String time, String pet, String location, String description) {
        this.name = name;
        this.recurring = recurring;
        this.date = date;
        this.time = time;
        this.pet = pet;
        this.location = location;
        this.description = description;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String location;

    private String description;
}
