package com.example.main;

import static com.example.main.DBHelper.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    // 선언
    private List<String> user_id = Arrays.asList("admin", "user1", "user2"); // 임시 유저 아이디 목록 (추후 삭제)
    private List<String> user_pw = Arrays.asList("admin", "password1", "password2"); // 임시 유저 비밀번호 목록 (추후 삭제)
    EditText login_id, login_pw;
    Button btn_login, btn_signup;
    Intent intent_main;
    Intent intent_signup;
    DBHelper dbhelper;
    SQLiteDatabase db;

    String sql;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbhelper = new DBHelper(LoginActivity.this);
        db = dbhelper.getWritableDatabase();
        // 아이디 부여
        login_id = (EditText) findViewById(R.id.login_id); // 로그인 시 입력할 아이디 에디트텍스트
        login_pw = (EditText) findViewById(R.id.login_pw); // 로그인 시 입력할 비밀번호 에디트텍스트
        btn_login = (Button) findViewById(R.id.btn_login); // 버튼을 눌러 로그인
        btn_signup = (Button) findViewById(R.id.btn_signup); // 회원가입창으로 이동하는 버튼

        // 로그인 이벤트
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = login_id.getText().toString();
                String pwd = login_pw.getText().toString();

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
                cursor.moveToNext();

                //비밀번호가 틀렸을 때
                if (!pwd.equals(cursor.getString(0))) {
                    Toast toast = Toast.makeText(LoginActivity.this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(LoginActivity.this, "로그인성공", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                    cursor.close();
                }
            });
                // 비회원을 위한 회원가입 창으로 이동 이벤트
                btn_signup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 회원가입 창으로 이동하는 코드 작성
                        intent_signup = new Intent(LoginActivity.this, SignupActivity.class);
                        startActivity(intent_signup);
                    }
                });

    }
}

