package com.example.evropaplus.Evropa;

public class Stroka {
    private String strok = "";
    private String pervod = "";

    public Stroka(String strok, String pervod) {
        this.strok = strok;
        this.pervod = pervod;
    }

    public String getStrok() {
        return strok;
    }

    public void setStrok(String strok) {
        this.strok = strok;
    }

    public String getPervod() {
        return pervod;
    }

    public void setPervod(String pervod) {
        this.pervod = pervod;
    }

    @Override
    public String toString() {
        return "Stroka{" +
                "strok='" + strok + '\'' +
                ", pervod='" + pervod + '\'' +
                '}';
    }
}
