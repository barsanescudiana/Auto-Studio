package com.example.autostudio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class EventActivity extends AppCompatActivity {

    ArrayList<String> events, actions;
    DatabaseAutoStudio databaseAutoStudio;
    Button add;
    UUID carId;
    String eventName;
    Event event;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        databaseAutoStudio = DatabaseAutoStudio.getInstance(this);
        carId = getIntent().getIntExtra("CAR_ID", 0);

        Log.e("ID MASINA", String.valueOf(carId));

        events = new ArrayList<>();

        events.add("Accident");
        events.add("ITP");
        events.add("RCA");
        events.add("Service visit");

        actions = new ArrayList<>();

        actions.add("Body");
        actions.add("Chassis");
        actions.add("Drive transmission & steering");
        actions.add("Electrical");
        actions.add("Engine");
        actions.add("Suspension & break");

        final Spinner eventSpinner = (Spinner) findViewById(R.id.event_spinner);
        ArrayAdapter<String> eventAdapter = new ArrayAdapter<>(this,
                R.layout.event_spinner_item,
                events);
        eventAdapter.setDropDownViewResource(R.layout.event_spinner_dropdown_item);
        eventSpinner.setAdapter(eventAdapter);

        final Spinner actionSpinner = (Spinner) findViewById(R.id.action_spinner);
        ArrayAdapter<String> actionAdapter = new ArrayAdapter<>(this,
                R.layout.event_spinner_item,
                actions);
        actionAdapter.setDropDownViewResource(R.layout.event_spinner_dropdown_item);
        actionSpinner.setAdapter(actionAdapter);

        eventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (eventSpinner.getSelectedItem().toString().equals("ITP") || eventSpinner.getSelectedItem().toString().equals("RCA")) {
                    findViewById(R.id.action_layout).setVisibility(View.GONE);
                    findViewById(R.id.mechanic_layout).setVisibility(View.GONE);
                    eventName = "Renewed " + eventSpinner.getSelectedItem().toString();
                } else if (eventSpinner.getSelectedItem().toString().equals("Accident")) {
                    findViewById(R.id.action_layout).setVisibility(View.GONE);
                    findViewById(R.id.mechanic_layout).setVisibility(View.GONE);
                    eventName = eventSpinner.getSelectedItem().toString();
                } else {
                    findViewById(R.id.action_layout).setVisibility(View.VISIBLE);
                    findViewById(R.id.mechanic_layout).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        add = findViewById(R.id.save_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(carId != 0) {
                    TextView date = findViewById(R.id.date_input);
                    EditText costText = findViewById(R.id.value_edit);
                    Double cost;
                    Spinner costCurrency = findViewById(R.id.value_spinner);
                    if(costCurrency.getSelectedItem().equals("EUR")) {
                        cost = 4.8 * Double.parseDouble(costText.getText().toString());
                    } else {
                        cost = Double.parseDouble(costText.getText().toString());
                    }
                    switch (eventSpinner.getSelectedItem().toString()) {
                        case "ITP":
                        case "RCA":
                        case "Accident":
                            event = new Event(eventSpinner.getSelectedItem().toString(),
                                    eventName,
                                    new Date(date.getText().toString()),
                                    "",
                                    cost,
                                    carId);
                            break;
                        case "Service visit":
                            EditText mechanic = findViewById(R.id.mechanic_edit);
                            event = new Event(eventSpinner.getSelectedItem().toString(),
                                    actionSpinner.getSelectedItem().toString(),
                                    new Date(date.getText().toString()),
                                    "Mechanic: " + mechanic.getText().toString(),
                                    cost,
                                    carId);
                            break;
                    }

                    Log.e("EVENT", event.toString());
                    databaseAutoStudio.getEventsDao().insert(event);
                }
            }
        });

        Button settings = (Button) findViewById(R.id.btn_settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });
        Button dashboard = (Button) findViewById(R.id.btn_main);
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dashboardIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(dashboardIntent);
            }
        });
    }
}