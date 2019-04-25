package com.example.columbiau;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsArticleActivity extends AppCompatActivity {

    public static String EXTRA_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.story);
        setContentView(R.layout.activity_news_article);
        EXTRA_URL = getIntent().getStringExtra("EXTRA_URL");

        final WebView webView = (WebView) findViewById(R.id.article_webview);
        WebSettings webSettings = webView.getSettings();
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                webView.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                webView.loadUrl("javascript:(function() { " +
                        "document.getElementsByTagName('header')[0].style.display='none'; " +
                        "document.querySelector('div#branding').style.display='none'; " +
                        "document.querySelector('div.container.visible-xs-block').style.display='none'; " +
                        "document.querySelector('div#header-xs').style.display='none'; " +
                        "document.getElementsByClassName('cu-profile-admin node-cu_article path-node page-node-type-cu-article has-glyphicons')[0].style.setProperty('padding-top', '0px', '!important'); " +
                        "document.querySelector('nav#breadcrumb').style.display='none'; " +
                        "document.getElementsByClassName('views-element-container views_block recent_news-block_2 block clearfix')[0].style.display='none'; " +
                        "})()");
                webView.setVisibility(View.VISIBLE);
            }
        });
        webView.loadUrl(EXTRA_URL);

    }
}

