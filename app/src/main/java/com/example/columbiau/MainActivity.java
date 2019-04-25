package com.example.columbiau;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout newsLinearLayout = (LinearLayout) findViewById(R.id.news_group);
        newsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newsIntent = new Intent(getApplicationContext(), NewsActivity.class);
                startActivity(newsIntent);
            }
        });

        LinearLayout courseCatalogLinearLayout = (LinearLayout) findViewById(R.id.course_group);
        courseCatalogLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent courseIntent = new Intent(getApplicationContext(), CourseActivity.class);
                startActivity(courseIntent);
            }
        });

        LinearLayout eventsLinearLayout = (LinearLayout) findViewById(R.id.events_group);
        eventsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent eventsIntent = new Intent(getApplicationContext(), EventsActivity.class);
                startActivity(eventsIntent);
            }
        });

        LinearLayout diningLinearLayout = (LinearLayout) findViewById(R.id.dining_group);
        diningLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent diningIntent = new Intent(getApplicationContext(), WebViewActivity.class);
                diningIntent.putExtra("EXTRA_URL", "https://dining.columbia.edu/");
                startActivity(diningIntent);
            }
        });

        LinearLayout housingLinearLayout = findViewById(R.id.housing_group);
        housingLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent housingIntent = new Intent(getApplicationContext(), WebViewActivity.class);
                housingIntent.putExtra("EXTRA_URL", "https://housing.columbia.edu/");
                startActivity(housingIntent);
            }
        });

        LinearLayout bookingLinearLayout = findViewById(R.id.classroom_group);
        bookingLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookingIntent = new Intent(getApplicationContext(), WebViewActivity.class);
                bookingIntent.putExtra("EXTRA_URL", "https://ems.cuit.columbia.edu/EmsWebApp/");
                startActivity(bookingIntent);
            }
        });

        LinearLayout libraryLinearLayout = findViewById(R.id.library_group);
        libraryLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent libraryIntent = new Intent(getApplicationContext(), WebViewActivity.class);
                libraryIntent.putExtra("EXTRA_URL", "https://library.columbia.edu/");
                startActivity(libraryIntent);
            }
        });

        LinearLayout buildingLinearLayout = findViewById(R.id.building_services_group);
        buildingLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookingIntent = new Intent(getApplicationContext(), WebViewActivity.class);
                bookingIntent.putExtra("EXTRA_URL", "https://facilities.columbia.edu/");
                startActivity(bookingIntent);
            }
        });

        LinearLayout studentServiceLinearLayout = findViewById(R.id.student_services_group);
        studentServiceLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent studentServiceIntent = new Intent(getApplicationContext(), WebViewActivity.class);
                studentServiceIntent.putExtra("EXTRA_URL", "https://socialwork.columbia.edu/student-experience/student-services/");
                startActivity(studentServiceIntent);
            }
        });

    }
}
