package com.example.evropaplus;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.evropaplus.Evropa.Song;
import com.example.evropaplus.Evropa.SongSostoyan;
import com.example.evropaplus.Evropa.Stroka;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.zip.Inflater;

public class ScrollingActivity extends AppCompatActivity implements Observer {
    private Intent intent_serviceEvropa, intent_servicePost;
    private Public publisher = Public.getPublish();
    private TableLayout textSongView;
    private View.OnClickListener clickListenerCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);



        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        NavigationView navigationView = (NavigationView) findViewById(R.id.design_navigation_vieww);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                       // .setAction("Action", null).show();
                Intent intent = new Intent(ScrollingActivity.this, JsonInfo.class);
                startActivity(intent);
            }
        });

        SongSostoyan.getInstance().setTextSong(new Song("", "", 0l));

        intent_serviceEvropa = new Intent(this, ServiceEvropaPlus.class);
        intent_serviceEvropa.putExtra("song", "song");
        startService(intent_serviceEvropa);

        intent_servicePost = new Intent(this, ServicePost.class);
        startService(intent_servicePost);
        textSongView = (TableLayout) findViewById(R.id.textSong);

        clickListenerCard = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView2ArtistSongLast = v.findViewById(R.id.textView2ArtistSongLast);
                TextView textView2NameSongLast = v.findViewById(R.id.textView2NameSongLast);
                ImageView image = v.findViewById(R.id.imageView2CardSong);
                Intent intent = new Intent(getApplicationContext(), SongActivity.class);
                intent.putExtra("artist", textView2ArtistSongLast.getText());
                intent.putExtra("song", textView2NameSongLast.getText());
                startActivity(intent);

            }
        };


        publisher.addObserver(this);
        setTextSongView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        try {
            stopService(intent_serviceEvropa);
            stopService(intent_servicePost);
        }catch (Exception e){e.printStackTrace();}
        super.onDestroy();
    }
    private Song song;
    @Override
    public void update(Observable o, Object arg) {
        try {
            song = (Song) arg;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    setTextSongView();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setTextSongView(){
        List<Stroka> textSong = SongSostoyan.getInstance().getListPerevod();
        Song tek_song = SongSostoyan.getInstance().getTextSong();
        textSongView.removeAllViews();
        LayoutInflater lInflater = LayoutInflater.from(this);
        inflate_card_song(lInflater, tek_song);
        if (textSong.size()==0){
            TextView not_found = new TextView(this);
            not_found.setText("Not Found");
            textSongView.addView(not_found);
        }



        for (int i = 0; i < textSong.size(); i++) {
            TableRow ll = (TableRow) lInflater.inflate(R.layout.song_text, null);
            TextView textViewOrigin = ll.findViewById(R.id.textView1song);
            TextView textViewPerevod = ll.findViewById(R.id.textView2song);
            textViewOrigin.setText(textSong.get(i).getStrok());
            textViewPerevod.setText(textSong.get(i).getPervod());
            //textPerevod = textPerevod + textSong.get(i).getStrok() + " : " + textSong.get(i).getPervod() + "\n";
            textSongView.addView(ll);
        }

        List<Song> listSong = SongSostoyan.getInstance().getPlayListItems();
        for (int i = 0; i < listSong.size(); i++) {
            inflate_card_song(lInflater, listSong.get(i));
        }

    }

    private void inflate_card_song(LayoutInflater lInflater, Song tek_song){
        LinearLayout cardView_Tek = (LinearLayout) lInflater.inflate(R.layout.card_song_item, null);
        TextView textView2ArtistSongLast = cardView_Tek.findViewById(R.id.textView2ArtistSongLast);
        TextView textView2NameSongLast = cardView_Tek.findViewById(R.id.textView2NameSongLast);
        ImageView image = cardView_Tek.findViewById(R.id.imageView2CardSong);
        if(tek_song != null) {
            textView2ArtistSongLast.setText(tek_song.getArtist());
            textView2NameSongLast.setText(tek_song.getSong());
            if (!tek_song.getUrl_img().equals("")) {
                Picasso.with(this)
                        .load("http:"+tek_song.getUrl_img())
                        .into(image);
            }

        }
        cardView_Tek.setOnClickListener(clickListenerCard);
        textSongView.addView(cardView_Tek);

    }
}
