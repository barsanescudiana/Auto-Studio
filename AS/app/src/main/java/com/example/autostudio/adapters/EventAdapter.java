package com.example.autostudio.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.autostudio.classes.Event;
import com.example.autostudio.R;

public class EventAdapter extends BaseAdapter {

    private final Context context;
    private final int resource;
    private final ArrayList<Event> eventsList;
    private final LayoutInflater layoutInflater;

    public EventAdapter(@NonNull Context context, int resource, ArrayList<Event> eventsList, LayoutInflater layoutInflater) {
        this.context = context;
        this.resource = resource;
        this.eventsList = eventsList;
        this.layoutInflater = layoutInflater;
    }

    private Calendar toCalendar(long timestamp)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = layoutInflater.inflate(resource, parent, false);
        Event testEvent = eventsList.get(position);

        if(testEvent != null) {
            TextView eventName = view.findViewById(R.id.event_name);
            String name = testEvent.getName();
            eventName.setText(name);

            TextView eventDate = view.findViewById(R.id.event_date);
            String date = testEvent.getDate().toString();
            eventDate.setText(date);

            Calendar testDate = toCalendar(testEvent.getDate().getTime());

            TextView daysNo = view.findViewById(R.id.days_ago);
            long msDiff = Calendar.getInstance().getTimeInMillis() - testDate.getTimeInMillis();
            long daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);
            String days = String.valueOf(daysDiff) + " days ago";
            daysNo.setText(days);
        }

        return view;
    }

    @Override
    public int getCount() {
        return eventsList.size();
    }

    @Override
    public Object getItem(int position) {
        return eventsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
