package com.example.pettrackr;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.pettrackr.MainActivity.ADD_EVENT_REQUEST;

public class RemindersFragment extends Fragment {
    private eventViewModel eventViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Reminders");
        View v = inflater.inflate(R.layout.fragment_reminders,container,false);

        FloatingActionButton buttonAddPet = v.findViewById(R.id.button_add_event_reminders);
        buttonAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddEvent.class);
                startActivityForResult(intent,ADD_EVENT_REQUEST);
            }
        });



        RecyclerView recyclerView = v.findViewById(R.id.reminders_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final eventAdapter adapter = new eventAdapter();
        recyclerView.setAdapter(adapter);

        eventViewModel = ViewModelProviders.of(this).get(com.example.pettrackr.eventViewModel.class);
        eventViewModel.getAllNonRecurringEvents().observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(@Nullable List<Event> events) {
                adapter.setEvents(events);
            }
        });
        //Event test = new Event("Walk Molly",true,"Everyday","8:00","Molly","Home","Take Molly on a walk");
        //Event test2 = new Event ("Take Leela to vet",false,"03-05-2019","14:00","Leela","Matlock Vet","Appointment");
        //eventViewModel.insert(test);
        //eventViewModel.insert(test2);
        return v;

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== ADD_EVENT_REQUEST && resultCode == RESULT_OK){
            String name = data.getStringExtra(AddEvent.EXTRA_EVENT_NAME);
            Boolean recurring = data.getBooleanExtra(AddEvent.EXTRA_RECUR,false);
            String date = data.getStringExtra(AddEvent.EXTRA_EVENT_DATE);
            String time = data.getStringExtra(AddEvent.EXTRA_EVENT_TIME);
            String pet = data.getStringExtra(AddEvent.EXTRA_EVENT_PETNAME);
            String location = data.getStringExtra(AddEvent.EXTRA_EVENT_LOCATION);
            String desc = data.getStringExtra(AddEvent.EXTRA_EVENT_DESCRIPTION);

            Event event = new Event(name,recurring,date,time,pet,location,desc);
            eventViewModel.insert(event);

            Toast.makeText(getActivity(), "Event Added", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(), "Event Not Saved", Toast.LENGTH_SHORT).show();
        }
    }
}
