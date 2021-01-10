package com.example.autostudio.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.autostudio.DatabaseAutoStudio;
import com.example.autostudio.R;
import com.example.autostudio.adapters.SpinnerAdapter;
import com.example.autostudio.classes.Car;

import java.util.ArrayList;

public class RefillFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Spinner car;
    SharedPreferences preferences;

    DatabaseAutoStudio databaseAutoStudio;

    private TextView close;
    private Button done;
    private EditText liters;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RefillFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        preferences = this.getActivity().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_refill, container, false);

        databaseAutoStudio = DatabaseAutoStudio.getInstance(view.getContext());

        close = view.findViewById(R.id.close);
        car = view.findViewById(R.id.refill_car_spinner);
        liters = (EditText) view.findViewById(R.id.refill_liters);

        ArrayList<Car> cars = (ArrayList<Car>) databaseAutoStudio.getCarsDao().getAll();
        SpinnerAdapter adapter = new SpinnerAdapter(this.getContext(), R.layout.spinner_item, cars, getLayoutInflater());

        car.setAdapter(adapter);

        preferences = this.getActivity().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        car.setSelection(preferences.getInt("CAR_INDEX", 0));

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction().remove(RefillFragment.this).commit();
            }
        });

        done = view.findViewById(R.id.button_done);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Car selected = (Car) car.getSelectedItem();
                Double l = selected.getTankCapacity() + Double.parseDouble(liters.getText().toString());

                databaseAutoStudio.getCarsDao().updateCarTankCapacityById(l, selected.getCarId());

                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction().remove(RefillFragment.this).commit();
            }
        });

        String gasStation = preferences.getString("GAS_STATION", "");
        EditText gas = view.findViewById(R.id.gas_station_refill);
        gas.setText(gasStation);
        
        return view;
    }
}