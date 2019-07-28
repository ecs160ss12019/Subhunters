package com.ecs160group.pacman;


import android.content.Context;
import android.media.MediaPlayer;

import android.provider.MediaStore;
import android.os.Bundle;

import java.io.*;
import java.io.FileInputStream;
//import javax.sound.sampled.*;

//import javax.sound.sampled.AudioInputSystem;
//import javax.sound.sampled.Clip;



public class sound{

    Context context;
    MediaPlayer mp;
    //public Context activityContext;
    public sound(Context c){
        this.context = c;

    }

    public void pacmanBeginning() {
        //Context activityContext = getApplicationContext();
        //Context activityContext = activityContext;
        mp = MediaPlayer.create(context, R.raw.pacman_beginning);
        mp.start();
    }
    public void pacmanDeath() {
        mp = MediaPlayer.create(context, R.raw.pacman_death);
        mp.start();
    }
    public void pacmanEatFruit() {
        mp = MediaPlayer.create(context, R.raw.pacman_eatfruit);
        mp.start();
    }
    public void pacmanChomp() {
        mp = MediaPlayer.create(context, R.raw.pacman_chomp);
        mp.start();
    }
    public void pacmanEatGhost() {
        mp = MediaPlayer.create(context, R.raw.pacman_eatghost);
        mp.start();
    }
    public void pacmanPowerup() {
        mp = MediaPlayer.create(context, R.raw.pacman_extrapac);
        mp.start();
    }
    public void pacmanIntermission() {
        mp = MediaPlayer.create(context, R.raw.pacman_intermission);
        mp.start();
    }



    void playSong(MediaPlayer mp){
        mp.start();
    }
    void pauseSong(MediaPlayer mp) {
        mp.pause();
    }
    void stopSong(MediaPlayer mp) {
        mp.stop();
    }


}
