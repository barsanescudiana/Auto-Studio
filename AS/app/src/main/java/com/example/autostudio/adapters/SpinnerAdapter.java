package com.example.autostudio.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.autostudio.classes.Car;
import com.example.autostudio.R;

import java.util.ArrayList;

public class SpinnerAdapter extends BaseAdapter {

    ArrayList<Car> arrayList;
    Context context;
    LayoutInflater layoutInflater;
    int resources;

    public SpinnerAdapter(Context context, int resource, ArrayList<Car> arrayList, LayoutInflater layoutInflater) {
        this.context = context;
        this.resources = resource;
        this.layoutInflater = layoutInflater;
        this.arrayList = arrayList;

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Car getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.spinner_item, null);

        TextView textView = view.findViewById(R.id.spinner_text);
        textView.setText(arrayList.get(position).getDetails());

        return view;
    }
}
