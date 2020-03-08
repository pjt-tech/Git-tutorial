package com.kye.mystore;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.viewpager.widget.ViewPager;

        import android.os.Bundle;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;

        import com.google.android.material.floatingactionbutton.FloatingActionButton;
        import com.google.android.material.tabs.TabLayout;

        import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    FloatingActionButton fab,fabsub1,fabsub2;
    TabLayout tabLayout;
    boolean isfabOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.pager);
        //viewPager 를 사용하기 위해서는 어댑터라는것이 필요
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);


        tabLayout = findViewById(R.id.layout);
        //탭호스트와 뷰페이저 연동
        tabLayout.setupWithViewPager(viewPager);




    }

}
