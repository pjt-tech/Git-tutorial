package com.kye.mycinema;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    ArrayList<ReviewItem> item = new ArrayList<>();
    Context context;

    public MyAdapter(Context context) {
        this.context = context;
        item.add(new ReviewItem("dltkd12**","꿀잼보장, 다음에 한번 더 볼거에요"));
        item.add(new ReviewItem("kye5***","돈아까워요, 보지마세요"));
        item.add(new ReviewItem("pjt33**","간만에 재밌는 영화!, 추천해요"));

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
