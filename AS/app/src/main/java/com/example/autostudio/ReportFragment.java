package com.example.autostudio;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReportFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReportFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReportFragment newInstance(String param1, String param2) {
        ReportFragment fragment = new ReportFragment();
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
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        Bundle args = getArguments();
        assert args != null;
        String report = args.getString("BUTTON");

        TextView where1, where2;
        EditText editWhere1, editWhere2;
        Button showReport = view.findViewById(R.id.query_button);

        ListView reportList = view.findViewById(R.id.report_list);

        where1 = view.findViewById(R.id.text_where1);
        where2 = view.findViewById(R.id.text_where2);

        editWhere1 = view.findViewById(R.id.edit_where1);
        editWhere2 = view.findViewById(R.id.edit_where2);


        switch (report) {
            case "CAR":

                where1.setText(R.string.fuel);
                where2.setText(R.string.km_t);

                editWhere1.setHint(R.string.petrol);
                editWhere2.setHint(R.string.km_eg);

                break;
            case "EVENT":

                where1.setText("Action");
                where2.setText("Cost");

                editWhere1.setHint("Accident");
                editWhere2.setHint(R.string._95);

                break;
        }

        return view;
    }
}