package com.ecs160group.pacman;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;


public class sound{

    Context context;
    public MediaPlayer mp, mpBeg, mpDeath, mpFruit, mpChomp, mpEatGhost, mpPower;
    //mp.setOnPreparedListener(this);
    //mp.prepareAsync();

    public sound(Context c){
        this.context = c;
        mpBeg = MediaPlayer.create(context, R.raw.pacman_beginning);
        mpDeath = MediaPlayer.create(context, R.raw.pacman_death);
        mpFruit = MediaPlayer.create(context, R.raw.pacman_eatfruit);
        mpChomp = MediaPlayer.create(context, R.raw.pacman_chomp);
        mpEatGhost = MediaPlayer.create(context, R.raw.pacman_eatghost);
        mpPower = MediaPlayer.create(context, R.raw.pacman_extrapac);

    }

    public void pacmanBeginning() {
        //mpBeg.prepare();
        mpBeg.start();

    }
    public void pacmanDeath() {
        mpDeath.start();
    }
    public void pacmanEatFruit() {
        mpFruit.start();
    }
    public void pacmanChomp() {
        mpChomp.start();
        //mpChomp.setLooping(true);
}
    public void pacmanEatGhost() {
        mpEatGhost.start();
    }
    public void pacmanPowerup() {
        mpPower.start();
    }
    public void pacmanIntermission() {
        //mp = MediaPlayer.create(context, R.raw.pacman_intermission);
        //mp.start();
    }

    public void onPrepared(MediaPlayer player) {
        player.start();
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
