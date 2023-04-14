package com.example.main;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    // 선언
    Button btn_chart, btn_study;
    LinearLayout chart_layout, study_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Chart Fragment 인스턴스 생성
        ChartFragment chartfragment = new ChartFragment();

        // Fragment를 FrameLayout에 추가
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.tab_chart_frag, chartfragment)
                .commit();

        // Study Fragment 인스턴스 생성
        StudyFragment studyfragment = new StudyFragment();

        // Fragment를 FrameLayout에 추가
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.tab_study_frag, studyfragment)
                .commit();

        // 아이디 부여
        btn_chart = (Button) findViewById(R.id.btn_chart);
        btn_study = (Button) findViewById(R.id.btn_study);
        chart_layout = (LinearLayout) findViewById(R.id.chart_layout);
        study_layout = (LinearLayout) findViewById(R.id.study_layout);

        // 차트 버튼 클릭 시 색상 변경 및 레이아웃 변경
        btn_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(view.isClickable()) {
                    btn_chart.setBackgroundColor(Color.CYAN);
                    btn_chart.setTextColor(Color.BLACK);
                    btn_study.setBackgroundColor(Color.parseColor("#0C2840"));
                    btn_study.setTextColor(Color.WHITE);
                    chart_layout.setVisibility(View.VISIBLE);
                    study_layout.setVisibility(View.GONE);
                }
            }
        });

        // 스터디 버튼 클릭 시 색상 변경 및 레이아웃 변경
        btn_study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(view.isClickable()) {
                    btn_study.setBackgroundColor(Color.CYAN);
                    btn_study.setTextColor(Color.BLACK);
                    btn_chart.setBackgroundColor(Color.parseColor("#0C2840"));
                    btn_chart.setTextColor(Color.WHITE);
                    chart_layout.setVisibility(View.GONE);
                    study_layout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public static class ChartFragment extends Fragment {

        public ChartFragment() {
            // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            // Inflate the layout for this fragment
            View rootView_chart = inflater.inflate(R.layout.fragment_tab_chart, container, false);

            // Fragment의 뷰들을 구성하는 코드 작성
            return rootView_chart;
        }
    }

    public static class StudyFragment extends Fragment {

        public StudyFragment() {
            // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            // Inflate the layout for this fragment
            View rootView_study = inflater.inflate(R.layout.fragment_tab_study, container, false);

            // Fragment의 뷰들을 구성하는 코드 작성
            return rootView_study;
        }
    }
}

