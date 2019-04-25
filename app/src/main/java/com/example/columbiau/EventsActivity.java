package com.example.columbiau;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class EventsActivity extends AppCompatActivity {

    Map<String, String> map = new HashMap<String, String>();
    Object[] mapKeys;
    int max;

    public EventsActivity() {
        map.put("Bacchantae", "https://m.facebook.com/Bacchantae/events");
        map.put("Columbia Biomedical Engineering Society", "https://m.facebook.com/ColumbiaBMES/events");
        map.put("Columbia Data Science Society", "https://m.facebook.com/cdsscu/events");
        map.put("Columbia University Gospel Choir", "https://m.facebook.com/CUGospel/events");
        map.put("Asian American Alliance", "https://m.facebook.com/ColumbiaAAA/events");
        map.put("Armenian Society of Columbia University", "https://m.facebook.com/ArmenianSocietyColumbia/events");
        map.put("African Students Association", "https://m.facebook.com/asacolumbia/events");
        map.put("Columbia Korea Students Association", "https://m.facebook.com/ColumbiaKSA/events");
        map.put("Columbia Earth Day Coalition", "https://m.facebook.com/ColumbiaEarthDay/events");
        map.put("Columbia Undergraduate Film Productions", "https://m.facebook.com/CUFilmProductions/events");
        map.put("Columbia Classical Performers", "https://m.facebook.com/columbiaclassicalperformers/events");
        map.put("Music Performance Program at Columbia University", "https://m.facebook.com/MPPColumbia/events");
//        map.put("Columbia Undergraduate Math Society", "http://www.math.columbia.edu/~ums/");
        mapKeys = map.keySet().toArray();
        max = mapKeys.length-1;
    }

    int positionCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
//        getApplicationContext().deleteDatabase("Database");
        Log.v("Help", Integer.toString(max));
        Intent intent = new Intent(getApplicationContext(), GatherEventsActivity.class);
        intent.putExtra("EXTRA_URL", map.get(mapKeys[positionCount]));
        intent.putExtra("EXTRA_CLUB_NAME", mapKeys[positionCount].toString());
        startActivityForResult(intent, positionCount);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (positionCount < max) {
            positionCount = requestCode + 1;
            Intent intent = new Intent(getApplicationContext(), GatherEventsActivity.class);
            intent.putExtra("EXTRA_CLUB_NAME", mapKeys[positionCount].toString());
            intent.putExtra("EXTRA_URL", map.get(mapKeys[positionCount]));
            startActivityForResult(intent, positionCount);
            overridePendingTransition(0, 0);
        } else {
            try {
                SQLiteOpenHelper eventsDatabaseHelper = new DatabaseHelper(getApplicationContext());
                SQLiteDatabase db = eventsDatabaseHelper.getReadableDatabase();
                Cursor cursor = db.query("EVENTS", new String[] {"EVENTNAME", "DATE", "TIME", "PLACE", "URLLINK", "CLUB", "DOMAIN"}, null, null, null, null, "DATE ASC, TIME");
                setTitle(R.string.events);
                setContentView(R.layout.activity_events);
                RecyclerView eventRecycler = findViewById(R.id.events_recycler);
                eventRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),
                        DividerItemDecoration.HORIZONTAL);
                eventRecycler.addItemDecoration(dividerItemDecoration);

                final EventsAdapter eventsAdapter = new EventsAdapter(cursor);
                eventRecycler.setAdapter(eventsAdapter);

                cursor.close();
                db.close();

                eventsAdapter.setListener(new EventsAdapter.Listener() {
                    public void onClick(int position) {
                        Intent intent = new Intent(getApplicationContext(), EventPageActivity.class);
                        intent.putExtra("EXTRA_URL", String.format("https://%s%s", eventsAdapter.domains.get(position), eventsAdapter.urls.get(position)));
                        startActivity(intent);
                    }
                });
            } catch (SQLiteException e) {
                Toast toast = Toast.makeText(this, "Database Unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
