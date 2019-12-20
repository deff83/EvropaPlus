package com.example.evropaplus.Evropa;

import java.util.ArrayList;
import java.util.List;

public class SongSostoyan {
    private static final SongSostoyan ourInstance = new SongSostoyan();

    public static SongSostoyan getInstance() {
        return ourInstance;
    }

    private Song textSong;

    private String json_answer = "";

    private List<Stroka> listPerevod = new ArrayList<>();

    private List<Song> playListItems = new ArrayList<>();

    private SongSostoyan() {
    }

    public List<Stroka> getListPerevod() {
        return listPerevod;
    }

    public void setListPerevod(List<Stroka> listPerevod) {
        this.listPerevod = listPerevod;
    }

    public Song getTextSong() {
        return textSong;
    }

    public void setTextSong(Song textSong) {
        this.textSong = textSong;
    }

    public String getJson_answer() {
        return json_answer;
    }

    public List<Song> getPlayListItems() {
        return playListItems;
    }

    public void setPlayListItems(List<Song> playListItems) {
        this.playListItems = playListItems;
    }

    public void setJson_answer(String json_answer) {
        this.json_answer = json_answer;
    }

    public String getSongText(){
        return textSong.getArtist() + ":" + textSong.getSong();
    }


}
