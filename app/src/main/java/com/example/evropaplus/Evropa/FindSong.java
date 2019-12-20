package com.example.evropaplus.Evropa;

public class FindSong {
    private String url = "";
    private String name = "";
    private String artist = "";

    public FindSong(String url, String name, String artist) {
        this.url = url;
        this.name = name;
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "FingSongs{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }
}
