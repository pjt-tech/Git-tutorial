package com.kye.mycinema.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kye.mycinema.MainActivity;
import com.kye.mycinema.R;


public class EvilFragment extends Fragment {


    public EvilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_evil,container,false);
        Button detail_btn = viewGroup.findViewById(R.id.detail_btn);

        detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).onActivity(3);
            }
        });

        return viewGroup;
    }

}