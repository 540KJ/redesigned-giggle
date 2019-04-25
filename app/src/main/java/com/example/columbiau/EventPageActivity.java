package com.example.columbiau;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class EventPageActivity extends AppCompatActivity {

    public static String EXTRA_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.event);
        setContentView(R.layout.activity_event_page);
        EXTRA_URL = getIntent().getStringExtra("EXTRA_URL");

        Log.v("URL", EXTRA_URL);
        final WebView webView = findViewById(R.id.event_page_webview);
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setWebViewClient(new WebViewClient() {});

        webView.loadUrl(EXTRA_URL);
    }
}
