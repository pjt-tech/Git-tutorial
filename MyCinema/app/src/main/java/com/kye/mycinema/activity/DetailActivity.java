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
import android.os.Handler;
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
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.kye.mycinema.adapter.MyListAdapter;
import com.kye.mycinema.R;
import com.kye.mycinema.data.AppHelper;
import com.kye.mycinema.data.CommentList;
import com.kye.mycinema.data.DetailLIst;
import com.kye.mycinema.data.ImageLoadTask;
import com.kye.mycinema.data.MovieInfo;
import com.kye.mycinema.data.ReviewItem;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    Handler handler = new Handler();
    ArrayList<ReviewItem> list = new ArrayList<>();
    TextView yes,no,title_txt,txt_rate,txt_rating,audience,genre,date,synopsis,director,actor; //웹에서 받아온 내용 적용할 상세화면 텍스트뷰들
    ImageView yesImage,noImage,mainImage;
    Button contents_btn,all_btn;
    ProgressBar pb;
    FrameLayout frame;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    LinearLayout container;
    MyListAdapter myAdapter;
    ViewGroup viewGroup;
    ListView listView;
    ImageView img_grade;
    int mainIndex; //id로 이용하기 위한 변수
    int index = 0;
    boolean select = true;
    String title;
    float rating;
    int grade;

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
        img_grade = findViewById(R.id.img_grade);

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

        myAdapter = new MyListAdapter(getApplicationContext(),list);

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


        all_btn = findViewById(R.id.all_btn);
        all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ListActivity.class);
                intent.putExtra("list",list);
                intent.putExtra("title",title);
                intent.putExtra("rating",rating);
                startActivity(intent);
            }
        });

        contents_btn = findViewById(R.id.contents_btn);
        contents_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContentsActivity.class);
                intent.putExtra("index",mainIndex);
                startActivityForResult(intent,10);
            }
        });
        //region like
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
                            requestCommentList();
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
                MovieInfo info = detailLIst.result.get(0);
                title = info.title;
                rating = info.audience_rating;
                title_txt.setText(info.id + "." + title);
                date.setText(info.date + " 개봉");
                genre.setText(info.genre + "/" + info.duration + "분");
                txt_rate.setText(info.reservation_rate + "%");
                txt_rating.setText(String.valueOf(rating));
                audience.setText(String.valueOf(info.audience) + "명");
                yes.setText(String.valueOf(info.like));
                no.setText(String.valueOf(info.dislike));
                synopsis.setText(info.synopsis);
                director.setText(info.director);
                actor.setText(info.actor);
                String url = info.thumb;
                ImageLoadTask task = new ImageLoadTask(url, mainImage, pb);
                task.execute();
                grade = info.grade;
                if(grade==12){
                    img_grade.setImageResource(R.drawable.ic_12);
                }else if(grade==15){
                    img_grade.setImageResource(R.drawable.ic_15);
                }else if(grade==19){
                    img_grade.setImageResource(R.drawable.ic_19);
                }
        }
        //endregion

    //region CommentList
    public void requestCommentList(){

        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readCommentList";
        url += "?" + "id="+(mainIndex+1);

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        commentResponse(response);
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

    public void commentResponse(String response){

        Gson gson = new Gson();
        CommentList commentList = gson.fromJson(response, CommentList.class);
        for(int i = 0; i < commentList.result.size(); i++){
            MovieInfo info = commentList.result.get(i);
            String writer = info.writer;
            String contents = info.contents;
            String time = info.time;
            ReviewItem reviewItem = new ReviewItem(writer,contents,time);
            list.add(reviewItem);
        }
        listView.setAdapter(myAdapter);
    }
    //endregion


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10 && resultCode==RESULT_OK){

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
