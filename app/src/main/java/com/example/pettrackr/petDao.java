package com.example.pettrackr;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface petDao {
    @Insert
    void insert(Pet pet);

    @Update
    void update(Pet pet);

    @Delete
    void delete(Pet pet);

    @Query("DELETE FROM pet_table")
    void deleteAllPets();

    @Query("SELECT * FROM pet_table")
    LiveData<List<Pet>> getAllPets();
    //https://developer.android.com/training/data-storage/room/accessing-data#query-collection-args
    //for future use -> click on pet and retrieve for ID, maybe use for scheduled activities with pet with ID
}
