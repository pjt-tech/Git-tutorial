package com.kye.mycinema.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.kye.mycinema.data.AppHelper;
import com.kye.mycinema.data.CommentList;
import com.kye.mycinema.data.MovieInfo;
import com.kye.mycinema.data.ReviewItem;
import com.kye.mycinema.data.Reviewer;

import java.util.ArrayList;

public class MyListAdapter extends BaseAdapter {

    ArrayList<ReviewItem> item;
    Context context;

    public MyListAdapter(){

    }

    public MyListAdapter(Context context,ArrayList list){
        this.context = context;
        this.item = list;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //view가 이미 있으면 재활용하겠다 리싸이클러뷰와 유사(convertView)
        Reviewer reviewer = null;
        if(convertView==null){
            reviewer = new Reviewer(context);
        }else{
            reviewer = (Reviewer)convertView;
        }
            ReviewItem items = item.get(position);
            reviewer.setName(items.getName());
            reviewer.setContents(items.getContents());
            reviewer.setTime(items.getTime());
        return reviewer;
    }
}

