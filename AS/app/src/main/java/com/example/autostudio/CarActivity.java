package com.example.autostudio;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CarActivity extends AppCompatActivity {

    Car testCar;
    TextView textName, textDetails, totalKM, range, toITP, avg;
    ImageView color;
    Button event;
    ListView eventsListView;
    DatabaseAutoStudio databaseAutoStudio;
    ArrayList<Event> eventsList;

    public static final int REQUEST_CODE = 100;

    private Calendar toCalendar(long timestamp)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

        Bundle bundle = getIntent().getExtras();
        testCar = (Car) bundle.getSerializable("SELECTED");

        eventsList = new ArrayList<>();
        eventsListView = findViewById(R.id.eventListView);

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
                startActivityForResult(eventIntent, REQUEST_CODE);
            }
        });

        String carName = testCar.getBrand() + " " + testCar.getModel();
        String motor = testCar.getEngineCapacity() / 1000 + "." + testCar.getEngineCapacity() / 100 % 10;
        String details = motor + " " + testCar.getEngineOutput() + "hp " + testCar.getFuel();
        textName.setText(carName);
        textDetails.setText(details);
        String kkm = testCar.getKm() / 1000 + "K";
        String mkm = testCar.getKm() / 1000000 + "M";
        if (testCar.getKm() >= 100 && testCar.getKm() <= 999999) {
            totalKM.setText(kkm);
        } else if (testCar.getKm() > 100000000) {
            totalKM.setText(mkm);
        }

        avg.setText(String.valueOf(testCar.getAvgConsumption()));

        Calendar date = toCalendar(testCar.getExpDateITP().getTime());

        long msDiff = Calendar.getInstance().getTimeInMillis() - date.getTimeInMillis();
        long daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);

        toITP.setText(String.valueOf(daysDiff) + " d");

        //pt fiecare trip, din tank capacity trb sa scadem (triprange * avg) / 100
        //10.7 .... 100km
        //x ...... 50km

        range.setText(String.valueOf((testCar.getTankCapacity() / testCar.getAvgConsumption()) * 100 - 50));

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            eventsList.clear();

            eventsList = (ArrayList<Event>) databaseAutoStudio.getEventsDao().getAll(testCar.getCarId());
            EventAdapter eventsAdapter = new EventAdapter(CarActivity.this, R.layout.event_list_item, eventsList, getLayoutInflater());
            eventsListView.setAdapter(eventsAdapter);
        }
    }
}