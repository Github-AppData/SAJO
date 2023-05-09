package com.example.main;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity{

    // 선언
    DBHelper dbhelper;
    SQLiteDatabase db;
    EditText signup_name, signup_id, signup_pwd, signup_email;
    Button complete_signup_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // 디자인 변수 선언
        signup_email = findViewById(R.id.signup_email); // 회원가입 시 사용할 이메일 입력 에디트텍스트
        signup_id = findViewById(R.id.signup_id); // 회원가입 시 사용할 아이디 입력 에디트텍스트
        signup_name = findViewById(R.id.signup_name); // 회원가입 시 사용자명 입력 에디트텍스트
        signup_pwd = findViewById(R.id.signup_pw); // 회원가입 시 사용할 비밀번호 입력 에디트텍스트
        complete_signup_btn = findViewById(R.id.complete_signup); // 회원가입 진행 버튼

        complete_signup_btn.setOnClickListener(v -> {
            String id = signup_id.getText().toString();
            String pwd = signup_pwd.getText().toString();
            String email = signup_email.getText().toString();
            String name = signup_name.getText().toString();

            dbhelper = new DBHelper(SignupActivity.this,"userdata.db",null,1);
            db = dbhelper.getWritableDatabase();

            //아이디와 비밀번호가 입력됬는지 확인
            if(id.length() == 0 || pwd.length() == 0){
                Toast.makeText(SignupActivity.this, "아이디 또는 비밀번호가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            //이메일이 이미 존재하는지 확인
            if (dbhelper.checkEmailExist(db,email)) {
                Toast.makeText(getApplicationContext(), "이미 사용 중인 이메일입니다.", Toast.LENGTH_SHORT).show();
            }
            // 아이디가 이미 존재하는지 확인
            else if (dbhelper.checkIdExist(db,id)) {
                Toast.makeText(getApplicationContext(), "이미 사용 중인 아이디입니다.", Toast.LENGTH_SHORT).show();
            } else {
                dbhelper.Insert(db,"USER",id, name, pwd, email);
                Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
            db.close();
        });
    }
}


