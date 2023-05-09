package com.example.main;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText login_id, login_pw;
    Button btn_login, btn_signup;
    Intent intent_login;
    Intent intent_signup;
    DBHelper dbhelper;
    SQLiteDatabase db;
    String sql;
    static _USER user;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = new _USER();
        dbhelper = new DBHelper(LoginActivity.this,"userdata.db",null,1);
        db = dbhelper.getWritableDatabase();
        dbhelper.onCreate(db);
        db.close();

        // 아이디 부여
        login_id = findViewById(R.id.login_id); // 로그인 시 입력할 아이디 에디트텍스트
        login_pw = findViewById(R.id.login_pw); // 로그인 시 입력할 비밀번호 에디트텍스트
        btn_login = findViewById(R.id.btn_login); // 버튼을 눌러 로그인
        btn_signup = findViewById(R.id.btn_signup); // 회원가입창으로 이동하는 버튼

        // 로그인 이벤트
        btn_login.setOnClickListener(v -> {
            String id = login_id.getText().toString();
            String pwd = login_pw.getText().toString();

            db = dbhelper.getWritableDatabase();

            //  아이디가 입력되지 않았을떄 돌아감
            if (id.length() == 0) {
                Toast toast = Toast.makeText(LoginActivity.this, "아이디를 입력하지 않았습니다.", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            // 비밀번호가 입력되지 않았을떄 돌아감
            if (pwd.length() == 0) {
                Toast toast = Toast.makeText(LoginActivity.this, "비밀번호를 입력하지 않았습니다.", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            sql = "SELECT id FROM USER WHERE id = '" + id + "'";
            cursor = db.rawQuery(sql, null);

            if (cursor.getCount() != 1) {
                Toast toast = Toast.makeText(LoginActivity.this, "아이디가 존재않습니다.", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            sql = "SELECT pwd FROM USER WHERE id = '" + id + "'";
            cursor = db.rawQuery(sql, null);
            cursor.moveToNext();
            if(!pwd.equals(cursor.getString(0))) {
                Toast toast = Toast.makeText(LoginActivity.this, "아이디와 비밀번호가 맞지않습니다.", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Toast toast = Toast.makeText(LoginActivity.this, "로그인성공", Toast.LENGTH_SHORT);
                toast.show();
                //다른 액티비티에서도 사용가능 하도록 값을 db에서 가져와 캡슐화하여 저장
                dbhelper.getUserData(db,user,"USER",id);
                db.close();
                intent_login = new Intent(getApplicationContext(), MainActivity.class);
                intent_login.putExtra("id",id);
                startActivity(intent_login);
                finish();
            }
            db.close();
            cursor.close();
        });
        // 비회원을 위한 회원가입 창으로 이동 이벤트
        btn_signup.setOnClickListener(v -> {
            // 회원가입 창으로 이동하는 코드 작성
            intent_signup = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent_signup);
        });

    }
}

