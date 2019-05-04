package com.example.pettrackr;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class petRepository {
    private petDao petdao;
    private LiveData<List<Pet>> allPets;

    public petRepository(Application application){
        Database database = Database.getInstance(application);
        petdao = database.petdao();
        allPets = petdao.getAllPets();
    }
    public void insert(Pet pet){
        new InsertPetAsyncTask(petdao).execute(pet);
    }
    public void update(Pet pet){
        new UpdatePetAsyncTask(petdao).execute(pet);
    }
    public void delete(Pet pet){
        new DeletePetAsyncTask(petdao).execute(pet);
    }
    public void deleteAllPets(){
        new DeleteAllPetsAsyncTask(petdao).execute();
    }

    public LiveData<List<Pet>> getAllPets() {
        return allPets;
    }

    private static class InsertPetAsyncTask extends AsyncTask<Pet, Void, Void> {
        private petDao PetDao;

        private InsertPetAsyncTask(petDao PetDao) {
            this.PetDao = PetDao;
        }

        @Override
        protected Void doInBackground(Pet... Pets) {
            PetDao.insert(Pets[0]);
            return null;
        }
    }

    private static class UpdatePetAsyncTask extends AsyncTask<Pet, Void, Void> {
        private petDao PetDao;

        private UpdatePetAsyncTask(petDao PetDao) {
            this.PetDao = PetDao;
        }

        @Override
        protected Void doInBackground(Pet... Pets) {
            PetDao.update(Pets[0]);
            return null;
        }
    }

    private static class DeletePetAsyncTask extends AsyncTask<Pet, Void, Void> {
        private petDao PetDao;

        private DeletePetAsyncTask(petDao PetDao) {
            this.PetDao = PetDao;
        }

        @Override
        protected Void doInBackground(Pet... Pets) {
            PetDao.delete(Pets[0]);
            return null;
        }
    }

    private static class DeleteAllPetsAsyncTask extends AsyncTask<Void, Void, Void> {
        private petDao PetDao;

        private DeleteAllPetsAsyncTask(petDao PetDao) {
            this.PetDao = PetDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            PetDao.deleteAllPets();
            return null;
        }
    }

}
