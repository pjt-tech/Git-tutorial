package com.kye.mycinema;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class ListActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<ReviewItem> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView = findViewById(R.id.listView);
        Button contents_btn = findViewById(R.id.contents_btn);

        //Serializable을 통해 객체를 전달받음
        Intent intent = getIntent();
        list = (ArrayList<ReviewItem>)intent.getSerializableExtra("list");

        ListAdapter adapter = new ListAdapter(this);
        listView.setAdapter(adapter);


        contents_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public class ListAdapter extends BaseAdapter {

        Context context;


        public ListAdapter(Context context) {
            this.context = context;
            Collections.reverse(list);
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Reviewer reviewer = new Reviewer(context);
            ReviewItem items  = list.get(position);
            reviewer.setName(items.getName());
            reviewer.setContents(items.getContents());


            return reviewer;
        }
    }

}
