package com.cyberkyj.firebase_auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edtEmail, edtPassword;
    Button btnSignIn;
    TextView txtFindPassword, txtSignIn;
    ProgressDialog progressDialog;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null){
            startActivity(new Intent(this, ProfileActivity.class));
        }
        edtEmail = findViewById(R.id.edtEmail2);
        edtPassword = findViewById(R.id.edtPassword2);
        txtFindPassword = findViewById(R.id.txtFindPassword);
        txtSignIn = findViewById(R.id.txtViewSign);
        btnSignIn = findViewById(R.id.btnSignUP2);
        progressDialog = new ProgressDialog(this);
        btnSignIn.setOnClickListener(this);
        txtSignIn.setOnClickListener(this);
        txtFindPassword.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v == btnSignIn){
            userLogin();
        }else if(v == txtSignIn){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }else if(v == txtFindPassword){
            finish();
            startActivity(new Intent(this, FindActivity.class));
        }

    }

    public void userLogin(){
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"Email을 입력해주세요",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Password를 입력해주세요",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("로그인중입니다. 잠시만 기다려주세요..");
        progressDialog.show();

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        }else{
                            Toast.makeText(getApplicationContext(),"로그인 실패!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
