package com.kye.mymovie;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    TextView yes,no;
    ImageView yesImage,noImage;
    Button contents_btn;
    int index = 0;
    boolean select = true;
    String name,contents;
    ArrayList<ReviewItem> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        yesImage = findViewById(R.id.yesImage);
        yes=findViewById(R.id.yesText);
        noImage = findViewById(R.id.noImage);
        no = findViewById(R.id.notext);
        listView = findViewById(R.id.listView);
        MyAdapter myAdapter = new MyAdapter(this);
        listView.setAdapter(myAdapter);
        Button all_btn = findViewById(R.id.all_btn);

         all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ListActivity.class);
                //Serializable전달형 Intent를 통해 전달받은 객체자료형 arraylist를 listActivity로 전달
                intent.putExtra("list",list);
                startActivityForResult(intent,20);
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
