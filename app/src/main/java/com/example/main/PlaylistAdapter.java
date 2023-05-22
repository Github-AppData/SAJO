package com.example.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.collection.ArraySet;

import java.util.List;

public class PlaylistAdapter extends ArrayAdapter<String[]> {

    // 리스트뷰에 사용할 레이아웃 연결 (기존 안드로이드가 제공하는 것이 아닌 item_playlist.xml을 만들어 사용했기 때문에)
    public PlaylistAdapter(Context context, List<String[]> songData) {
        super(context, 0, songData);
    }

    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String[] playlist = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_playlist, parent, false);
        }

        TextView playlist_title = convertView.findViewById(R.id.playlist_title);

        playlist_title.setText(playlist[0]);


        return convertView;
    }*/
}