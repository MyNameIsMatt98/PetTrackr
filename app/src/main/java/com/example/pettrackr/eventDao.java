package com.example.pettrackr;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface eventDao {
    @Insert
    void insert(Event event);

    @Update
    void update(Event event);

    @Delete
    void delete(Event event);

    @Query("DELETE FROM event_table")
    void deleteAllEvents();

    @Query("SELECT * FROM event_table ORDER BY date ASC, time ASC")
    LiveData<List<Event>> getAllEvents();

    @Query("SELECT * FROM event_table WHERE recurring = 0 ORDER BY date ASC, time ASC")//not sure if this is the way to check for false
    LiveData<List<Event>> getAllNonRecurring();

    @Query("SELECT * FROM event_table WHERE date = :dayOfWeek OR date = :date OR date = 'Everyday' ORDER BY time ASC")
    LiveData<List<Event>> getAllEventsOnDay(String dayOfWeek, String date);
}
