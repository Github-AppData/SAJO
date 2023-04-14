package com.example.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SongAdapter extends ArrayAdapter<String[]> {

    // 리스트뷰에 사용할 레이아웃 연결 (기존 안드로이드가 제공하는 것이 아닌 item_list.xml을 만들어 사용했기 때문에)
    public SongAdapter(Context context, List<String[]> songData) {
        super(context, 0, songData);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String[] song = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list, parent, false);
        }

        TextView songRank = convertView.findViewById(R.id.song_rank);
        TextView songTitle = convertView.findViewById(R.id.song_title);
        TextView songArtist = convertView.findViewById(R.id.song_artist);

        songRank.setText(song[0]);
        songTitle.setText(song[1]);
        songArtist.setText(song[2]);

        return convertView;
    }
}
