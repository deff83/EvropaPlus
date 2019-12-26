package com.example.evropaplus;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.evropaplus.Evropa.EvropaInfo;
import com.example.evropaplus.Evropa.Song;

import java.sql.Time;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.zip.Inflater;

public class ServiceEvropaPlus extends Service {

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    private WindowManager wm;
    private WindowManager.LayoutParams wmLayout;
    private int pol_x = 0;
    private int pol_y = 0;
    LinearLayout lay_widjet;
    TextView text_song_widjet;

    public ServiceEvropaPlus() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sp = getSharedPreferences("EVROPAPL", Context.MODE_PRIVATE);
        editor = sp.edit();

        wm = (WindowManager) getSystemService(WINDOW_SERVICE) ;
        wmLayout = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        wmLayout.gravity = Gravity.TOP | Gravity.LEFT;
        pol_x = sp.getInt("pol_x", 0);
        pol_y = sp.getInt("pol_y", 0);
        LayoutInflater inflater = LayoutInflater.from(this);
        lay_widjet = (LinearLayout) inflater.inflate(R.layout.widjet_evropa, null);
        text_song_widjet = (TextView) lay_widjet.findViewById(R.id.textSongWidjet);
        wmLayout.x = pol_x;
        wmLayout.y =pol_y;


        wm.addView(lay_widjet, wmLayout);
        try{
            lay_widjet.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int initialX = 0, initialY = 0;
                    float initialTouchX = 0f, initialTouchY = 0f;
                    switch(event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            initialX = wmLayout.x;
                            initialY = wmLayout.y;
                            initialTouchX = event.getRawX();
                            initialTouchY = event.getRawY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            wmLayout.x = initialX + (int) (event.getRawX() - initialTouchX);
                            wmLayout.y = initialY + (int) (event.getRawY() - initialTouchY);
                            wm.updateViewLayout(v, wmLayout);
                            break;
                        case MotionEvent.ACTION_UP:
                            editor.putInt("pol_x", wmLayout.x);
                            editor.putInt("pol_y", wmLayout.y);
                            editor.commit();
                            break;
                    }
                    return false;
                }
            });


        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent!=null) {
            String textSongName = intent.getStringExtra("song");
            text_song_widjet.setText(textSongName);

        }
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        wm.removeView(lay_widjet);
        super.onDestroy();
    }
}
