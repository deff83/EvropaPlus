package com.example.evropaplus.Evropa;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Song {
    private String song;
    private String artist;
    private Long time_st;
    private String url_img = "";


    public Song(String song, String artist, Long time_st) {
        this.song = song;
        this.setArtist(artist);
        this.time_st = time_st;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getTime_st() {
        return time_st;
    }

    public void setTime_st(long time_st) {
        this.time_st = time_st;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public String getDataSong(){
        String time_st_string = "no data";
        if (time_st != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss z");
            Date dateSong = new Date(time_st * 1000);
            time_st_string = dateFormat.format(dateSong);
            System.out.println("time_st:" + time_st_string);
        }
        return time_st_string;
    }
}
