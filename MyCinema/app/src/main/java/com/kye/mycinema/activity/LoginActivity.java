package com.kye.mycinema.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kye.mycinema.R;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txt_register;
    EditText edt_name,edt_pw;
    Button btn_in,btn_out;
    ProgressDialog progressDialog;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        edt_name = findViewById(R.id.edt_name);
        edt_pw = findViewById(R.id.edt_pw);
        btn_in = findViewById(R.id.btn_in);
        btn_out = findViewById(R.id.btn_out);
        txt_register = findViewById(R.id.txt_register);

        txt_register.setOnClickListener(this);
        btn_in.setOnClickListener(this);
        btn_out.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View v) {
        if(v==btn_in){
            login();
        }else if(v==btn_out){
            Intent intent = new Intent();
            setResult(RESULT_CANCELED,intent);
            finish();
        }else if(v==txt_register){
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        }
    }
    public void login(){
        final String mail = edt_name.getText().toString();
        String pw = edt_pw.getText().toString();
        if(mail.isEmpty()){
            Toast.makeText(getApplicationContext(),"Email을 입력해주세요.",Toast.LENGTH_SHORT).show();
            return;
        }
        if(pw.isEmpty()){
            Toast.makeText(getApplicationContext(),"비밀번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("로그인중입니다. 잠시만 기다려주세요..");
        progressDialog.show();

        auth.signInWithEmailAndPassword(mail,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent();
                    intent.putExtra("mail",mail);
                    setResult(RESULT_OK,intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"아이디 비밀번호를 확인해주세요.",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED,intent);
        finish();
    }
}
