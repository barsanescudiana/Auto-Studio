package com.example.autostudio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CarActivity extends AppCompatActivity {

    Car testCar;
    TextView textName, textDetails, totalKM, range, toITP, avg;
    ImageView color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

        textName = (TextView) findViewById(R.id.textCarName);
        textDetails = (TextView) findViewById(R.id.textDetails);
        totalKM = (TextView) findViewById(R.id.totalKMtext);
        range = (TextView) findViewById(R.id.range);
        toITP = (TextView) findViewById(R.id.toITP);
        avg = (TextView) findViewById(R.id.avg);
        color = (ImageView) findViewById(R.id.imgColor);

        testCar = (Car) savedInstanceState.get("selectedCar");
        String carName = testCar.getBrand() + " " +testCar.getModel();
        String motor = String.valueOf(testCar.getEngineCapacity()/1000) + "." + String.valueOf(testCar.getEngineCapacity()/100%10);
        String details = motor + " " + testCar.getEngineOutput() + "hp " + testCar.getFuel();
        textName.setText(carName);
        textDetails.setText(details);
        totalKM.setText(String.valueOf(testCar.getKm()));
        avg.setText(String.valueOf(testCar.getAvgConsumption()));
        toITP.setText("200 d");

        range.setText(String.valueOf(testCar.getKm()/testCar.getAvgConsumption()));
    }
}