package com.example.main;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class songActivity extends AppCompatActivity {

    DBHelper dbhelper;
    ListView song_lists;
    ArrayList<String> listItem;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chart_frag_activity);

        song_lists = findViewById(R.id.song_list);

        dbhelper = new DBHelper(songActivity.this, "userdata.db",null,1);

        viewData();

        song_lists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = song_lists.getItemAtPosition(i).toString();
                Toast.makeText(songActivity.this, "asdasd"+text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewData() {

    }
}
