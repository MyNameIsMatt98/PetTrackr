package com.example.pettrackr;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class eventAdapter extends RecyclerView.Adapter<eventAdapter.EventHolder> {
    private List<Event> events = new ArrayList<>();

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.event_item,viewGroup,false);
        return new EventHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder eventHolder, int i) {
        Event currentEvent = events.get(i);
        eventHolder.textViewName.setText(currentEvent.getName());
        eventHolder.textViewDate.setText(currentEvent.getDate());
        eventHolder.textViewTime.setText(currentEvent.getTime());
        eventHolder.textViewLocation.setText(currentEvent.getLocation());
        eventHolder.textViewDescription.setText(currentEvent.getDescription());
        eventHolder.textViewPetName.setText(currentEvent.getPet());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void setEvents( List<Event> events ){
        this.events = events;
        notifyDataSetChanged();
    }

    class EventHolder extends RecyclerView.ViewHolder{
        private TextView textViewName;
        private TextView textViewDate;
        private TextView textViewTime;
        private TextView textViewLocation;
        private TextView textViewDescription;
        private TextView textViewPetName;

        public EventHolder(View itemView){
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewDate = itemView.findViewById(R.id.text_view_date);
            textViewTime = itemView.findViewById(R.id.text_view_time);
            textViewLocation = itemView.findViewById(R.id.text_view_location);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPetName = itemView.findViewById(R.id.text_view_petname);
        }
    }
}
