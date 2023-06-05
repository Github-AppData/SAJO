package com.example.main;

// 차트 Setter, Getter 있는 클래스
public class Chart {

    private int rank;
    private String name;
    private String singer;


    public Chart(int rank, String name, String singer){
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

