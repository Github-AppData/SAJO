package com.example.main;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity{

    // 선언
    DBHelper dbhelper;
    SQLiteDatabase sqlDB;
    EditText signup_name, signup_id, signup_pwd, signup_email;
    Button complete_signup_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // 디자인 변수 선언
        signup_email = (EditText) findViewById(R.id.signup_email); // 회원가입 시 사용할 이메일 입력 에디트텍스트
        signup_id = (EditText) findViewById(R.id.signup_id); // 회원가입 시 사용할 아이디 입력 에디트텍스트
        signup_name = (EditText) findViewById(R.id.signup_name); // 회원가입 시 사용자명 입력 에디트텍스트
        signup_pwd = (EditText) findViewById(R.id.signup_pw); // 회원가입 시 사용할 비밀번호 입력 에디트텍스트
        complete_signup_btn = (Button) findViewById(R.id.complete_signup); // 회원가입 진행 버튼

        dbhelper = new DBHelper(SignupActivity.this);

        complete_signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 버튼을 누른 뒤 이메일이 이미 존재하는지 확인
                if (dbhelper.checkEmailExist(signup_email.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "이미 사용 중인 이메일입니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                // 아이디가 이미 존재하는지 확인
                else if (dbhelper.checkIdExist(signup_id.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "이미 사용 중인 아이디입니다.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    dbhelper.Insert(signup_id.getText().toString(), signup_name.getText().toString(), signup_pwd.getText().toString(), signup_email.getText().toString());
                    Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent_loginagain = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent_loginagain);
                    finish();
                }
            }
        });
    }
}


