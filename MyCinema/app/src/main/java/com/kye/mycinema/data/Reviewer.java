package com.kye.mycinema.data;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.kye.mycinema.R;

public class Reviewer extends LinearLayout {

    //리스트뷰어댑터로부터 정보를 받아 인플레이션을 하기때문에  LinearLayout 을 상속받음

    String name,contents,time;
    TextView idtext,contentsText,txt_time;

    public Reviewer(Context context) {
        super(context);
        init(context);
    }

    public Reviewer(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            init(context);
    }

    public void init(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.listitem, this, true);
        idtext = findViewById(R.id.idText);
        contentsText = findViewById(R.id.contentText);
        txt_time = findViewById(R.id.txt_time);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        idtext.setText(name);
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        contentsText.setText(contents);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        txt_time.setText(time);
    }
}
