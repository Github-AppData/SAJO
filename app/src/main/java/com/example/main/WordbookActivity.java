package com.example.main;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class WordbookActivity extends AppCompatActivity {

    // 선언
    Button btn_back_to_mypage;
    ListView my_word;
    SQLiteDatabase db;
    DBHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordbook);

        // 아이디 부여
        btn_back_to_mypage = (Button) findViewById(R.id.btn_back_to_mypage); // BACK 버튼
        my_word = (ListView) findViewById(R.id.my_word); // StudyActivity에서 메모 버튼을 눌러 나의 단어장으로 이동시킨 단어 목록 리스트뷰

        // back 버튼 클릭 시 나의 단어장에서 다시 마이 페이지로 이동
        btn_back_to_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_mp = new Intent(WordbookActivity.this, MypageActivity.class);
                startActivity(intent_mp);
            }
        });

        // wordbook 리스트
        dbhelper = new DBHelper(this, "userdata.db", null, 1);
        db = dbhelper.getWritableDatabase();
        dbhelper.onCreate(db);
        db.close();

        List<Wordbook> wordbook = dbhelper.ge

        // TODO : 리스트뷰의 아이템을 길게 클릭할 시 해당 단어가 리스트뷰에서 삭제될 수 있도록

    }
}
