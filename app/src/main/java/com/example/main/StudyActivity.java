package com.example.main;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class StudyActivity extends AppCompatActivity implements View.OnClickListener {

    // 선언

    private MediaPlayer mediaPlayer = new MediaPlayer();                        // 음악 플레이를 위한 클래스
    Button btn_go_study, btn_to_wordbook, btn_prev, btn_pause, btn_next, btn_search;
    TextView musicTitle, lyricsTextView, wordDefinition;
    EditText searchWord;
    SeekBar musicSeekBar;
    ScrollView lyricsScrollView, wordDictionary;
    private ArrayList<Playlist> list;                       // 음악 리스트
    private int position;                                   // 현재 음악 번호 및 위치
    boolean isPlaying = true;                               // 플레이 중인지 아닌지 switch 변수
    private ProgressUpdate progressUpdate;                  // 음악 process 실시간 출력하는 Thread
    private boolean playSwitch = true;                      // 음악 실행 여부 확인
    SQLiteDatabase db;
    DBHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        
        // 아이디 부여
        btn_go_study = (Button) findViewById(R.id.btn_go_study); // BACK 버튼
        btn_to_wordbook = (Button) findViewById(R.id.btn_to_wordbook); // 검색 결과를 나의 단어장으로 이동시키는 버튼
        btn_search = (Button) findViewById(R.id.btn_search); // 단어 의미 검색 버튼

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

        dbhelper = new DBHelper(this, "userdata.db", null, 1);
        db = dbhelper.getWritableDatabase();
        dbhelper.onCreate(db);
        db.close();

        btn_prev.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        btn_pause.setOnClickListener(this);

        // 화면 이동 완료 되었으면 id Toast 메시지 띄워주기
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0); // 음악 재생하는 위치 가져오기
        list = (ArrayList<Playlist>) intent.getSerializableExtra("playlist"); // 재생할 모든 음악 가져오기

        // 해당 번호의 음악 재생하기
        playMusic(list.get(position));
        progressUpdate = new ProgressUpdate();
        progressUpdate.start();

        // back 버튼 클릭 시 음악 플레이어 화면에서 다시 메인 화면으로 이동
        btn_go_study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPlaying = false;
                if(mediaPlayer!=null){
                    mediaPlayer.release();
                    mediaPlayer = null;
                }

                Intent intent_st = new Intent(StudyActivity.this, MainActivity.class);
                startActivity(intent_st);
            }
        });

        // TODO : 노래 길이에 맞춰 씨크바가 움직일 수 있도록
        // seekBar가 계속 증가가 될텐데 그때마다 이벤트 발생시켜줌
        musicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            // seekBar에 사용자가 손을 대고 있을 때 => 음악 중지
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();
            }

            // seekBar에 사용자가 손을 떼었을 때 => 해당 장소에 음악 재생
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
                if(seekBar.getProgress() > 0 && isPlaying == true){
                    mediaPlayer.start();
                }
            }
        });

        // TODO : 에디트뷰에 단어 입력 후 "검색" 버튼을 누르면 단어 검색 결과 텍스트뷰에 해당 단어의 뜻이 나타날 수 있도록
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchWord.getText().toString().isEmpty()){
                    Toast.makeText(StudyActivity.this, "검색할 단어를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    String mean = dbhelper.getWordMean(searchWord.getText().toString());
                    Log.v("mean", mean);
                    wordDefinition.setText(mean);
                }
            }
        });


        // 메모 버튼 클릭 시 검색한 단어 및 검색 결과를 나의 단어장으로 이동
        btn_to_wordbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : 메모 버튼 클릭 시 검색한 단어 및 검색 결과를 나의 단어장에 있는 리스트뷰로 이동 가능하도록 하는 로직
                String result = dbhelper.addMyWord(wordDefinition.getText().toString());
                Toast.makeText(StudyActivity.this, result, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_pause){
            if(playSwitch){
                // 음악 멈춤
                mediaPlayer.pause();
                playSwitch = false;
            } else {
                // 음악 재생 시작 지점을 이전에 멈춘 지점으로 지정해줌
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
                // 음악 시작
                mediaPlayer.start();
                playSwitch = true;
            }
        } else if(v.getId() == R.id.btn_prev){
            // TODO : 버튼 클릭 시 STUDY 리스트에 존재하는 노래 중 이전 곡으로 이동
            if(position-1 >= 0){ // 이전 곡이 있을 때
                position--;      // 위치를 이전으로 바꾸어줌
                playMusic(list.get(position));  // 이전 음악을 재생시켜줌
                musicSeekBar.setProgress(0);         // 음악을 처음부터 재생하니까 SeekBar도 처음으로 지정
            }
        } else if(v.getId() == R.id.btn_next){
            // TODO : 버튼 클릭 시 STUDY 리스트에 존재하는 노래 중 다음 곡으로 이동
            if(position+1 < list.size()){ // 다음 곡이 있을 때
                position++;      // 위치를 다음으로 바꾸어줌
                playMusic(list.get(position));  // 다음 음악을 재생시켜줌
                musicSeekBar.setProgress(0);         // 음악을 처음부터 재생하니까 SeekBar도 처음으로 지정
            }
        }
    }

    // 해당 번호의 음악 가져와서 재생하기
    public void playMusic(Playlist playlist) {
        try{
            musicSeekBar.setProgress(0); // 음악 재생 전 seekBar 0으로 초기화
            musicTitle.setText(playlist.getName()); // 제목 보여줌
            lyricsTextView.setText(playlist.getLyrics()); // 가사 보여줌
            Log.v("musicActivity", "제목 보여줌");
            Log.v("musicActivity",   playlist.getLyrics() + " 보여줌");
            Log.v("musicActivity", "음악 " + R.raw.memories + " 새로 생성해줌");
            Log.v("musicActivity", "음악 " + R.raw.peaches + " 새로 생성해줌");
            Log.v("musicActivity", "음악 " + R.raw.abcdefu + " 새로 생성해줌");
            Log.v("musicActivity", "음악 " + R.raw.painkiller + " 새로 생성해줌");
            mediaPlayer.reset(); // 음악 재생하기 전에 초기화
            Log.v("musicActivity", "음악 " + playlist.getMusicResId() + " 새로 생성해줌");
            mediaPlayer = MediaPlayer.create(this, playlist.getMusicResId());
            mediaPlayer.setVolume(1.0f, 1.0f);
            // 음악이 모두 재생되어서 끝났을 때
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if(position + 1 < list.size()){
                        position++;
                        playMusic(list.get(position));
                    }
                }
            });
            Log.v("musicActivity", "음악 OnCompletionListener 세팅해줌");
            mediaPlayer.start();   // 음악 재생하기
            Log.v("musicActivity", "음악 재생");
            musicSeekBar.setMax(mediaPlayer.getDuration()); // seekBar의 최대값음 음악 재생 마지막 값으로 세팅
            Log.v("musicActivity", "seekBar 최대값 지정");
        } catch (Exception e){
            Log.e("SimplePlayer", e.getMessage());
        }
    }



    // seekBar의 progress 음악 재생과 동시에 증가시켜줌
    // 스레드로 상속해줘서 음악 재생과 함께 동작할 수 있도록 해줌
    class ProgressUpdate extends Thread{
        @Override
        public void run() {
            while(isPlaying){ // 음악이 재생중이라면
                try {
                    Thread.sleep(500); // 0.5초마다
                    if(mediaPlayer!=null){ // 음악이 없지 않다면
                        // 현재 음악 재생 지점으로 progress 지정해줌(곧 계속 증가됨)
                        musicSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                    }
                } catch (Exception e) {
                    Log.e("ProgressUpdate",e.getMessage());
                }

            }
        }
    }

    // 앱 종료 시, 음악 멈추고, 음악 실행 되었던거 삭제해줌
    @Override
    protected void onDestroy() {
        super.onDestroy();
        isPlaying = false;
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}