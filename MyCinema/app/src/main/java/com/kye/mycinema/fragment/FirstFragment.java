package com.kye.mycinema.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kye.mycinema.activity.MainActivity;
import com.kye.mycinema.R;
import com.kye.mycinema.data.ImageLoadTask;


public class FirstFragment extends Fragment {

    String title,fir_img;
    float rate;
    int id,grade;


    public FirstFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_first,container,false);
        TextView txt_title = viewGroup.findViewById(R.id.frag_title);
        TextView txt_rate = viewGroup.findViewById(R.id.txt_rate);
        TextView txt_grade = viewGroup.findViewById(R.id.txt_grade);
        ImageView imageView = viewGroup.findViewById(R.id.fir_img);
        ProgressBar progressBar = viewGroup.findViewById(R.id.progressBar);

        //번들이 null이 아니라면 키값에 해당하는 데이터를 전달받아 텍스트뷰에 설정 하겠다.
        if(getArguments()!=null){
            id = getArguments().getInt("id");
            title = getArguments().getString("title");
            rate = getArguments().getFloat("rate");
            grade = getArguments().getInt("grade");
            fir_img = getArguments().getString("image");
            txt_title.setText(id+"."+ title);
            txt_rate.setText("예매율 : " + rate + "%");
            txt_grade.setText(grade+"세 관람가");
        }

         /*이미지는 크기가 커서 쓰레드를 이용하여 비트맵으로 바꾸는 작업을해줘야함
          AsyncTask를 이용,번들로 받은 이미지 다운로드 경로 url을 task에 전달.
         */
        String url = fir_img;
        ImageLoadTask task = new ImageLoadTask(url,imageView,progressBar);
        task.execute();

        //상세보기화면클릭시 fragment에서 다른액티비티로 직접적인 이동이 불가하므로 메인액티비티에 인덱스 값을 전달하여 이동
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
