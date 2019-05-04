package com.example.pettrackr;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class eventRepository {
    private eventDao eventDao;
    private LiveData<List<Event>> allEvents;
    private LiveData<List<Event>> allNonRecurringEvents;
    private LiveData<List<Event>> allDateEvents;
    public String dayOfWeek;
    public String date;

    public eventRepository(Application application){
        Database database = Database.getInstance(application);
        eventDao = database.eventdao();
        allEvents = eventDao.getAllEvents();
        allNonRecurringEvents = eventDao.getAllNonRecurring();
        allDateEvents = eventDao.getAllEventsOnDay(dayOfWeek, date);

    }
    public void insert(Event event){
        new InsertEventAsyncTask(eventDao).execute(event);

    }
    public void update(Event event){
        new UpdateEventAsyncTask(eventDao).execute(event);

    }
    public void delete(Event event){
        new DeleteEventAsyncTask(eventDao).execute(event);
    }
    public void deleteAllEvents(){
        new DeleteAllEventAsyncTask(eventDao).execute();
    }


    public LiveData<List<Event>> getAllNonRecurringEvents(){
        return allNonRecurringEvents;
    }

    public LiveData<List<Event>> getAllEvents(){
        return allEvents;
    }

    public LiveData<List<Event>> getAllDateEvents(String dw, String d){
        dayOfWeek = dw;
        date = d;
        allDateEvents = eventDao.getAllEventsOnDay(dayOfWeek, date);
        return allDateEvents;
    }

    private static class InsertEventAsyncTask extends AsyncTask<Event, Void, Void >{
        private eventDao eventdao;
        private InsertEventAsyncTask(eventDao eventDao){
            this.eventdao = eventDao;
        }
        @Override
        protected Void doInBackground(Event... events) {
            eventdao.insert(events[0]);
            return null;
        }
    }
    private static class UpdateEventAsyncTask extends AsyncTask<Event, Void, Void >{
        private eventDao eventdao;
        private UpdateEventAsyncTask(eventDao eventDao){
            this.eventdao = eventDao;
        }
        @Override
        protected Void doInBackground(Event... events) {
            eventdao.update(events[0]);
            return null;
        }
    }
    private static class DeleteEventAsyncTask extends AsyncTask<Event, Void, Void >{
        private eventDao eventdao;
        private DeleteEventAsyncTask(eventDao eventDao){
            this.eventdao = eventDao;
        }
        @Override
        protected Void doInBackground(Event... events) {
            eventdao.delete(events[0]);
            return null;
        }
    }
    private static class DeleteAllEventAsyncTask extends AsyncTask<Void, Void, Void >{
        private eventDao eventdao;
        private DeleteAllEventAsyncTask(eventDao eventDao){
            this.eventdao = eventDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            eventdao.deleteAllEvents();
            return null;
        }
    }
}
