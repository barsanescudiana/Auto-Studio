package com.example.autostudio.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.autostudio.R;

import java.util.ArrayList;

public class ReportAdapter extends BaseAdapter {

    private final ArrayList<String> list;
    private final Context context;
    private final int resource;
    private final LayoutInflater layoutInflater;

    public ReportAdapter(@NonNull Context context, int resource, ArrayList<String> list, LayoutInflater layoutInflater) {
        this.context = context;
        this.resource = resource;
        this.list = list;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(resource, parent, false);

        String text = list.get(position);

        if(text != "") {
            TextView textView = (TextView) view.findViewById(R.id.text_item);
            textView.setText(text);
        }

        return view;
    }
}
