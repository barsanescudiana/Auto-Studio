package com.example.autostudio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddCarActivity2 extends AppCompatActivity {

    EditText capacity, output, avg, rca, itp, tank;
    Button save;
    Car newCar = new Car();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car2);

        capacity = findViewById(R.id.capacity_edit);
        output = findViewById(R.id.output_edit);
        avg = findViewById(R.id.avg_edit);
        rca = findViewById(R.id.rcaDate);
        itp = findViewById(R.id.itpDate);
        tank = findViewById(R.id.tank_edit);

        save = findViewById(R.id.save);

        Bundle bundle = getIntent().getExtras();
        newCar = (Car) bundle.getSerializable("NEW");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAutoStudio databaseAutoStudio = DatabaseAutoStudio.getInstance(getApplicationContext());

                newCar.setEngineCapacity(Integer.parseInt(capacity.getText().toString()));
                newCar.setEngineOutput(Integer.parseInt(output.getText().toString()));
                newCar.setAvgConsumption(Double.parseDouble(avg.getText().toString()));
                newCar.setExpDateRCA(new Date(rca.getText().toString()));
                newCar.setExpDateITP(new Date(itp.getText().toString()));
                newCar.setTankCapacity(Double.parseDouble(tank.getText().toString()));
                Log.e("newCar object: ", newCar.toString());
                databaseAutoStudio.getCarsDao().insert(newCar);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
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