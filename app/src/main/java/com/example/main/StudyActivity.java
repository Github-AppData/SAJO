package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class StudyActivity extends AppCompatActivity {

    // 선언
    Button btn_go_study, btn_to_wordbook, btn_prev, btn_pause, btn_next;
    TextView musicTitle, lyricsTextView, wordDefinition;
    EditText searchWord;
    SeekBar musicSeekBar;
    ScrollView lyricsScrollView, wordDictionary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        
        // 아이디 부여
        btn_go_study = (Button) findViewById(R.id.btn_go_study); // BACK 버튼
        btn_to_wordbook = (Button) findViewById(R.id.btn_to_wordbook); // 검색 결과를 나의 단어장으로 이동시키는 버튼

        btn_prev = (Button) findViewById(R.id.btn_prev); // 이전 곡 버튼
        btn_pause = (Button) findViewById(R.id.btn_pause); // 재생 및 일시정지 버튼
        btn_next = (Button) findViewById(R.id.btn_next); // 다음 곡 버튼
        musicSeekBar = (SeekBar) findViewById(R.id.musicSeekBar); // 음악 재생바 씨크바

        musicTitle = (TextView) findViewById(R.id.musicTitle); // 노래 제목 텍스트뷰

        lyricsScrollView = (ScrollView) findViewById(R.id.lyricsScrollView); // 노래 가사 텍스트뷰가 포함된 스크롤뷰
        lyricsTextView = (TextView) findViewById(R.id.lyricsTextView); // 노래 가사 텍스트뷰

        searchWord = (EditText) findViewById(R.id.searchWord); // 단어 검색창 에디트텍스트

        wordDictionary = (ScrollView) findViewById(R.id.wordDictionary); // 단어 검색 결과 텍스트뷰가 포함된 스크롤뷰
        wordDefinition = (TextView) findViewById(R.id.wordDefinition); // 단어 검색 결과 텍스트뷰

        // back 버튼 클릭 시 음악 플레이어 화면에서 다시 메인 화면으로 이동
        btn_go_study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_st = new Intent(StudyActivity.this, MainActivity.class);
                startActivity(intent_st);
            }
        });

        // 메모 버튼 클릭 시 검색한 단어 및 검색 결과를 나의 단어장으로 이동
        btn_to_wordbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : 메모 버튼 클릭 시 검색한 단어 및 검색 결과를 나의 단어장에 있는 리스트뷰로 이동 가능하도록 하는 로직
            }
        });

        // 이전 곡으로
        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : 버튼 클릭 시 STUDY 리스트에 존재하는 노래 중 이전 곡으로 이동
            }
        });

        // 재생 및 일시정지
        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : 버튼 클릭 시 STUDY 리스트에 존재하는 노래 중 이전 곡으로 이동
            }
        });

        // 다음 곡으로
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : 버튼 클릭 시 STUDY 리스트에 존재하는 노래 중 다음 곡으로 이동
            }
        });

        // 그 외 TODOLIST
        // TODO : 노래제목 텍스트뷰 및 노래가사 텍스트뷰에 노래 정보가 표시될 수 있도록
        // TODO : 노래 길이에 맞춰 씨크바가 움직일 수 있도록
        // TODO : 에디트뷰에 단어 입력 후 "검색" 버튼을 누르면 단어 검색 결과 텍스트뷰에 해당 단어의 뜻이 나타날 수 있도록

    }
}