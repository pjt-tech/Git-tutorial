package com.kye.mycinema;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    public void onActivity(int index){
        Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
        if(index==0){
            intent.putExtra("id",index);
        }else if(index==1){
            intent.putExtra("id",index);
        }else if(index==2){
            intent.putExtra("id",index);
        }else if(index==3){
            intent.putExtra("id",index);
        }else if(index==4){
            intent.putExtra("id",index);
        }else if(index==5){
            intent.putExtra("id",index);
        }
        startActivity(intent);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> list = new ArrayList<>();

        public MyPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
            list.add(new KunFragment());
            list.add(new GongFragment());
            list.add(new KingFragment());
            list.add(new EvilFragment());
            list.add(new LuckFragment());
            list.add(new AsuraFragment());
        }

        @NonNull
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
