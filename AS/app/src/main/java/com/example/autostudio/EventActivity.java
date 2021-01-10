package com.example.autostudio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.autostudio.classes.Event;

import java.util.Date;

public class EventActivity extends AppCompatActivity {

    DatabaseAutoStudio databaseAutoStudio;
    Button add;
    long carId;
    String eventName;
    Event event;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);

        databaseAutoStudio = DatabaseAutoStudio.getInstance(this);
        carId = getIntent().getLongExtra("CAR_ID", -1);

        final Spinner eventSpinner = findViewById(R.id.event_spinner);

        final Spinner actionSpinner = findViewById(R.id.action_spinner);

        final EditText mechanic = findViewById(R.id.mechanic_edit);
        if(mechanic.getVisibility() == View.VISIBLE) {
            String myMechanic = preferences.getString("MECHANIC", "");
            mechanic.setText(myMechanic);
        }

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
                if (carId != -1) {

                    TextView date = findViewById(R.id.date_input);
                    EditText costText = findViewById(R.id.value_edit);
                    Double cost;
                    Spinner costCurrency = findViewById(R.id.value_spinner);

                    if(date.getText().toString().isEmpty() || date.getText().toString().trim().isEmpty()) {
                        date.setError("Date not added!");
                    } else if (costText.getText().toString().isEmpty() || costText.getText().toString().trim().isEmpty()){
                        costText.setError("Cost not added!");
                    } else {
                        if (costCurrency.getSelectedItem().equals("EUR")) {
                            cost = 4.8 * Double.parseDouble(costText.getText().toString());
                        } else {
                            cost = Double.parseDouble(costText.getText().toString());
                        }
                        switch (eventSpinner.getSelectedItem().toString()) {
                            case "ITP":
                            case "RCA":
                            case "Accident":
                                if(eventSpinner.getSelectedItem().toString().equals("ITP")) {
                                    databaseAutoStudio.getCarsDao().updateCarITPById(new Date(date.getText().toString()), carId);
                                }

                                if(eventSpinner.getSelectedItem().toString().equals("RCA")) {
                                    databaseAutoStudio.getCarsDao().updateCarRCAById(new Date(date.getText().toString()), carId);
                                }
                                event = new Event(eventSpinner.getSelectedItem().toString(),
                                        eventName,
                                        new Date(date.getText().toString()),
                                        "",
                                        cost,
                                        carId);
                                break;
                            case "Service visit":
                                if(mechanic.getText().toString().isEmpty() || mechanic.getText().toString().trim().isEmpty()) {
                                    mechanic.setError("Mechanic not added!");
                                } else {
                                    EditText mechanic = findViewById(R.id.mechanic_edit);
                                    event = new Event(eventSpinner.getSelectedItem().toString(),
                                            actionSpinner.getSelectedItem().toString(),
                                            new Date(date.getText().toString()),
                                            "Mechanic: " + mechanic.getText().toString(),
                                            cost,
                                            carId);
                                }
                                break;
                        }
                        databaseAutoStudio.getEventsDao().insert(event);
                    }
                }

                setResult(RESULT_OK);
                finish();
            }
        });

        Button settings = findViewById(R.id.btn_settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });
        Button dashboard = findViewById(R.id.btn_main);
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dashboardIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(dashboardIntent);
            }
        });
    }
}