package com.caitlynwiley.audiodemo;

import android.content.Context;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer player;
    SeekBar volumeControls;
    SeekBar scrub;
    AudioManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player = MediaPlayer.create(this, R.raw.thunder);
        player.setLooping(true);

        manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVol = manager.getStreamVolume(AudioManager.STREAM_MUSIC);

        volumeControls = findViewById(R.id.volume);
        volumeControls.setMax(maxVolume);
        volumeControls.setProgress(currentVol);
        volumeControls.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                manager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        int length = player.getDuration();

        scrub = findViewById(R.id.scrub);
        scrub.setMax(length);
        scrub.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    player.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                pause(null);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                play(null);
            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                scrub.setProgress(player.getCurrentPosition());
            }
        }, 0, 10);
    }

    public void play(View view) {
        player.start();
    }

    public void pause(View view) {
        player.pause();
    }
}
