package com.example.autostudio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ReportsActivity extends AppCompatActivity {

    DatabaseAutoStudio databaseAutoStudio;
    ArrayList<Car> cars;
    ArrayList<Event> events;
    Button carRep, eventRep;

    TextView where1, where2, title, details;
    EditText editWhere1, editWhere2;

    Button showReport;
    ListView reportList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        databaseAutoStudio = DatabaseAutoStudio.getInstance(this);

        findViewById(R.id.frame_layout).setVisibility(View.GONE);

        showReport = findViewById(R.id.query_button);
        reportList = findViewById(R.id.report_list);

        where1 = findViewById(R.id.text_where1);
        where2 = findViewById(R.id.text_where2);
        editWhere1 = findViewById(R.id.edit_where1);
        editWhere2 = findViewById(R.id.edit_where2);

        title = findViewById(R.id.report_title);

        carRep = findViewById(R.id.car_report);
        eventRep = findViewById(R.id.event_report);

        details = findViewById(R.id.query_details);

        carRep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.frame_layout).setVisibility(View.VISIBLE);
                reportList.setVisibility(View.GONE);

                where1.setText(R.string.fuel);
                where2.setText(R.string.km_t);

                editWhere1.setHint(R.string.petrol);
                editWhere1.setText("");
                editWhere2.setHint(R.string._95);
                editWhere2.setText("");

                title.setVisibility(View.GONE);
                details.setText(R.string.car_list_details);

            }
        });

        eventRep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.frame_layout).setVisibility(View.VISIBLE);
                reportList.setVisibility(View.GONE);

                where1.setText("Action");
                where2.setText("Cost");

                editWhere1.setHint("Accident");
                editWhere1.setText("");
                editWhere2.setHint(R.string._95);
                editWhere2.setText("");

                title.setVisibility(View.GONE);
                details.setText(R.string.event_details);
            }
        });

        showReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title.setVisibility(View.VISIBLE);
                findViewById(R.id.frame_layout).setVisibility(View.VISIBLE);

                String table = details.getText().toString().split(" ")[0];


                reportList.setAdapter(null);

                switch (table) {
                    case "Car":
                        title.setText(R.string.cars_report);
                        Log.e("CAR", "onClick: CAR");
                        cars = (ArrayList<Car>) databaseAutoStudio.getCarsDao().getCarsByFuelAndKm(editWhere1.getText().toString(),
                                Double.parseDouble(editWhere2.getText().toString()));

                        Log.e("LISTA", cars.toString() );

                        ArrayList<String> carsString = new ArrayList<>();

                        for(Car car : cars) {
                            String motor = car.getEngineCapacity() / 1000 + "." + car.getEngineCapacity() / 100 % 10;
                            String details = car.getBrand() + " " + car.getModel() + " " + motor + " " + car.getEngineOutput() + "hp " + car.getFuel() + " " + car.getKm();

                            carsString.add(details);
                        }

                        Log.e("LISTA STRING", carsString.toString() );

                        reportList.setVisibility(View.VISIBLE);

                        ReportAdapter carAdapter = new ReportAdapter(getApplicationContext(), R.layout.report_list_item, carsString, getLayoutInflater());
                        reportList.setAdapter(carAdapter);

                        break;
                    case "Event":
                        title.setText(R.string.events_report);
                        Log.e("EVENT", "onClick: EVENT");
                        events = (ArrayList<Event>) databaseAutoStudio.getEventsDao().getEventsByNameAndCost(editWhere1.getText().toString(),
                                Double.parseDouble(editWhere2.getText().toString()));

                        Log.e("LISTA", events.toString() );
                        ArrayList<String> eventsInfo = new ArrayList<>();

                        for(Event event : events) {
                            eventsInfo.add(event.getInfo());
                        }

                        reportList.setVisibility(View.VISIBLE);
                        ReportAdapter adapter = new ReportAdapter(getApplicationContext(), R.layout.report_list_item, eventsInfo, getLayoutInflater());
                        reportList.setAdapter(adapter);

                        break;
                }
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