package com.example.evropaplus;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.evropaplus.Evropa.EvropaInfo;
import com.example.evropaplus.Evropa.FindSong;
import com.example.evropaplus.Evropa.Song;
import com.example.evropaplus.Evropa.SongSostoyan;
import com.example.evropaplus.Evropa.Stroka;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServicePost extends Service {

    private Timer songEvropaTimer = new Timer();

    private EvropaInfo evropa = new EvropaInfo();

    public ServicePost() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        songEvropaTimer.schedule(new SostoyanSongEvropaTask(), 2000, 2000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        songEvropaTimer.cancel();
        super.onDestroy();
    }

    class SostoyanSongEvropaTask extends TimerTask {
        @Override
        public boolean cancel() {
            return super.cancel();
        }

        @Override
        public long scheduledExecutionTime() {
            return super.scheduledExecutionTime();
        }

        @Override
        public void run() {
            try {
                String responce = evropa.getResponce();
                Song song = evropa.getSongLast(responce);
                String text = song.getArtist() + ":" + song.getSong();
                System.out.println(text) ;
                Intent intent_serviceEvropa = new Intent(getApplicationContext(), ServiceEvropaPlus.class);
                intent_serviceEvropa.putExtra("song", text);
                startService(intent_serviceEvropa);

                SongSostoyan songSostoyan = SongSostoyan.getInstance();


                List<Song> songList = evropa.getLastSongs(responce);
                songSostoyan.setPlayListItems(songList);


                if (songSostoyan.getSongText().equals(text)){

                }else{
                    songSostoyan.setTextSong(song);
                    songSostoyan.setListPerevod(new ArrayList<Stroka>());
                    Song testSong = new Song(song.getSong(),song.getArtist(), null);
                    try {
                        FindSong findURLtextSong = evropa.getSsilkSong(testSong);
                        List<Stroka> textSongStroka = evropa.getTextSong(findURLtextSong);
                        songSostoyan.setListPerevod(textSongStroka);
                    }catch (Exception ex){ex.printStackTrace();}

                    Public.getPublish().changeSong(song);
                }

                //Halsey : Nightmare
                //String textSong = evropa.getTextSong(song);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
