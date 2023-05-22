package com.example.main;

public class Wordbook {
    private String name;
    private String mean;

    public Wordbook(String name, String mean){
        this.mean = mean;
        this.name = name;
    }




    public String getName() {
        return name;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public void setName(String name) {
        this.name = name;
    }

}
