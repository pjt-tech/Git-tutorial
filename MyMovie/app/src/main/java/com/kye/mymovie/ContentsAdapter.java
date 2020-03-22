package com.kye.mymovie;

import android.content.Context;
import android.content.Intent;
import android.icu.text.Transliterator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class ContentsAdapter extends BaseAdapter {

    ArrayList<ReviewItem> item;
    Context context;


    public ContentsAdapter(Context context,ArrayList item) {
        this.context = context;
        this.item = item;
        Collections.reverse(item);
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
        Reviewer reviewer = new Reviewer(context);
        ReviewItem items  = item.get(position);
        reviewer.setName(items.getName());
        reviewer.setContents(items.getContents());


        return reviewer;
    }
}
