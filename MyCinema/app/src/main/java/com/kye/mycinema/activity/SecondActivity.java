package com.kye.mycinema.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.kye.mycinema.adapter.MyListAdapter;
import com.kye.mycinema.R;
import com.kye.mycinema.data.ReviewItem;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    ListView listView;
    TextView yes,no,title_txt;
    ImageView yesImage,noImage,mainImage;
    Button contents_btn,all_btn;
    int index = 0;
    boolean select = true;
    String name,contents;
    ArrayList<ReviewItem> list = new ArrayList<>();
    FrameLayout frame;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("영화 상세");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.gong_drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {};
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger_menu);


        frame = findViewById(R.id.frame);
        title_txt = findViewById(R.id.title_txt);
        mainImage = findViewById(R.id.mainImage);
        yesImage = findViewById(R.id.yesImage);
        yes=findViewById(R.id.yesText);
        noImage = findViewById(R.id.noImage);
        no = findViewById(R.id.notext);

        NavigationView navigationView = findViewById(R.id.gong_drawerView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.nav_list){
                    finish();
                }

                return true;
            }
        });

        listView = findViewById(R.id.listView1);
        MyListAdapter myAdapter1 = new MyListAdapter(this);
        listView.setAdapter(myAdapter1);

        all_btn = findViewById(R.id.all_btn);
        all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putParcelableArrayListExtra("list",list);
                startActivity(intent);
            }
        });

        contents_btn = findViewById(R.id.contents_btn);
        contents_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContentsActivity.class);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
