package com.example.autostudio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
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

        Bundle bundle = getIntent().getExtras();
        testCar = (Car) bundle.getSerializable("SELECTED");
        String carName = testCar.getBrand() + " " +testCar.getModel();
        String motor = String.valueOf(testCar.getEngineCapacity()/1000) + "." + String.valueOf(testCar.getEngineCapacity()/100%10);
        String details = motor + " " + testCar.getEngineOutput() + "hp " + testCar.getFuel();
        textName.setText(carName);
        textDetails.setText(details);
        String kkm = String.valueOf(testCar.getKm()/1000) + "K";
        String mkm = String.valueOf(testCar.getKm()/1000000) + "M";
        String km = String.valueOf(testCar.getKm()/1000) +"."+String.valueOf(Math.round(testCar.getKm()/100)%10);
        if(testCar.getKm() >= 100000 && testCar.getKm() <= 999999){
            totalKM.setText(kkm);
        } else if (testCar.getKm() > 100000000) {
            totalKM.setText(mkm);
        } else
            totalKM.setText(km);
        avg.setText(String.valueOf(testCar.getAvgConsumption()));
        toITP.setText("200 d");

        range.setText(String.valueOf(testCar.getKm()/testCar.getAvgConsumption()));

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
    }
}