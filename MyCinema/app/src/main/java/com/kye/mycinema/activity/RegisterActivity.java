package com.kye.mycinema.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kye.mycinema.R;


public class RegisterActivity extends AppCompatActivity {

    EditText edt_name,edt_pw;
    Button btn_in,btn_out;
    ProgressDialog progressDialog;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        edt_name = findViewById(R.id.edt_name);
        edt_pw = findViewById(R.id.edt_pw);
        btn_in = findViewById(R.id.btn_in);
        btn_out = findViewById(R.id.btn_out);
        progressDialog = new ProgressDialog(this);

        btn_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void registerUser(){
        String mail = edt_name.getText().toString();
        String pw = edt_pw.getText().toString();
        if(mail.isEmpty()){
            Toast.makeText(getApplicationContext(),"Email을 입력해주세요",Toast.LENGTH_SHORT).show();
            return;
        }
        if(pw.isEmpty()){
            Toast.makeText(getApplicationContext(),"비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("등록중입니다. 기다려주세요..");
        progressDialog.show();


        auth.createUserWithEmailAndPassword(mail,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"회원가입 완료!",Toast.LENGTH_SHORT).show();
                    edt_name.setText("");
                    edt_pw.setText("");
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"등록 에러!",Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });

    }
}
