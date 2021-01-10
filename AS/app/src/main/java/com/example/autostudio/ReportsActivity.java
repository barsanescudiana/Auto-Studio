package com.example.autostudio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class ReportsActivity extends AppCompatActivity {

    DatabaseAutoStudio databaseAutoStudio;
    ArrayList<Car> cars;
    ArrayList<Event> events;
    Button carRep, eventRep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        databaseAutoStudio = DatabaseAutoStudio.getInstance(this);

        cars = (ArrayList<Car>) databaseAutoStudio.getCarsDao().getAll();
        events = (ArrayList<Event>) databaseAutoStudio.getEventsDao().getAll();

        carRep = findViewById(R.id.car_report);
        eventRep = findViewById(R.id.event_report);

        carRep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_report, new ReportFragment()).commit();
            }
        });

        eventRep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_report, new ReportFragment()).commit();
            }
        });

        //toolbar
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