package com.example.main;


// TODO : 차트에서 ‘좋아요’로 이동 자신의 플레이리스트로 추가할 수 있는 기능
// Playlist Setter, Getter 있는 클래스
public class Playlist {
    private int rank;
    private String name;
    private String singer;


    public Playlist(int rank, String name, String singer){
        this.rank = rank;
        this.name = name;
        this.singer = singer;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }
}
