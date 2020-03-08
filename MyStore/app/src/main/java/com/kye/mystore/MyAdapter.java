package com.kye.mystore;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MyAdapter extends FragmentPagerAdapter {


    //플래그먼트자료형의 arrayList 를 어댑터가 객체화함과 동시에 객체화
    ArrayList<Fragment> fragments = new ArrayList<>();

    //탭호스트의 타이틀
    String[] titles = {"tab1","tab2","tab3","tab4"};


    public MyAdapter(FragmentManager fm) {
        super(fm);
        //어댑터생성자가 호출될때
        //arraylist에 프래그먼트를 객체화하여 하나씩 더해준다.
        fragments.add(new FragmentA());
        fragments.add(new FragmentB());
        fragments.add(new FragmentC());
        fragments.add(new FragmentD());
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
