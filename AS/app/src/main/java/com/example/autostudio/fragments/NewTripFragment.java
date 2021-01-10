package com.example.autostudio.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
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

public class NewTripFragment extends DialogFragment {

    private TextView close;
    private Spinner car;
    private Button add;
    DatabaseAutoStudio databaseAutoStudio;

    EditText kilometers;

    SharedPreferences preferences;

    public NewTripFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_trip, container, false);

        databaseAutoStudio = DatabaseAutoStudio.getInstance(this.getContext());
        car = view.findViewById(R.id.trip_car_spinner);

        kilometers = (EditText) view.findViewById(R.id.trip_km);

        ArrayList<Car> cars = (ArrayList<Car>) databaseAutoStudio.getCarsDao().getAll();
        SpinnerAdapter adapter = new SpinnerAdapter(this.getContext(), R.layout.spinner_item, cars, getLayoutInflater());

        car.setAdapter(adapter);

        preferences = this.getActivity().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        car.setSelection(preferences.getInt("CAR_INDEX", 0));

        close = view.findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(NewTripFragment.this).commit();
            }
        });

        add = view.findViewById(R.id.button_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Car selected = (Car) car.getSelectedItem();
                Double km = selected.getKm() + Double.parseDouble(kilometers.getText().toString());
                Double tank = selected.getTankCapacity() - (Double.parseDouble(kilometers.getText().toString()) * selected.getAvgConsumption() / 100);

                databaseAutoStudio.getCarsDao().updateCarKmById(km, selected.getCarId());
                databaseAutoStudio.getCarsDao().updateCarTankCapacityById(tank, selected.getCarId());

                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction().remove(NewTripFragment.this).commit();
            }
        });

        return view;
    }
}