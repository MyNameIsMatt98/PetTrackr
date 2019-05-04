package com.example.pettrackr;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@android.arch.persistence.room.Database(entities = {Pet.class, Event.class},version = 2)
public abstract class Database extends RoomDatabase {

    private static Database instance;

    public abstract petDao petdao();

    public abstract eventDao eventdao();

    public static synchronized Database getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    Database.class,
                    "pet_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
