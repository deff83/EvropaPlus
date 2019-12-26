package com.example.evropaplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.evropaplus.Evropa.EvropaInfo;
import com.example.evropaplus.Evropa.FindSong;
import com.example.evropaplus.Evropa.Song;
import com.example.evropaplus.Evropa.SongSostoyan;
import com.example.evropaplus.Evropa.Stroka;

import java.util.ArrayList;
import java.util.List;

public class SongActivity extends AppCompatActivity  {
    private String artist;
    private String song;
    private TableLayout tableLaySondText;
    private  NestedScrollView n;
    private Song songGet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        Intent intent = getIntent();
        artist = intent.getStringExtra("artist");
        song = intent.getStringExtra("song");

        tableLaySondText = (TableLayout) findViewById(R.id.textSongAct);
        LayoutInflater inflater = LayoutInflater.from(this);
        n = (NestedScrollView) findViewById(R.id.scrollabl);

        inflate_card_song(inflater);
        Public publisher = Public.getPublish();
        new SongTask().execute();


    }

    private void inflate_card_song(LayoutInflater lInflater){
        LinearLayout cardView_Tek = (LinearLayout) lInflater.inflate(R.layout.card_song_item, n, false);
        TextView textView2ArtistSongLast = cardView_Tek.findViewById(R.id.textView2ArtistSongLast);
        TextView textView2NameSongLast = cardView_Tek.findViewById(R.id.textView2NameSongLast);
        ImageView image = cardView_Tek.findViewById(R.id.imageView2CardSong);
        textView2ArtistSongLast.setText(artist);
        textView2NameSongLast.setText(song);
        tableLaySondText.addView(cardView_Tek);
    }

    private void setTextSongView(){
        List<Stroka> textSong = SongSostoyan.getInstance().getListPerevod();
        Song tek_song = SongSostoyan.getInstance().getTextSong();
        tableLaySondText.removeAllViews();
        LayoutInflater lInflater = LayoutInflater.from(this);
        inflate_card_song(lInflater);
        if (textSong.size()==0){
            TextView not_found = new TextView(this);
            not_found.setText("Not Found");
            tableLaySondText.addView(not_found);
        }
        for (int i = 0; i < textSong.size(); i++) {
            TableRow ll = (TableRow) lInflater.inflate(R.layout.song_text, tableLaySondText, false);
            TextView textViewOrigin = ll.findViewById(R.id.textView1song);
            TextView textViewPerevod = ll.findViewById(R.id.textView2song);
            textViewOrigin.setText(textSong.get(i).getStrok());
            textViewPerevod.setText(textSong.get(i).getPervod());
            //textPerevod = textPerevod + textSong.get(i).getStrok() + " : " + textSong.get(i).getPervod() + "\n";
            tableLaySondText.addView(ll);
        }
    }

    public class SongTask extends AsyncTask<Void, Integer, Void>{
        public SongTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            setTextSongView();
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Song songFind = new Song(song, artist, null);
            EvropaInfo evropa = new EvropaInfo();
            SongSostoyan songSostoyan = SongSostoyan.getInstance();
            try {
                FindSong findURLtextSong = evropa.getSsilkSong(songFind);
                List<Stroka> textSongStroka = evropa.getTextSong(findURLtextSong);
                songSostoyan.setListPerevod(textSongStroka);
            }catch (Exception ex){ex.printStackTrace();songSostoyan.setListPerevod(new ArrayList<Stroka>());}
            return null;
        }
    }



}
