package com.example.evropaplus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.evropaplus.Evropa.SongSostoyan;

import org.w3c.dom.Text;

import java.util.Observable;
import java.util.Observer;

public class JsonInfo extends AppCompatActivity implements Observer {
    TextView textView_jsonAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_info);
        textView_jsonAnswer = (TextView) findViewById(R.id.textViewJsoj_info);
        textView_jsonAnswer.setText(SongSostoyan.getInstance().getJson_answer());
    }

    @Override
    public void update(Observable o, Object arg) {
        try{
            textView_jsonAnswer.setText(SongSostoyan.getInstance().getJson_answer());
        }catch (Exception e){e.printStackTrace();}
    }
}
