package com.example.autostudio;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RefillFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewTripFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RefillFragment newInstance(String param1, String param2) {
        RefillFragment fragment = new RefillFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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