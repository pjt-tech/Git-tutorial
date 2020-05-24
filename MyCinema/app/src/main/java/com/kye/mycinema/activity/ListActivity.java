package com.kye.mycinema.activity;

import androidx.appcompat.app.AppCompatActivity;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
import android.widget.ImageView;
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
    String title;
    int grade,count;
    float rating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Button contents_btn = findViewById(R.id.contents_btn);
        LinearLayout container = findViewById(R.id.container);
        TextView txt_title = findViewById(R.id.txt_title);
        TextView txt_rating = findViewById(R.id.txt_rating);
        TextView txt_persons = findViewById(R.id.txt_persons);
        ImageView img_grade = findViewById(R.id.img_grade);

        Intent intent = getIntent();
        list = intent.getParcelableArrayListExtra("list");
        title = intent.getStringExtra("title");
        rating = intent.getFloatExtra("rating",0);
        count = intent.getIntExtra("count",0);
        grade =intent.getIntExtra("grade",0);

        if(grade==12){
            img_grade.setImageResource(R.drawable.ic_12);
        }else if(grade==15){
            img_grade.setImageResource(R.drawable.ic_15);
        }else if(grade==19){
            img_grade.setImageResource(R.drawable.ic_19);
        }

        txt_title.setText(title);
        txt_rating.setText(String.valueOf(rating));
        txt_persons.setText("("+count+"명 참여)");

        //한줄평 리스트뷰는 공유하기위해 인플레이션을 함
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.comment_list, container, true);
        ListView listView = viewGroup.findViewById(R.id.listView);

        adapter = new MyListAdapter(this,list);
        listView.setAdapter(adapter);


        contents_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ContentsActivity.class);
                intent.putExtra("title",title);
                intent.putExtra("grade",grade);
                startActivity(intent);
                finish();
            }
        });

    }
}
