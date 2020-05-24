package com.kye.mycinema.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kye.mycinema.R;
import com.kye.mycinema.data.AppHelper;

import java.util.HashMap;
import java.util.Map;

public class ContentsActivity extends AppCompatActivity {

    EditText editText, editText1;
    Intent intent;
    int mainIndex,id;
    String name,contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents);

        Button save_btn = findViewById(R.id.save_btn);
        final Button finish_btn = findViewById(R.id.finish_btn);
        editText = findViewById(R.id.editText);
        editText1 = findViewById(R.id.editText2);
        TextView txt_title = findViewById(R.id.txt_title);
        ImageView img_grade = findViewById(R.id.img_grade);

        intent = getIntent();
        String title = intent.getStringExtra("title");
        mainIndex = intent.getIntExtra("index", 0);
        int grade = intent.getIntExtra("grade",0);
        txt_title.setText(title);
        //연령 별로 나누어 이미지뷰에 연령 띄우기
        if(grade==12){
            img_grade.setImageResource(R.drawable.ic_12);
        }else if(grade==15){
            img_grade.setImageResource(R.drawable.ic_15);
        }else if(grade==19){
            img_grade.setImageResource(R.drawable.ic_19);
        }
        //사용자의 한줄평 작성 화면

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editText.getText().toString();
                contents = editText1.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력하세요.", Toast.LENGTH_LONG).show();
                } else if (contents.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "내용을 입력하세요.", Toast.LENGTH_LONG).show();
                } else{
                    requestCommentList();
                    Toast.makeText(getApplicationContext(),"서버에 저장이 완료되었습니다.", Toast.LENGTH_LONG).show();
                    intent.putExtra("id",mainIndex);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });

        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void requestCommentList() {

        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/createComment";

        StringRequest request = new StringRequest(
                //POST 방식은 서버에 데이터를 저장하는 방식이다
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        //POST는 getParams()라는 메서드 하나가 추가된다.getParams()를 통해 서버에 데이터를 저장시킨다. 번들과 같은 키 방식이다
        id = mainIndex+1;
        String rating = "7.5";
        Map<String,String> params = new HashMap<>();
        params.put("id",String.valueOf(id));
        params.put("writer",name);
        params.put("rating",rating);
        params.put("contents",contents);
        return params;
    }
};
        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
    }
}
