package com.kye.mycinema.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.kye.mycinema.fragment.EvilFragment;
import com.kye.mycinema.fragment.GongFragment;
import com.kye.mycinema.fragment.KingFragment;
import com.kye.mycinema.fragment.KunFragment;
import com.kye.mycinema.fragment.LuckFragment;

import java.util.ArrayList;

public class MyPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> list = new ArrayList<>();

    public MyPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        list.add(new KunFragment());
        list.add(new GongFragment());
        list.add(new KingFragment());
        list.add(new EvilFragment());
        list.add(new LuckFragment());
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
