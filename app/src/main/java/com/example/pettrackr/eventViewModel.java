package com.example.pettrackr;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class eventViewModel extends AndroidViewModel {
    private eventRepository repository;
    private LiveData<List<Event>> allEvents;
    private LiveData<List<Event>> allNonRecurringEvents;
    private LiveData<List<Event>> allDateEvents;
    private String dayOfWeek;
    private String date;

    public eventViewModel(@NonNull Application application) {
        super(application);
        repository = new eventRepository(application);
        allEvents = repository.getAllEvents();
        allNonRecurringEvents = repository.getAllNonRecurringEvents();
        allDateEvents = repository.getAllDateEvents(dayOfWeek,date);

    }
    public void insert(Event event){
        repository.insert(event);

    }
    public void update(Event event){
        repository.update(event);

    }
    public void delete(Event event){
        repository.delete(event);
    }
    public void deleteAllEvents(){
        repository.deleteAllEvents();
    }


    public LiveData<List<Event>> getAllNonRecurringEvents(){
        return allNonRecurringEvents;
    }

    public LiveData<List<Event>> getAllEvents(){
        return allEvents;
    }

    public LiveData<List<Event>> getAllDateEvents(String dw, String d){
        dayOfWeek= dw;
        date = d;
        allDateEvents = repository.getAllDateEvents(dayOfWeek,date);
        return allDateEvents;
    }





    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
