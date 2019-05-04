package com.example.pettrackr;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class AddEvent extends AppCompatActivity {
//https://stackoverflow.com/questions/39037707/how-to-display-a-spinner-when-a-radio-button-clicked
    public static final String EXTRA_EVENT_NAME = "com.example.pettrackr.EXTRA_EVENT_NAME";
    public static final String EXTRA_EVENT_DATE = "com.example.pettrackr.EXTRA_EVENT_DATE";
    public static final String EXTRA_EVENT_TIME = "com.example.pettrackr.EXTRA_EVENT_TIME";
    public static final String EXTRA_EVENT_LOCATION = "com.example.pettrackr.EXTRA_EVENT_LOCATION";
    public static final String EXTRA_EVENT_DESCRIPTION = "com.example.pettrackr.EXTRA_EVENT_DESCRIPTION";
    public static final String EXTRA_EVENT_PETNAME = "com.example.pettrackr.EXTRA_EVENT_PETNAME";
    public static final String EXTRA_RECUR = "com.example.pettrackr.EXTRA_RECUR";


final Calendar theCalendar = Calendar.getInstance();
    Switch recurring;
    Spinner dayOfWeek;
    EditText date_editText;
    EditText name_editText;
    EditText time_editText;
    EditText location_editText;
    EditText description_editText;
    EditText petName_editText;
    Boolean recur;
    Button addEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_add_event);
        dayOfWeek = findViewById(R.id.day_of_week_spinner);
        recurring = findViewById(R.id.recurring_switch);
        date_editText = findViewById(R.id.date_edit_text);
        name_editText = findViewById(R.id.edit_text_name);
        time_editText = findViewById(R.id.time_edit_text);
        location_editText = findViewById(R.id.location_edit_text);
        description_editText = findViewById(R.id.description_edit_text);
        petName_editText = findViewById(R.id.pet_name_edit_text);
        addEvent = findViewById(R.id.event_save_button);

        Toolbar toolbar = findViewById(R.id.toolbarAddEvent);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Event");



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days_of_week, android.R.layout.simple_spinner_item); //load items for spinner
        dayOfWeek.setAdapter(adapter);
        dayOfWeek.setVisibility(View.GONE);
        date_editText.setVisibility(View.VISIBLE);
        recur = false;


        recurring.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    dayOfWeek.setVisibility(View.VISIBLE);
                    date_editText.setVisibility(View.GONE);
                    recur = true;
                }else{
                    dayOfWeek.setVisibility(View.GONE);
                    date_editText.setVisibility(View.VISIBLE);
                    recur = false;
                }

            }
        });
        recurring.setChecked(false);

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEvent();
            }
        });
        tapDate();
        tapTime();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_pet_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_pet:
                saveEvent();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void saveEvent(){
        String eventName = name_editText.getText().toString();
        Boolean eventRecurring = recur;
        String eventDate = date_editText.getText().toString();
        if(recur){
            eventDate = dayOfWeek.getSelectedItem().toString();
        }
        String eventTime = time_editText.getText().toString();
        String Location ="";
        if(!location_editText.getText().toString().isEmpty()){
            Location = location_editText.getText().toString();
        }
        String Description ="";
        if(!description_editText.getText().toString().isEmpty()){
            Description = description_editText.getText().toString();
        }
        String PetName ="";
        if(!petName_editText.getText().toString().isEmpty()){
            PetName = petName_editText.getText().toString();
        }
        if(eventName.trim().isEmpty()|eventDate.trim().isEmpty()|eventTime.trim().isEmpty()){
            Toast.makeText(this, "Enter Name, Date and Time", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_EVENT_NAME,eventName);
        data.putExtra(EXTRA_EVENT_DATE,eventDate);
        data.putExtra(EXTRA_EVENT_TIME,eventTime);
        data.putExtra(EXTRA_EVENT_LOCATION,Location);
        data.putExtra(EXTRA_EVENT_DESCRIPTION,Description);
        data.putExtra(EXTRA_EVENT_PETNAME,PetName);
        data.putExtra(EXTRA_RECUR,eventRecurring);

        setResult(RESULT_OK,data);
        finish();
    }

    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            theCalendar.set(theCalendar.HOUR_OF_DAY,hourOfDay);
            theCalendar.set(theCalendar.MINUTE,minute);
            setTime();
        }
    };

    public void tapTime(){
        time_editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(AddEvent.this,time,theCalendar.get(theCalendar.HOUR_OF_DAY),
                        theCalendar.get(theCalendar.MINUTE),true).show();
            }
        });
    }
    private void setTime(){
        String theTimeFormat = "HH:mm";
        SimpleDateFormat sdfTime = new SimpleDateFormat(theTimeFormat, Locale.ENGLISH);
        time_editText.setText(sdfTime.format(theCalendar.getTime()));
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            theCalendar.set(Calendar.YEAR,year);
            theCalendar.set(Calendar.MONTH,month);
            theCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            setDate();//add later
        }
    };

    public void tapDate(){
        date_editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddEvent.this,date,theCalendar.get(Calendar.YEAR)
                        ,theCalendar.get(Calendar.MONTH),
                        theCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
    }

    private void setDate(){
        String theFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(theFormat, Locale.ENGLISH);
        date_editText.setText(sdf.format(theCalendar.getTime()));
    }



}
