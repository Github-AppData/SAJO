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

    DBSongHelper db;
    ListView song_lists;
    ArrayList<String> listItem;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list);

        song_lists = findViewById(R.id.song_lists);

        db = new DBSongHelper(this);

        viewData();

        song_lists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = song_lists.getItemAtPosition(i).toString();
                Toast.makeText(songActivity.this, ""+text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewData() {
        List<Chart> songList = db.getAllSongs();

        if(songList.isEmpty()){
            Toast.makeText(this, "No", Toast.LENGTH_SHORT).show();
        } else {
            listItem = new ArrayList<>();
            for (Chart chart : songList) {
                listItem.add("Rank: " + chart.getRank());
                listItem.add("Name: " + chart.getName());
                listItem.add("Singer: " + chart.getSinger());
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItem);
            song_lists.setAdapter(adapter);
        }
    }
}
