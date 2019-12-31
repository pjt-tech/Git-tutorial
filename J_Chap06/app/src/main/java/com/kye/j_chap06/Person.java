package com.kye.j_chap06;

import android.widget.Toast;

public class Person {
    String name;
    MainActivity activity;

    public Person(String name, MainActivity activity) {
        this.name = name;
        this.activity = activity;
        Toast.makeText(activity.getApplicationContext(), name + "이(가) 생성되었습니다.", Toast.LENGTH_LONG).show();
        activity.imageView.setImageResource(R.drawable.person);
    }

    public void walk(int speed) {

        Toast.makeText(activity.getApplicationContext(), name + "이(가)" + speed + "km 속도로 걸어갑니다.", Toast.LENGTH_LONG).show();
        activity.imageView.setImageResource(R.drawable.person_walk);

    }

    public void run(int speed) {

        Toast.makeText(activity.getApplicationContext(), name + "이(가)" + speed + "km 속도로 뛰어갑니다.", Toast.LENGTH_LONG).show();
        activity.imageView.setImageResource(R.drawable.person_run);
    }
}
