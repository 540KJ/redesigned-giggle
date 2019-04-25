package com.example.columbiau;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class NewsArticleAdapter extends RecyclerView.Adapter<NewsArticleAdapter.ViewHolder> {

    private List<String> captions;
    private Listener listener;
    private List<String> images;

    interface Listener {
        void onClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    public NewsArticleAdapter(List<String> captions, List<String> images) {
        Collections.reverse(captions);
        Collections.reverse(images);
        this.captions = captions;
        this.images = images;
    }

    @Override
    public int getItemCount() {
        return captions.size();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public NewsArticleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView mcv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_news, parent, false);
        return new ViewHolder(mcv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.image_itself);
        Picasso.get().load(images.get(position)).into(imageView);
        TextView textView = (TextView) cardView.findViewById(R.id.image_caption);
        textView.setText(captions.get(position));

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
