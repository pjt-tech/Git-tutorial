package com.kye.mycinema;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.kye.mycinema.data.AppHelper;
import com.kye.mycinema.activity.firstActivity;
import com.kye.mycinema.activity.FourthActivity;
import com.kye.mycinema.activity.SecondActivity;
import com.kye.mycinema.activity.ThirdActivity;
import com.kye.mycinema.activity.FifthActivity;
import com.kye.mycinema.adapter.MyPagerAdapter;
import com.kye.mycinema.data.MovieInfo;
import com.kye.mycinema.data.MovieList;
import com.kye.mycinema.data.ResponseInfo;
import com.kye.mycinema.fragment.EvilFragment;
import com.kye.mycinema.fragment.GongFragment;
import com.kye.mycinema.fragment.KingFragment;
import com.kye.mycinema.fragment.KunFragment;
import com.kye.mycinema.fragment.LuckFragment;


public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    MyPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region toolbar,viewpager,requestQueue
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("영화 목록");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.main_drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {};
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger_menu);


        viewPager = findViewById(R.id.viewPager);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);


        if(AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        requestMovieList();

        NavigationView navigationView = findViewById(R.id.main_drawerView);
        View header_View = navigationView.getHeaderView(0);

        ImageView imageView = header_View.findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"이미지 클릭",Toast.LENGTH_LONG).show();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        //endregion

    }

    //region request
    public void requestMovieList(){
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readMovieList";
        url += "?" + "type=1";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),"요청 및 응답완료",Toast.LENGTH_LONG).show();
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

    //region Response
    public void processResponse(String response){

        Gson gson = new Gson();

        ResponseInfo info = gson.fromJson(response, ResponseInfo.class);
        if(info.code==200){
            MovieList movieList = gson.fromJson(response,MovieList.class);
            for(int i = 0; i< movieList.result.size(); i++){
                MovieInfo movieInfo = movieList.result.get(i);
                String title = movieInfo.title;
                float rate = movieInfo.reservation_rate;
                int grade = movieInfo.grade;
                setFragment(title,rate,grade,i);
            }
        }
    }
    //endregion

    //region setFrag
    public void setFragment(String title,float rate,int grade,int index){
        Bundle bundle = new Bundle();
        switch (index){
            case 0 :
                KunFragment kun = new KunFragment();

                bundle.putString("title",title);
                bundle.putFloat("rate",rate);
                bundle.putInt("grade",grade);
                kun.setArguments(bundle);
                break;
            case 1 :
                GongFragment gong = new GongFragment();

                bundle.putString("title",title);
                bundle.putFloat("rate",rate);
                bundle.putInt("grade",grade);
                gong.setArguments(bundle);
                break;
            case 2 :
                KingFragment king = new KingFragment();

                bundle.putString("title",title);
                bundle.putFloat("rate",rate);
                bundle.putInt("grade",grade);
                king.setArguments(bundle);
                break;
            case 3 :
                EvilFragment evil = new EvilFragment();

                bundle.putString("title",title);
                bundle.putFloat("rate",rate);
                bundle.putInt("grade",grade);
                evil.setArguments(bundle);
                break;
            case 4 :
                LuckFragment luck = new LuckFragment();

                bundle.putString("title",title);
                bundle.putFloat("rate",rate);
                bundle.putInt("grade",grade);
                luck.setArguments(bundle);
                break;


                default : return;
        }
    }
    //endregion

    //region intent
    public void onActivity(int index) {
        Intent intent;
        if (index == 0) {
            intent = new Intent(getApplicationContext(), firstActivity.class);
            startActivity(intent);
        } else if (index == 1) {
            intent = new Intent(getApplicationContext(), SecondActivity.class);
            startActivity(intent);
        } else if (index == 2) {
            intent = new Intent(getApplicationContext(), ThirdActivity.class);
            startActivity(intent);
        } else if (index == 3) {
            intent = new Intent(getApplicationContext(), FourthActivity.class);
            startActivity(intent);
        } else if (index == 4) {
            intent = new Intent(getApplicationContext(), FifthActivity.class);
            startActivity(intent);
        }
    }
    //endregion

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
