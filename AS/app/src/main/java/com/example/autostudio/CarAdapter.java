package com.example.autostudio;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Date;

public class CarAdapter extends ArrayAdapter<Car> {

    private final Context context;
    private final int resource;
    private final ArrayList<Car> carList;
    private final LayoutInflater layoutInflater;

    public final Date current = new Date();


    public CarAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Car> carList, LayoutInflater layoutInflater) {
        super(context, resource, carList);
        this.context = context;
        this.resource = resource;
        this.carList = carList;
        this.layoutInflater = layoutInflater;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = layoutInflater.inflate(resource, parent, false);
        Car testCar = carList.get(position);


        if(testCar != null) {

            TextView carName = view.findViewById(R.id.carName);
            String name = testCar.getBrand() + " " + testCar.getModel();
            carName.setText(name);

            TextView carDetails = view.findViewById(R.id.carDetails);
            String motor = testCar.getEngineCapacity() / 1000 + "." + testCar.getEngineCapacity() / 100 % 10;
            String details = motor + " " + testCar.getEngineOutput() + "hp " + testCar.getFuel();
            carDetails.setText(details);

            if (testCar.getExpDateRCA().after(current)) {
                Button btnRCA = view.findViewById(R.id.btn_RCA);
                btnRCA.setBackgroundResource(R.drawable.btn_green);

            } else {
                Button btnRCA = view.findViewById(R.id.btn_RCA);
//                btnRCA.setBackgroundResource(R.drawable.btn_red);
            }

            if(testCar.getExpDateITP().after(current)) {
                Button btnITP = view.findViewById(R.id.btn_ITP);
                btnITP.setBackgroundResource(R.drawable.btn_green);
            }  else {
                Button btnITP = view.findViewById(R.id.btn_ITP);
//                btnITP.setBackgroundResource(R.drawable.btn_red);
            }
            View card_bg = view.findViewById(R.id.card_bg);

            switch (testCar.getColor()){
                case "Blue":
                    card_bg.setBackgroundResource(R.drawable.blue_card);
                    break;
                case "Gray":
                    card_bg.setBackgroundResource(R.drawable.grey_card);
                    break;
                case "Green":
                    card_bg.setBackgroundResource(R.drawable.green_card);
                    break;
                case "Black":
                    card_bg.setBackgroundResource(R.drawable.black_card);
                    break;
                case "Red":
                    card_bg.setBackgroundResource(R.drawable.red_card);
                    break;
                case "Yellow":
                    card_bg.setBackgroundResource(R.drawable.yellow_card);
                    break;
                case "Purple":
                    card_bg.setBackgroundResource(R.drawable.purple_card);
                    break;
                case "White":
                    card_bg.setBackgroundResource(R.drawable.white_card);
                    TextView carNameText = view.findViewById(R.id.carName);
                    carNameText.setTextColor(Color.parseColor("#000000"));
                    TextView carDetailsText = view.findViewById(R.id.carDetails);
                    carDetailsText.setTextColor(Color.BLACK);
                    break;
            }

        }

        return view;
    }
}
