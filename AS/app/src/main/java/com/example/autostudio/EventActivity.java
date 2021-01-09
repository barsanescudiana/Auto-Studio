package com.example.autostudio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EventActivity extends AppCompatActivity {

    ArrayList<String> events, actions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

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

        Spinner actionSpinner = (Spinner) findViewById(R.id.action_spinner);
        ArrayAdapter<String> actionAdapter = new ArrayAdapter<>(this,
                R.layout.event_spinner_item,
                actions);
        actionAdapter.setDropDownViewResource(R.layout.event_spinner_dropdown_item);
        actionSpinner.setAdapter(actionAdapter);

        Spinner eventSpinner = (Spinner) findViewById(R.id.event_spinner);
        ArrayAdapter<String> eventAdapter = new ArrayAdapter<>(this,
                R.layout.event_spinner_item,
                events);
        eventAdapter.setDropDownViewResource(R.layout.event_spinner_dropdown_item);
        eventSpinner.setAdapter(eventAdapter);

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