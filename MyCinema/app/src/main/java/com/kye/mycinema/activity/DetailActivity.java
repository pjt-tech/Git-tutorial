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
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.kye.mycinema.data.MyListAdapter;
import com.kye.mycinema.R;
import com.kye.mycinema.data.AppHelper;
import com.kye.mycinema.data.CommentList;
import com.kye.mycinema.data.DetailLIst;
import com.kye.mycinema.data.ImageLoadTask;
import com.kye.mycinema.data.MovieInfo;
import com.kye.mycinema.data.ResponseInfo;
import com.kye.mycinema.data.ReviewItem;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<ReviewItem> list = new ArrayList<>(); //한줄평 리스트를 담고있는 arraylist ,어댑터에 생성자로 넘겨줌
    TextView yes,no,title_txt,txt_rate,txt_rating,audience,genre,date,synopsis,director,actor; //웹에서 받아온 내용 적용할 상세화면 텍스트뷰들
    TextView nav_name,nav_mail; //네비게이션 드로어 헤더 텍스트
    ImageView yesImage,noImage,mainImage;
    Button contents_btn,all_btn,btn_preView,btn_res,btn_login; //인텐트
    ProgressBar pb;
    FrameLayout frame;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    LinearLayout container;
    MyListAdapter myAdapter;
    ViewGroup viewGroup;
    ListView listView;
    ImageView img_grade;
    FirebaseAuth auth;
    FirebaseUser user;
    FrameLayout frameLayout;

    int mainIndex; //id로 이용하기 위한 변수
    int index = 0;  //좋아요 컨트롤
    boolean select = true; //좋아요 컨트롤
    String title;
    float rating;
    int grade,count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        //region findViewById
        frameLayout = findViewById(R.id.frame);
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
        NavigationView navigationView;
        container = findViewById(R.id.layout);
        all_btn = findViewById(R.id.all_btn);
        contents_btn = findViewById(R.id.contents_btn);
        btn_preView = findViewById(R.id.btn_preView);
        btn_res = findViewById(R.id.btn_res);

        //endregion

        Intent intent = getIntent();
        mainIndex = intent.getIntExtra("index",0);

        LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.comment_list, container, true);
        listView = viewGroup.findViewById(R.id.listView);

        myAdapter = new MyListAdapter(getApplicationContext(),list);
        requestMovieList();
        requestCommentList();

        //region navigationView
        toolbar.setTitle("영화 상세");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);

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
            public void onClick(View v) {
                if(btn_login.getText().equals("로그인")){
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    setResult(RESULT_OK,intent);
                    finish();
                }else if (btn_login.getText().equals("로그아웃")){
                    auth.signOut();
                    nav_name.setText("비회원");
                    nav_mail.setText("e-mail");
                    btn_login.setText("로그인");
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    setResult(RESULT_CANCELED,intent);
                    finish();
                }

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.nav_list){
                    finish();
                }else if(id == R.id.nav_book){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ticket.movie.naver.com/Ticket/Reserve.aspx"));
                    startActivity(intent);
                }else if (id == R.id.nav_review){
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.themoviedb.org/"));
                    startActivity(intent);
                }else if (id == R.id.nav_settings){
                    if(nav_name.getText().equals("Cinema 천국 회원입니다.")){
                        Intent intent = new Intent(getApplicationContext(),UserSettingActivity.class);
                        startActivity(intent);
                    }else if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Snackbar.make(viewGroup,"로그인시 사용가능합니다.", BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }
                return true;
            }
        });
        //endregion

        //region onClickListener
        all_btn.setOnClickListener(this);
        contents_btn.setOnClickListener(this);
        yesImage.setOnClickListener(this);
        noImage.setOnClickListener(this);
        btn_preView.setOnClickListener(this);
        btn_res.setOnClickListener(this);
        //endregion
    }

    //region onClick
    @Override
    public void onClick(View v) {
        //좋아요 버튼을 만들기 위한 알고리즘
        if(v==all_btn){
            Intent intent = new Intent(getApplicationContext(),ListActivity.class);
            intent.putExtra("list",list);
            intent.putExtra("title",title);
            intent.putExtra("rating",rating);
            intent.putExtra("count",count);
            intent.putExtra("grade",grade);
            startActivity(intent);
        }else if(v==contents_btn){
            Intent intent = new Intent(getApplicationContext(), ContentsActivity.class);
            intent.putExtra("index",mainIndex);
            intent.putExtra("title",title);
            intent.putExtra("grade",grade);
            startActivityForResult(intent,10);
        }else if(v==yesImage){
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
        }else if(v==noImage){
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
        }else if(v==btn_preView){
            Intent intent = new Intent(getApplicationContext(),PreviewActivity.class);
            intent.putExtra("index",mainIndex);
            startActivity(intent);
        }else if(v==btn_res){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ticket.movie.naver.com/Ticket/Reserve.aspx"));
            startActivity(intent);
        }
    }
    //endregion

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
        //인텐트로 넘어올때 인덱스 값으로 구별하여 웹서버의 Json데이터를 Volley의 StringRequest객체를 이용하여 String변수에 담아옴

        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readCommentList";
        url += "?" + "id="+(mainIndex+1);

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        commentResponse(response);

                        listView.setAdapter(myAdapter);
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
        //Gson을 통하여 웹서버 한줄평 목록들을 객체로 변환 후 리스트뷰에 담아서 풀어줌
        Gson gson = new Gson();
        ResponseInfo responseInfo = gson.fromJson(response, ResponseInfo.class);
        count = responseInfo.totalCount;
        CommentList commentList = gson.fromJson(response, CommentList.class);
        for(int i = 0; i < commentList.result.size(); i++){
            MovieInfo info = commentList.result.get(i);
            String writer = info.writer;
            String contents = info.contents;
            String time = info.time;
            ReviewItem reviewItem = new ReviewItem(writer,contents,time);
            list.add(reviewItem);
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
            }else{
                super.onBackPressed();
            }
      }
        @Override
        protected void onResume(){
        super.onResume();
        //로그인 후 프레그먼트를 왔다갔다 할때 로그인이 풀리지않아야하기때문에 Resume에 설정 수정해야함
        Log.d("log", "onResume()실행됨");
            if(user!=null){
                nav_name.setText("Cinema 천국 회원입니다.");
                nav_mail.setText(user.getEmail()+"으로 로그인하였습니다.");
                btn_login.setText("로그아웃");
            }

    }
}
