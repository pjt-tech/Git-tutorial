package com.kye.mycinema;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    ListView listView;
    TextView yes,no,title_txt;
    ImageView yesImage,noImage,mainImage;
    Button contents_btn,all_btn;
    int index = 0;
    boolean select = true;
    String name,contents;
    ArrayList<ReviewItem> list = new ArrayList<>();
    FrameLayout frame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        frame = findViewById(R.id.frame);
        title_txt = findViewById(R.id.title_txt);
        mainImage = findViewById(R.id.mainImage);
        yesImage = findViewById(R.id.yesImage);
        yes=findViewById(R.id.yesText);
        noImage = findViewById(R.id.noImage);

        no = findViewById(R.id.notext);
        listView = findViewById(R.id.listView);
        MyAdapter myAdapter = new MyAdapter(this);
        listView.setAdapter(myAdapter);

        Intent intent = getIntent();
        int idx = intent.getIntExtra("id",0);
        if(idx == 1){
            setContentView(R.layout.gong);
            ListView listView1 = findViewById(R.id.listView1);
            MyAdapter myAdapter1 = new MyAdapter(this);
            listView1.setAdapter(myAdapter1);
        }else  if(idx == 2){
            setContentView(R.layout.king);
            ListView listView2 = findViewById(R.id.listView2);
            MyAdapter myAdapter2 = new MyAdapter(this);
            listView2.setAdapter(myAdapter2);
        }else  if(idx == 3){
            setContentView(R.layout.evil);
            ListView listView3 = findViewById(R.id.listView3);
            MyAdapter myAdapter3 = new MyAdapter(this);
            listView3.setAdapter(myAdapter3);
        }else  if(idx == 4){
            setContentView(R.layout.luck);
            ListView listView4 = findViewById(R.id.listView4);
            MyAdapter myAdapter4 = new MyAdapter(this);
            listView4.setAdapter(myAdapter4);
        }else  if(idx == 5){
            setContentView(R.layout.asura);
            ListView listView5 = findViewById(R.id.listView5);
            MyAdapter myAdapter5 = new MyAdapter(this);
            listView5.setAdapter(myAdapter5);
        }

        all_btn = findViewById(R.id.all_btn);
        all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("list",list);
                startActivity(intent);
            }
        });

        contents_btn = findViewById(R.id.contents_btn);
        contents_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ContentsActivity.class);
                startActivityForResult(intent,10);
            }
        });


        yesImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(yes.getText().toString());
                int num1 = Integer.parseInt(no.getText().toString());
                if(index == 0) {
                    yesImage.setImageResource(R.drawable.ic_thumb_up_selected);
                    yes.setText(String.valueOf(num + 1));
                    index = 1;
                }else if(index == 1){
                    yesImage.setImageResource(R.drawable.ic_thumb_up);
                    yes.setText(String.valueOf(num - 1));
                    index = 0;
                }if(select == false){
                    yesImage.setImageResource(R.drawable.ic_thumb_up_selected);
                    yes.setText(String.valueOf(num + 1));
                    noImage.setImageResource(R.drawable.ic_thumb_down);
                    no.setText(String.valueOf(num1 - 1));
                    select = true;
                }
            }
        });

        noImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(no.getText().toString());
                int num1 = Integer.parseInt(yes.getText().toString());
                if (select == true) {
                    noImage.setImageResource(R.drawable.ic_thumb_down_selected);
                    no.setText(String.valueOf(num+1));
                    select = false;
                }else if(select == false) {
                    noImage.setImageResource(R.drawable.ic_thumb_down);
                    no.setText(String.valueOf(num-1));
                    select = true;
                }if(index==1){
                    noImage.setImageResource(R.drawable.ic_thumb_down_selected);
                    no.setText(String.valueOf(num+1));
                    yesImage.setImageResource(R.drawable.ic_thumb_up);
                    yes.setText(String.valueOf(num1 - 1));
                    index = 0;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10 && resultCode==RESULT_OK){
            this.name = data.getStringExtra("name");
            this.contents = data.getStringExtra("contents");
            Toast.makeText(getApplicationContext(), "저장된 아이디 : "+name+" , 저장된 내용 : " + contents, Toast.LENGTH_LONG).show();
            //onActivityResult를 통해 값을 전달받아 arraylist에 더함
            list.add(new ReviewItem(name,contents));
        }
    }
}
