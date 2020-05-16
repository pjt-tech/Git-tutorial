package com.kye.mycinema.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kye.mycinema.MainActivity;
import com.kye.mycinema.R;


public class KunFragment extends Fragment {

    String title;
    float rate;
    int grade;

    public KunFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_kun,container,false);
        TextView txt_title = viewGroup.findViewById(R.id.frag_title);
        TextView txt_rate = viewGroup.findViewById(R.id.txt_rate);
        TextView txt_grade = viewGroup.findViewById(R.id.txt_grade);


        Bundle bundle = getArguments();

        if(getArguments()!=null){
            title = bundle.getString("title");
            rate = bundle.getFloat("rate");
            grade = bundle.getInt("grade");
            txt_title.setText("1."+ title);
            txt_rate.setText("예매율 : " + rate + "%");
            txt_grade.setText(grade+"세 관람가");
        }

        Button detail_btn = viewGroup.findViewById(R.id.detail_btn);
        detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).onActivity(0);
            }
          });

        return viewGroup;
    }

}
