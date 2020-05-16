package com.kye.mycinema.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.kye.mycinema.R;
import com.kye.mycinema.data.ReviewItem;
import com.kye.mycinema.data.Reviewer;

import java.util.ArrayList;
import java.util.Collections;

        public class ListActivity extends AppCompatActivity {
            ListView listView;
            ArrayList<ReviewItem> list;
            ListAdapter adapter;
            EditText edt_Name,edt_Contents;
            String name,contents;
            AlertDialog.Builder builder;
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_list);
                listView = findViewById(R.id.listView);
                Button contents_btn = findViewById(R.id.contents_btn);

                Intent intent = getIntent();
                list = intent.getParcelableArrayListExtra("list");

                adapter = new ListAdapter(this,list);
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
        ArrayList<ReviewItem> list;

        public ListAdapter(Context context,ArrayList<ReviewItem> list) {
            this.context = context;
            Collections.reverse(list);
            this.list = list;
        }
        public void addItem(ReviewItem item){
            list.add(item);
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
