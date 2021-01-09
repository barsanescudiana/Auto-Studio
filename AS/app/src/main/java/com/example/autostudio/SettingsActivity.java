package com.example.autostudio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    EditText mechanic, gasStation;
    Spinner car;
    Button add, delete;
    CarsDB carsDB;
    ArrayList<Car> carsList;
    SharedPreferences preferences;

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

    public void checkPreferences() {
        if(preferences.contains("initialized")) {
            add.setVisibility(View.INVISIBLE);

            String mechanic = preferences.getString("MECHANIC", "");
            String gas = preferences.getString("GAS_STATION", "");
            int carIndex = preferences.getInt("CAR_INDEX", 0);

            this.mechanic.setText(mechanic);
            this.gasStation.setText(gas);
            this.car.setSelection(carIndex);

        } else {

            add.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        mechanic = (EditText) findViewById(R.id.mechanic_edit);
        gasStation = (EditText) findViewById(R.id.gas_edit);
        add = findViewById(R.id.add_button);
        car = findViewById(R.id.car_spinner);

        checkPreferences();

        carsList = new ArrayList<>();

        String newMechanic;

        mechanic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SharedPreferences.Editor editor;
                editor = preferences.edit();

                if(preferences.contains("MECHANIC")) {
                    editor.remove("MECHANIC");
                    editor.putString("MECHANIC", s.toString());

                    editor.apply();
                }
            }
        });

        gasStation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SharedPreferences.Editor editor;
                editor = preferences.edit();

                if(preferences.contains("GAS_STATION")) {
                    editor.remove("GAS_STATION");
                    editor.putString("GAS_STATION", s.toString());

                    editor.apply();
                }
            }
        });

        car.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor;
                editor = preferences.edit();

                if(preferences.contains("CAR_INDEX")) {
                    if(position != preferences.getInt("CAR_INDEX", 0)) {
                        editor.remove("CAR_INDEX");
                        editor.putInt("CAR_INDEX", position);

                        editor.apply();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        carsDB = CarsDB.getInstance(this);
        carsList = (ArrayList<Car>) carsDB.getCarsDao().getAll();

        ArrayList<String> details = new ArrayList<>();
        for(Car car : carsList) {
            String motor = String.valueOf(car.getEngineCapacity()/1000) + "." + String.valueOf(car.getEngineCapacity()/100%10);
            String carDetails =  car.getBrand() + " " + car.getModel() + " " + motor + " " + car.getEngineOutput() + "hp " + car.getFuel() + " " + car.getColor();

            details.add(carDetails);
        }

        ArrayAdapter<String> carAdapter = new ArrayAdapter<String>(this,
                R.layout.event_spinner_item,
                details);
        carAdapter.setDropDownViewResource(R.layout.event_spinner_dropdown_item);
        car.setAdapter(carAdapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor;
                editor = preferences.edit();

                editor.putBoolean("initialized", true);

                editor.putString("MECHANIC", mechanic.getText().toString());
                editor.putString("GAS_STATION", gasStation.getText().toString());

                editor.putInt("CAR_INDEX", car.getSelectedItemPosition());

                editor.commit();

                add.setVisibility(View.INVISIBLE);

                checkPreferences();
            }
        });

    }
}