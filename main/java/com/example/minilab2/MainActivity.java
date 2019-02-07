package com.example.minilab2;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import java.util.*;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    public TextView text_view;
    public long sec, ms, elapsed_time, previous_time;
    public boolean pause_flag, start_flag;
    public String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_view = (TextView)findViewById(R.id.textView);
        time = "00:000";
        text_view.setText(time);
        sec = 0;
        ms = 0;
        previous_time = 0;
        elapsed_time = 0;
        start_flag = true;
    }

    public void start(View v){
        pause_flag = false;

        if(start_flag) {
            previous_time = System.currentTimeMillis();
            start_flag = false;
        }
        else
            previous_time = System.currentTimeMillis() - elapsed_time;

        new Thread(new Runnable() {
            public void run(){
                while(!pause_flag) {
                    elapsed_time = System.currentTimeMillis() - previous_time;

                    ms = elapsed_time % 1000;
                    sec = elapsed_time / 1000;

                    String ms_str = Long.toString(ms);
                    String sec_str = Long.toString(sec);

                    if (ms_str.length() == 1)
                        ms_str = "00" + ms_str;
                    else if (ms_str.length() == 2)
                        ms_str = "0" + ms_str;

                    if (sec_str.length() == 1)
                        sec_str = "0" + sec_str;

                    time = sec_str + ":" + ms_str;

                    text_view.post(new Runnable() {
                        public void run() {
                            text_view.setText(time);
                        }
                    });
                    SystemClock.sleep(50);
                }
            }
        }).start();
    }

    public void pause_resume(View v){
        pause_flag = !pause_flag;

        if(!pause_flag)
            start(text_view);
    }

    public void reset(View v){
        pause_flag = true;
        start_flag = true;
        time = "00:000";
        text_view.setText(time);
    }

}