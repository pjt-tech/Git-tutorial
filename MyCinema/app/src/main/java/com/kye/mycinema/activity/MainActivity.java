package com.kye.mycinema.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.kye.mycinema.R;
import com.kye.mycinema.data.AppHelper;
import com.kye.mycinema.data.MovieInfo;
import com.kye.mycinema.data.MovieList;
import com.kye.mycinema.data.ResponseInfo;
import com.kye.mycinema.fragment.FourthFragment;
import com.kye.mycinema.fragment.SecondFragment;
import com.kye.mycinema.fragment.ThirdFragment;
import com.kye.mycinema.fragment.FirstFragment;
import com.kye.mycinema.fragment.FifthFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    MyPagerAdapter adapter;
    ArrayList<Fragment> list = new ArrayList<>();
    TextView nav_name,nav_mail;
    Button btn_login;
    FirebaseAuth auth;
    FirebaseUser user;
    int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        //region toolbar,viewpager,requestQueue,navigationView
        if(AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(this);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("영화 목록");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewPager);
        drawerLayout = findViewById(R.id.main_drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {};
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger_menu);

        navigationView = findViewById(R.id.main_drawerView);
        View header_View = navigationView.getHeaderView(0);

        nav_name = header_View.findViewById(R.id.nav_name);
        nav_mail = header_View.findViewById(R.id.nav_mail);

        ImageView imageView = header_View.findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"이미지 클릭",Toast.LENGTH_LONG).show();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        btn_login = header_View.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
               if(btn_login.getText().equals("로그아웃")){
                    auth.signOut();
                    nav_name.setText("비회원");
                    nav_mail.setText("이메일");
                    btn_login.setText("로그인");
                    Toast.makeText(getApplicationContext(),"로그아웃 되었습니다.",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivityForResult(intent,20);
                }
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.nav_list){
                        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                }else if(id == R.id.nav_book){
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ticket.movie.naver.com/Ticket/Reserve.aspx"));
                    startActivity(intent);
                }else if (id == R.id.nav_review){
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.themoviedb.org/"));
                    startActivity(intent);
                }else if (id == R.id.nav_settings) {
                    if(btn_login.getText().equals("로그아웃")){
                        Intent intent = new Intent(getApplicationContext(),UserSettingActivity.class);
                        startActivity(intent);
                    }else if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.closeDrawer(GravityCompat.START);

                    }
                }
                return true;
            }
        });


//endregion
        requestMovieList();
    }

    //region request
    public void requestMovieList(){
       String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readMovieList";
       url += "?" + "type=1";

       //Volley 라이브러리를 사용하여 StringRequest로 웹서버에 데이터 요청
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    //String형태로 응답을 받음
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),"서버에 요청 및 응답완료",Toast.LENGTH_LONG).show();
                        processResponse(response);
                        adapter = new MyPagerAdapter(getSupportFragmentManager(),list);
                        viewPager.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        );
        //이미 requestQueue로 받은 데이터가 있어도 새로 받아서 넣겠다.
        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
    }
    //endregion

    //region Response
    public void processResponse(String response){

        //웹서버에서 전달받은 JSON(response)데이터를 GSON을 이용하여 자바객체로 변환하고 사용 할 수 있는 형태로 바꿔줌
        Gson gson = new Gson();
        ResponseInfo info = gson.fromJson(response, ResponseInfo.class);
        if(info.code==200){
            MovieList movieList = gson.fromJson(response,MovieList.class);
            for(int i = 0; i< movieList.result.size(); i++){
                MovieInfo movieInfo = movieList.result.get(i);
                int id = movieInfo.id;
                String title = movieInfo.title;
                float rate = movieInfo.reservation_rate;
                int grade = movieInfo.grade;
                String image = movieInfo.image;
                setFragment(id,title,rate,grade,image,i);
            }
        }
    }
    //endregion

    //region setFrag
    public void setFragment(int id,String title,float rate,int grade,String image,int index){

        //Gson을 이용해 사용이 가능해진 데이터를 각 프레그먼트에 번들형태로 전달
        Bundle bundle = new Bundle();
        switch (index){
            case 0 :
                FirstFragment fragment = new FirstFragment();
                list.add(fragment);
                bundle.putInt("id",id);
                bundle.putString("title",title);
                bundle.putFloat("rate",rate);
                bundle.putInt("grade",grade);
                bundle.putString("image",image);
                fragment.setArguments(bundle);
                break;
            case 1 :
                SecondFragment second = new SecondFragment();
                list.add(second);
                bundle.putInt("id",id);
                bundle.putString("title",title);
                bundle.putFloat("rate",rate);
                bundle.putInt("grade",grade);
                bundle.putString("image",image);
                second.setArguments(bundle);
                break;
            case 2 :
                ThirdFragment third = new ThirdFragment();
                list.add(third);
                bundle.putInt("id",id);
                bundle.putString("title",title);
                bundle.putFloat("rate",rate);
                bundle.putInt("grade",grade);
                bundle.putString("image",image);
                third.setArguments(bundle);
                break;
            case 3 :
                FourthFragment fourth = new FourthFragment();
                list.add(fourth);
                bundle.putInt("id",id);
                bundle.putString("title",title);
                bundle.putFloat("rate",rate);
                bundle.putInt("grade",grade);
                fourth.setArguments(bundle);
                bundle.putString("image",image);
                break;
            case 4 :
                FifthFragment fifth = new FifthFragment();
                list.add(fifth);
                bundle.putInt("id",id);
                bundle.putString("title",title);
                bundle.putFloat("rate",rate);
                bundle.putInt("grade",grade);
                fifth.setArguments(bundle);
                bundle.putString("image",image);
                break;

                default : return;
        }
    }
    //endregion

    public void onActivity(int index){
        Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
        intent.putExtra("index",index);
        startActivityForResult(intent,10);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==10 && resultCode==RESULT_OK){
                    result = data.getIntExtra("result",0);

             }else if(requestCode==20 && resultCode==RESULT_OK){
                String mail = data.getStringExtra("mail");
                nav_name.setText("Cinema 천국 회원입니다.");
                nav_mail.setText(mail+"으로 로그인하였습니다.");
                btn_login.setText("로그아웃");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("log", "onResume()실행됨");
        if(user!=null){
            nav_name.setText("Cinema 천국 회원입니다.");
            nav_mail.setText(user.getEmail()+"으로 로그인하였습니다.");
            btn_login.setText("로그아웃");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("log", "onStop()실행됨");
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> list;

        public MyPagerAdapter(@NonNull FragmentManager fm, ArrayList list) {
            super(fm);
            this.list=list;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

    }
}
