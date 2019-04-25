package com.example.columbiau;

import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    List<String> articleURLs = new ArrayList<>();
    List<String> imageURLs = new ArrayList<>();
    List<String> dates = new ArrayList<>();
    List<String> headings = new ArrayList<>();

    class RetrieveNewsFeed extends AsyncTask<Void, Void, Void> {
        protected void onPreExecute() {
            setTitle("News");
            setContentView(R.layout.activity_loading);
        }

        protected Void doInBackground(Void... args) {
            String url = "https://news.columbia.edu/content/news-archive";
            try {
                Document doc = Jsoup.connect(url).get();
                Elements newsHeadlines = doc.select("div.field.field--name-field-cu-date.field--type-datetime.field--label-hidden.field--item, span[property='schema:name'], h2 a[href]");
                Log.v("Headline", newsHeadlines.text());
                int count = 0;
                for (Element headline : newsHeadlines) {
                    if (headline.hasAttr("href") && count != 0) {
                        String articleURL = headline.attr("abs:href");
                        Document docTemp = Jsoup.connect(articleURL).get();
                        Element imgURLElement = docTemp.selectFirst("img[typeof='foaf:Image']");
                        String imgURL = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/aa/Butler_Library_Columbia_University.jpg/1200px-Butler_Library_Columbia_University.jpg";
                        if (imgURLElement != null) {
                            imgURL = imgURLElement.attr("abs:src");

                        }
                        else if (imgURLElement == null) {
                            Element thumbnailURLElement = docTemp.selectFirst("iframe[allow]");
                            if (thumbnailURLElement != null) {
                                String thumbnailURLElementString = thumbnailURLElement.attr("src");
                                String videoIndex = thumbnailURLElementString.substring(thumbnailURLElementString.lastIndexOf('/') + 1);
//                                Log.v("VideoIndex", videoIndex);
                                imgURL = "https://img.youtube.com/vi/" + videoIndex + "/maxresdefault.jpg";
                            }
                        }
//                        Log.v("ImgURL", imgURL);
                        imageURLs.add(imgURL);
                        articleURLs.add(articleURL);
//                        Log.v("Headline", articleURL);
                    }
                    else {
                        String headlineData = headline.text();
                        if (headline.hasAttr("property")) {
                            headings.add(headlineData);
                        }
                        else
                            dates.add(headlineData);
//                        Log.v("Headline", headlineData);
                    }
                    count++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void args) {
            setTitle("News");
            setContentView(R.layout.activity_news);
            RecyclerView newsRecycler = (RecyclerView) findViewById(R.id.news_recycler);
            newsRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            headings.removeAll(Arrays.asList("", null));
            Collections.reverse(articleURLs);

            NewsArticleAdapter adapter = new NewsArticleAdapter(headings, imageURLs);
            newsRecycler.setAdapter(adapter);

            adapter.setListener(new NewsArticleAdapter.Listener() {
                public void onClick(int position) {
                    Intent intent = new Intent(getApplicationContext(), NewsArticleActivity.class);
                    intent.putExtra("EXTRA_URL", articleURLs.get(position));
                    startActivity(intent);
                }
            });
            Log.v("OnPostExecute", "ExecutedTwo");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new RetrieveNewsFeed().execute();
    }
}
