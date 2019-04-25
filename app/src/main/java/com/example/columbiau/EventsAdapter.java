package com.example.columbiau;

import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private List<String> dates = new ArrayList<>();
    private List<String> eventNames = new ArrayList<>();
    private List<String> times = new ArrayList<>();
    private List<String> location = new ArrayList<>();
    private List<String> clubNames = new ArrayList<>();
    public List<String> urls = new ArrayList<>();
    public List<String> domains = new ArrayList<>();
    private Listener listener;

    Map<String, String> months = new HashMap<String, String>() {{
        put("01", "JAN");
        put("02", "FEB");
        put("03", "MAR");
        put("04", "APR");
        put("05", "MAY");
        put("06", "JUN");
        put("07", "JUL");
        put("08", "AUG");
        put("09", "SEP");
        put("10", "OCT");
        put("11", "NOV");
        put("12", "DEC");

    }};


    interface Listener {
        void onClick(int positon);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    public EventsAdapter(Cursor cursor) {
        while (cursor.moveToNext()) {
            Log.v("SQLITE", String.format("%s %s %s %s %s", cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
            eventNames.add(cursor.getString(0));
            dates.add(convertDateToRelevant(cursor.getString(1)));
            times.add(cursor.getString(2));
            location.add(cursor.getString(3));
            urls.add(cursor.getString(4));
            clubNames.add(cursor.getString(5));
            domains.add(cursor.getString(6));
        }
    }

    public String convertDateToRelevant(String date) {
        String[] parts = date.split("/");
        String numericDate = parts[0];
        // Careful: days may be 1 instead of 01
        String numericDay = parts[1];
        String alphaDate = months.get(numericDate).toString();
        return String.format("%s %s", alphaDate, numericDay);
    }

    @Override
    public int getItemCount() {
        return eventNames.size();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_events, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CardView cardView = holder.cardView;
        TextView dateTextView = (TextView) cardView.findViewById(R.id.event_date);
        dateTextView.setText(dates.get(position));
        TextView nameTextView = cardView.findViewById(R.id.event_name);
        nameTextView.setText(eventNames.get(position));
        TextView clubTextView = cardView.findViewById(R.id.club_name);
        clubTextView.setText(clubNames.get(position));
        TextView timeTextView = cardView.findViewById(R.id.event_time);
        timeTextView.setText(times.get(position));
        TextView locationTextView = cardView.findViewById(R.id.event_place);
        locationTextView.setText(location.get(position));

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });

    }
}
