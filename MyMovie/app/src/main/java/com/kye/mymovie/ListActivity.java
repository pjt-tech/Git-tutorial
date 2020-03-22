package com.kye.mymovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

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

        ContentsAdapter adapter = new ContentsAdapter(this,list);
        listView.setAdapter(adapter);


        contents_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
