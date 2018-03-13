package com.example.saksh.imanews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by saksh on 19-02-2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private List<NewsItem> NewsItems;
    private Context context;
    View v;
    public MyAdapter(List<NewsItem> NewsItems, Context context) {
        this.NewsItems = NewsItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewsItem itemNew=NewsItems.get(position);
        holder.title.setText(itemNew.getTitle());
        holder.Decription.setText(itemNew.getDecription());
        Picasso.with(context).load(itemNew.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return NewsItems.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder
    {
        public TextView title;
        public TextView Decription;
        public ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.newsTitle);
            Decription=(TextView)itemView.findViewById(R.id.newsDesc);
            imageView=(ImageView)itemView.findViewById(R.id.image1);
        }
    }

}
