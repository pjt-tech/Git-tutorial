package com.kye.mycinema.activity;

import androidx.appcompat.app.AppCompatActivity;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.LinearLayout;
        import android.widget.ListView;
import android.widget.TextView;

import com.kye.mycinema.R;
        import com.kye.mycinema.data.MyListAdapter;
import com.kye.mycinema.data.ReviewItem;

import java.util.ArrayList;


public class ListActivity extends AppCompatActivity {

    ArrayList<ReviewItem> list;
    MyListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Button contents_btn = findViewById(R.id.contents_btn);
        LinearLayout container = findViewById(R.id.container);
        TextView txt_title = findViewById(R.id.txt_title);
        TextView txt_rating = findViewById(R.id.txt_rating);

        Intent intent = getIntent();
        list = intent.getParcelableArrayListExtra("list");
        String title = intent.getStringExtra("title");
        float rating = intent.getFloatExtra("rating",0);

        txt_title.setText(title);
        txt_rating.setText(String.valueOf(rating));

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.comment_list, container, true);
        ListView listView = viewGroup.findViewById(R.id.listView);

        adapter = new MyListAdapter(this,list);
        listView.setAdapter(adapter);


        contents_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
