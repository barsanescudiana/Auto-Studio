package com.example.autostudio;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewTripFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewTripFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView close;
    private Spinner car;
    DatabaseAutoStudio databaseAutoStudio;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewTripFragment() {
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
    public static NewTripFragment newInstance(String param1, String param2) {
        NewTripFragment fragment = new NewTripFragment();
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_new_trip, container, false);

        databaseAutoStudio = DatabaseAutoStudio.getInstance(this.getContext());
        car = view.findViewById(R.id.refill_car_spinner);

        ArrayList<Car> cars = (ArrayList<Car>) databaseAutoStudio.getCarsDao().getAll();
        SpinnerAdapter adapter = new SpinnerAdapter(this.getContext(), R.layout.spinner_item, cars, getLayoutInflater());

        car.setAdapter(adapter);

        close = view.findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(NewTripFragment.this).commit();
            }
        });

        return view;
    }
}