package com.example.saksh.imanews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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

public class PublicNewsAdapter extends RecyclerView.Adapter<PublicNewsAdapter.ViewHolder> {

    List<MyNews> myNews;
    Context context;

    View v;

    public PublicNewsAdapter(List<MyNews> myNews, Context context) {
        this.myNews = myNews;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card2,parent,false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(PublicNewsAdapter.ViewHolder holder, int position) {
        MyNews myNewsApp=myNews.get(position);
        String name="<b>UploadedBy: </b>"+myNewsApp.getUsername();
        String date1="<b>Date: </b>"+myNewsApp.getDateofNews();
        String place="<b>Place: </b>"+myNewsApp.getAddressofNews();
        holder.name.setText(Html.fromHtml(name));
        holder.title.setText(myNewsApp.getTitleOfNews());
        holder.desc.setText(myNewsApp.getDecpofNews());
        holder.date.setText(Html.fromHtml(date1));
        holder.upload.setText(Html.fromHtml(place));
        Picasso.with(context).load(myNewsApp.getUrlofNews()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return  myNews.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder
    {
        public TextView name,title,desc,date,upload,title1;
        public ImageView imageView;
        public  ViewHolder(View item)
        {
            super(item);
            name=(TextView)item.findViewById(R.id.publicName);
            title=(TextView)item.findViewById(R.id.publicTitle);
            desc=(TextView)item.findViewById(R.id.publicDesc);
            date=(TextView)item.findViewById(R.id.publicDate);
            upload=(TextView)item.findViewById(R.id.publicUpload);
            imageView=(ImageView)item.findViewById(R.id.publicImage);
        }



    }
}
