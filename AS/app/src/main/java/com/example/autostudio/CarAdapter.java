package com.example.autostudio;

import android.content.Context;
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

    private Context context;
    private int resource;
    private ArrayList<Car> carList;
    private LayoutInflater layoutInflater;

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

            TextView carName = (TextView) view.findViewById(R.id.carName);
            String name = testCar.getBrand() + " " +testCar.getModel();
            carName.setText(name);

            TextView carDetails = (TextView) view.findViewById(R.id.carDetails);
            String motor = String.valueOf(testCar.getEngineCapacity()/1000) + "." + String.valueOf(testCar.getEngineCapacity()/100%10);
            String details = motor + " " + testCar.getEngineOutput() + "hp " + testCar.getFuel();
            carDetails.setText(details);

            if (testCar.getExpDateRCA().after(current)) {
                Button btnRCA = (Button) view.findViewById(R.id.btn_RCA);
                btnRCA.setBackgroundResource(R.drawable.btn_green);

            } else {
                Button btnRCA = (Button) view.findViewById(R.id.btn_RCA);
                btnRCA.setBackgroundResource(R.drawable.btn_red);
            }

            if(testCar.getExpDateITP().after(current)) {
                Button btnITP = (Button) view.findViewById(R.id.btn_ITP);
                btnITP.setBackgroundResource(R.drawable.btn_green);
            }  else {
                Button btnITP = (Button) view.findViewById(R.id.btn_ITP);
                btnITP.setBackgroundResource(R.drawable.btn_red);
            }

        }

        return view;
    }
}
