package com.example.pettrackr;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class petViewModel extends AndroidViewModel {
    private petRepository repository;
    private LiveData<List<Pet>> allPets;

    public petViewModel(@NonNull Application application) {
        super(application);
        repository = new petRepository(application);
        allPets = repository.getAllPets();

    }
    public void insert(Pet pet){
        repository.insert(pet);
    }
    public void update(Pet pet){
        repository.update(pet);
    }
    public void delete(Pet pet){
        repository.delete(pet);
    }
    public void deleteAllPets(){
        repository.deleteAllPets();
    }
    public LiveData<List<Pet>> getAllPets(){
        return allPets;
    }
}
