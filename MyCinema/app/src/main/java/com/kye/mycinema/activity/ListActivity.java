package com.kye.mycinema.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.kye.mycinema.R;
import com.kye.mycinema.adapter.MyListAdapter;


    public class ListActivity extends AppCompatActivity {

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_list);
                Button contents_btn = findViewById(R.id.contents_btn);
                LinearLayout container = findViewById(R.id.container);

                LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ViewGroup viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.comment_list, container, true);
                ListView listView = viewGroup.findViewById(R.id.listView);

                MyListAdapter adapter = new MyListAdapter(this);

                listView.setAdapter(adapter);

        contents_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
