package com.kye.mycinema.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.kye.mycinema.adapter.MyListAdapter;
import com.kye.mycinema.R;
import com.kye.mycinema.data.AppHelper;
import com.kye.mycinema.data.DetailInfo;
import com.kye.mycinema.data.DetailLIst;
import com.kye.mycinema.data.ImageLoadTask;
import com.kye.mycinema.data.MovieInfo;
import com.kye.mycinema.data.ReviewItem;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    ArrayList<ReviewItem> list = new ArrayList<>();
    TextView yes,no,title_txt,txt_rate,txt_rating,audience,genre,date,synopsis,director,actor; //웹에서 받아온 내용 적용할 상세화면 텍스트뷰들
    ImageView yesImage,noImage,mainImage;
    Button contents_btn,all_btn;
    ProgressBar pb;
    FrameLayout frame;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    int mainIndex;
    int index = 0;
    boolean select = true;
    String name,contents;
    LinearLayout container;
    MyListAdapter myAdapter;
    ViewGroup viewGroup;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //region findViewById
        txt_rate = findViewById(R.id.txt_rate);
        title_txt = findViewById(R.id.title_txt);
        mainImage = findViewById(R.id.mainImage);
        yesImage = findViewById(R.id.yesImage);
        yes=findViewById(R.id.yesText);
        noImage = findViewById(R.id.noImage);
        txt_rating = findViewById(R.id.txt_rating);
        audience = findViewById(R.id.audience);
        genre = findViewById(R.id.genre);
        date = findViewById(R.id.date);
        synopsis = findViewById(R.id.synopsis);
        director = findViewById(R.id.director);
        actor = findViewById(R.id.actor);
        no = findViewById(R.id.notext);
        pb = findViewById(R.id.progressBar);
        frame = findViewById(R.id.frame);

        Toolbar toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.detail_drawer);
        NavigationView navigationView = findViewById(R.id.main_drawerView);
        container = findViewById(R.id.container);
        //endregion

        Intent intent = getIntent();
        mainIndex = intent.getIntExtra("index",0);

        requestMovieList();
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.comment_list, container, true);
        listView = viewGroup.findViewById(R.id.listView);

        toolbar.setTitle("영화 상세");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {};
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger_menu);

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

        //region like
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
        //endregion
    }

    //region request

    //메인액티비티와 동일 상세화면으로 웹서버에서 아래 url에 있는 Json데이터를 요청하고 받아와서 Gson으로 파싱하여 텍스트뷰에 설정
        public void requestMovieList(){
            String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readMovie";
            url += "?" + "id="+(mainIndex+1);

            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            myAdapter = new MyListAdapter(getApplicationContext(),mainIndex);
                            listView.setAdapter(myAdapter);
                            processResponse(response);

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
            );
            request.setShouldCache(false);
            AppHelper.requestQueue.add(request);
        }
//endregion


    //region response
        public void processResponse(String response){

                Gson gson = new Gson();
                DetailLIst detailLIst = gson.fromJson(response, DetailLIst.class);
                DetailInfo detailInfo = detailLIst.result.get(0);
                title_txt.setText(detailInfo.id + "." + detailInfo.title);
                date.setText(detailInfo.date + " 개봉");
                genre.setText(detailInfo.genre + "/" + detailInfo.duration + "분");
                txt_rate.setText(detailInfo.reservation_rate + "%");
                txt_rating.setText(String.valueOf(detailInfo.audience_rating));
                audience.setText(String.valueOf(detailInfo.audience) + "명");
                yes.setText(String.valueOf(detailInfo.like));
                no.setText(String.valueOf(detailInfo.dislike));
                synopsis.setText(detailInfo.synopsis);
                director.setText(detailInfo.director);
                actor.setText(detailInfo.actor);
                String url = detailInfo.thumb;
                ImageLoadTask task = new ImageLoadTask(url, mainImage, pb);
                task.execute();

        }
        //endregion

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10 && resultCode==RESULT_OK){
            this.name = data.getStringExtra("name");
            this.contents = data.getStringExtra("contents");
            Toast.makeText(getApplicationContext(), "저장된 아이디 : "+name+" , 저장된 내용 : " + contents, Toast.LENGTH_LONG).show();
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
            }else{
                super.onBackPressed();
            }
        }
    }
