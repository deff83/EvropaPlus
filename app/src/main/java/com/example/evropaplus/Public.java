package com.example.evropaplus;

import com.example.evropaplus.Evropa.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Public extends Observable {
    private static Public aPublic = new Public();
    private Public(){}

    public static Public getPublish(){return aPublic;}

    public void changeSong(Song song){
        setChanged();
        notifyObservers(song);
    }
}
