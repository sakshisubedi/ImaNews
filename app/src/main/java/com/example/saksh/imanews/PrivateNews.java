package com.example.saksh.imanews;


import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PrivateNews extends Fragment {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    public StringRequest stringRequest;
    public RequestQueue requestQueue;
    public String url_String="https://newsapi.org/v2/top-headlines?sources=the-times-of-india&apiKey=6093aae6a87b4111a426e78547ca0dbb";
    public String sources;
    private List<NewsItem> newsItemsList;
    public PrivateNews() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_private_news, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sources="the-times-of-india";
        recyclerView=(RecyclerView)getView().findViewById(R.id.recycler);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsItemsList= new ArrayList<>();
        loadData();
    }
    public void loadData()
    {
        final ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();
        stringRequest=new StringRequest(Request.Method.GET, url_String, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("articles");
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject object=array.getJSONObject(i);
                        NewsItem item=new NewsItem(object.getString("title"),object.getString("description"),object.getString("urlToImage"));
                        Log.v("hello",item.getDecription());
                        newsItemsList.add(item);
                    }
                    adapter=new MyAdapter(newsItemsList,getContext());
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(),"Error:"+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}
