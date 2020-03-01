package com.kye.mytoast;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(),"위치가 바뀐 토스트",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER|Gravity.LEFT,200,200);
                toast.show();
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toastborder,(ViewGroup)findViewById(R.id.toast_layout_root)); //인플레이터를 통해 새로만든레이아웃을 가져옴

                TextView textView = layout.findViewById(R.id.text); // 가져온 레이아웃을 통해 텍스트를 꺼낼수있음
                textView.setText("모양을 바꾼 토스트");

                Toast toast = new Toast(getApplicationContext()); //토스트 객체화
                toast.setGravity(Gravity.CENTER,0,-100); //토스트 위치설정
                toast.setDuration(Toast.LENGTH_LONG); //띄울 길이
                toast.setView(layout);  //인플레이터에서만든 레이아웃 설정

                toast.show();


            }
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"스낵바입니다.",Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
