package com.tactical.andy.player;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.media.audiofx.Equalizer;

import com.tactical.andy.allmusic.AllMusic;
import com.tactical.andy.musicplayer.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Player extends AppCompatActivity {

    Bundle songExtraData;
    ArrayList<File> songFileList;
    SeekBar mSeekBar;
    TextView mSongTitle;
    ImageView playBtn;
    ImageView nextBtn;
    ImageView prevBtn;
    ImageView shuffleBtn;
    static MediaPlayer mMediaPlayer;
    int position;
    TextView currentTime;
    TextView totalTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        mSeekBar = findViewById(R.id.musicSeekBar);
        mSongTitle = findViewById(R.id.songTitle);
        playBtn = findViewById(R.id.playBtn);
        prevBtn = findViewById(R.id.prevBtn);
        nextBtn = findViewById(R.id.nextBtn);
        currentTime = findViewById(R.id.currentTimer);
        totalTimer = findViewById(R.id.totalTimer);
        shuffleBtn = findViewById(R.id.shuffle_Btn);

        //check if player is null
        if(mMediaPlayer != null){

            mMediaPlayer.stop();
        }

        final Intent songData = getIntent();
        songExtraData = songData.getExtras();
        songFileList = (ArrayList) songExtraData.getParcelableArrayList("songFileList");

        position = songExtraData.getInt("position", 0);
        initMusicPlayer(position);


        //when play/pause button is clicked

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                play();

            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position < songFileList.size() -1){

                  //checking to see if the current song is the last


                    position++;
                }else{

                    //set position to  zero if it is equal to or greater than song list

                    position = 0;
                }

                initMusicPlayer(position);
            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position <=0 ){


                    position = songFileList.size() -1;
                }else{
                    position--;

                }
                initMusicPlayer(position);
            }
        });



        shuffleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //   Random rand = new Random();
             //   int random = rand.nextInt(position);
             //   String temp = mMediaPlayer.toString();
              //  Random random = new Random();
              //  random.nextInt(position);
              //  onRestart();



            }
        });




    }

    private void initMusicPlayer(final int position){

        if(mMediaPlayer != null && mMediaPlayer.isPlaying()){
            mMediaPlayer.reset();
        }
        String name = songFileList.get(position).getName();
        mSongTitle.setText(name);

        //find song path
        Uri songResourceUri = Uri.parse(songFileList.get(position).toString());

        //create media player
        mMediaPlayer = MediaPlayer.create(getApplicationContext(),songResourceUri);

        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                //set seekerbar maximum duration depending on song
                mSeekBar.setMax(mMediaPlayer.getDuration());


                //Setting String to the length of the song
                String totTime = createTimerLabel(mMediaPlayer.getDuration());
                totalTimer.setText(totTime);


                //start player
                mMediaPlayer.start();


                playBtn.setImageResource(R.drawable.pause_btn);
            }
        });
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //when the song finishes
              //  playBtn.setImageResource(R.drawable.play_btn);

                int currentSongPosition = position;

                if(currentSongPosition < songFileList.size() -1){

                    //checking to see if the current song is the last


                    currentSongPosition++;
                }else{

                    //set position to  zero if it is equal to or greater than song list

                    currentSongPosition = 0;
                }

                initMusicPlayer(currentSongPosition);
            }
        });


        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //when seekbar changes

                if(fromUser){
                    mMediaPlayer.seekTo(progress);
                    mSeekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        //seekerbar changes with length of song
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(mMediaPlayer != null){
                    try{
                        if(mMediaPlayer.isPlaying()){
                            Message message = new Message();
                            message.what = mMediaPlayer.getCurrentPosition();
                            handler.sendMessage (message);
                            Thread.sleep(1000);

                        }
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }

                }
            }
        }).start();




    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            int current_position = msg.what;
            currentTime.setText(createTimerLabel(msg.what)); //setTimer
            mSeekBar.setProgress(msg.what);
        }
    };

    private void play(){

        if(mMediaPlayer != null && mMediaPlayer.isPlaying()){
            mMediaPlayer.pause();
            playBtn.setImageResource(R.drawable.play_btn);
        }else{

            mMediaPlayer.start();
            playBtn.setImageResource(R.drawable.pause_btn);
        }
    }

    public String createTimerLabel(int duration){


        String timerLabel = "";
        int min = duration/1000/60;
        int sec = duration / 1000 % 60;

        timerLabel += min + ":";
        if (sec < 10) timerLabel += "0";
        timerLabel += sec;

        return timerLabel;
    }

   //public int[] shuffleSong(int shuffle){


      // int randomInt = (new Random().nextInt(position);
       //int sound = position.get(randomInt);
       //MediaPlayer mp = MediaPlayer.create(this, sound);
       //mp.start();

  // }


}
