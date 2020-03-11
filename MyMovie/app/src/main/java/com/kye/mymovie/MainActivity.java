package com.kye.mymovie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    TextView yes,no;
    ImageView yesImage,noImage;

    int index = 0;
    boolean select = true;

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
}
