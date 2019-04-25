package com.example.columbiau;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class GatherEventsActivity extends AppCompatActivity {

    public static String EXTRA_URL;
    public final String CURRENT_YEAR = "2019";
    public String CLUB_NAME;
    String domainName = "www.columbia.edu";
    Map<String, String> months = new HashMap<String, String>() {{
        put("JAN", "01");
        put("FEB", "02");
        put("MAR", "03");
        put("APR", "04");
        put("MAY", "05");
        put("JUN", "06");
        put("JUL", "07");
        put("AUG", "08");
        put("SEP", "09");
        put("OCT", "10");
        put("NOV", "11");
        put("DEC", "12");
    }};

    private String convertDateNameToNumeric(String dateName) {
        String month = dateName.substring(0, 3);
        String numericMonth = months.get(month);
        String day = dateName.substring(3);
        return String.format("%s/%s/%s", numericMonth, day, CURRENT_YEAR);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        EXTRA_URL = getIntent().getStringExtra("EXTRA_URL");
        CLUB_NAME = getIntent().getStringExtra("EXTRA_CLUB_NAME");
        scrapeData(EXTRA_URL, CLUB_NAME);
    }

    public static boolean checkRecordDbExists(SQLiteDatabase db, String tableName,
                                                      String dbField, String fieldValue) {
        String Query = "SELECT * FROM " + tableName + " WHERE " + dbField + " = '" + fieldValue + "'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public void scrapeData(String url, final String clubName) {

        final WebView webView = new WebView(getApplicationContext());

        try {
            URL aURL = new URL(EXTRA_URL);
            domainName = aURL.getHost();
            Log.v("domainName", domainName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        class HtmlHandler {
            @JavascriptInterface
            public void facebookHandleHtml(String html) {
                Document doc = Jsoup.parse(html, "", Parser.htmlParser());
                Elements events = doc.select("div._5zma div._2x2s");
                //Log.v("Events", "Here");
                //Log.v("EventsToString", events.toString());
                SQLiteOpenHelper eventsDatabaseHelper = new DatabaseHelper(getApplicationContext());
                SQLiteDatabase db = eventsDatabaseHelper.getWritableDatabase();
                for (Element event : events) {
                    String date = convertDateNameToNumeric(event.select("span._1e38._2-xr").text());
                    String eventName = event.select("h3._592p._r-i").text();
                    String time = event.select("span[title]").text();
                    String place = event.select("div._52jc._5d19 span[class]:not(:has(span[title]))").text();
                    String urlLink = event.select("a[class='_5379']").attr("href");
//                    Log.v("URLEVENT", urlLink);

                    if (!checkRecordDbExists(db, "EVENTS", "EVENTNAME", eventName)) {
//                        Log.v("Checked", "Checked");
                        ContentValues eventValues = new ContentValues();
                        eventValues.put("DATE", date);
                        eventValues.put("TIME", time);
                        eventValues.put("EVENTNAME", eventName);
                        eventValues.put("PLACE", place);
                        eventValues.put("URLLINK", urlLink);
                        eventValues.put("CLUB", clubName);
                        eventValues.put("DOMAIN", domainName);
                        db.insert("EVENTS", null, eventValues);
                    }
                }
                db.close();

                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                returnIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
            }
        }

        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setJavaScriptEnabled(true);
        HtmlHandler save = new HtmlHandler();
        webView.addJavascriptInterface(save, "HtmlHandler");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageCommitVisible(WebView view, String url) {
                webView.loadUrl("javascript:HtmlHandler.facebookHandleHtml(document.documentElement.outerHTML);");
            }
        });
        webView.loadUrl(url);

    }
}
