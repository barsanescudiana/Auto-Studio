package com.example.autostudio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class CarActivity extends AppCompatActivity {

    Car testCar;
    TextView textName, textDetails, totalKM, range, toITP, avg;
    ImageView color;
    Button event;
    ListView eventsListView;
    DatabaseAutoStudio databaseAutoStudio;
    ArrayList<Event> eventsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

        Bundle bundle = getIntent().getExtras();
        testCar = (Car) bundle.getSerializable("SELECTED");

        eventsList = new ArrayList<>();
        eventsListView = findViewById(R.id.eventListView);

//        Date testDate = new Date("10/04/2019");
//        Event event1 = new Event("Changed break pads", testDate, 1);
//        Event event2 = new Event("Revision", testDate, 1);
//        Event event3 = new Event("Changed seasonal tires", testDate, 1);
//
//        eventsList.add(event1);
//        eventsList.add(event2);
//        eventsList.add(event3);

        databaseAutoStudio = DatabaseAutoStudio.getInstance(this);
        eventsList = (ArrayList<Event>) databaseAutoStudio.getEventsDao().getAll(testCar.getCarId());

        EventAdapter eventsAdapter = new EventAdapter(CarActivity.this, R.layout.event_list_item, eventsList, getLayoutInflater());
        eventsListView.setAdapter(eventsAdapter);

        textName = (TextView) findViewById(R.id.textCarName);
        textDetails = (TextView) findViewById(R.id.textDetails);
        totalKM = (TextView) findViewById(R.id.totalKMtext);
        range = (TextView) findViewById(R.id.range);
        toITP = (TextView) findViewById(R.id.toITP);
        avg = (TextView) findViewById(R.id.avg);
        color = (ImageView) findViewById(R.id.imgColor);

        event = (Button) findViewById(R.id.newEvent);
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent eventIntent = new Intent(getApplicationContext(), EventActivity.class);
                eventIntent.putExtra("CAR_ID", testCar.getCarId());
                startActivity(eventIntent);
            }
        });

        String carName = testCar.getBrand() + " " + testCar.getModel();
        String motor = testCar.getEngineCapacity() / 1000 + "." + testCar.getEngineCapacity() / 100 % 10;
        String details = motor + " " + testCar.getEngineOutput() + "hp " + testCar.getFuel();
        textName.setText(carName);
        textDetails.setText(details);
        String kkm = testCar.getKm() / 1000 + "K";
        String mkm = testCar.getKm() / 1000000 + "M";
        String km = testCar.getKm() / 1000 + "." + Math.round(testCar.getKm() / 100) % 10;
        if (testCar.getKm() >= 100000 && testCar.getKm() <= 999999) {
            totalKM.setText(kkm);
        } else if (testCar.getKm() > 100000000) {
            totalKM.setText(mkm);
        } else
            totalKM.setText(km);
        avg.setText(String.valueOf(testCar.getAvgConsumption()));
        toITP.setText("200 d");

        range.setText(String.valueOf(testCar.getKm() / testCar.getAvgConsumption()));

        switch (testCar.getColor()) {
            case "Blue":
                color.setImageDrawable(getDrawable(R.drawable.blue_card));
                break;
            case "Gray":
                color.setImageDrawable(getDrawable(R.drawable.grey_card));
                break;
            case "Green":
                color.setImageDrawable(getDrawable(R.drawable.green_card));
                break;
            case "Black":
                color.setImageDrawable(getDrawable(R.drawable.black_card));
                break;
            case "Red":
                color.setImageDrawable(getDrawable(R.drawable.red_card));
                break;
            case "Yellow":
                color.setImageDrawable(getDrawable(R.drawable.yellow_card));
                break;
            case "Purple":
                color.setImageDrawable(getDrawable(R.drawable.purple_card));
                break;
            case "White":
                color.setImageDrawable(getDrawable(R.drawable.white_card));
                break;
        }

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