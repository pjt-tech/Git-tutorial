package com.kye.mymovie;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class Reviewer extends LinearLayout {

    String name,contents;
    TextView idtext,contentsText;

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
}
