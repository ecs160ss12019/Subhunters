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

    public Context activityContext;
    public void setContext(Context c){
        activityContext = c;

    }

    public void pacmanBeginning() {
        //Context activityContext = getApplicationContext();
        //Context activityContext = activityContext;
        final MediaPlayer PacmanDeath = MediaPlayer.create(activityContext, R.raw.pacman_beginning);
        PacmanDeath.start();
    }



}

/*
public class sound {
    void playClip(String fileLoc){
        try{
            File filePath = new File(fileLoc);
            if(filePath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(filePath);
                Clip clip = AudioSystem.getClip();
                clip.start();

            }else{
                System.out.println("Audio File not found");
            }
        }
        catch(Exception e){ e.PrintStackTrace();}
    }
}

*/

/*
public class sound {

    public static void main() {

        File pacmanDeath = new File("/../../../../res/sounds/pacman_beginning.wav");
        playSound(pacmanDeath);

    }

    static void playSound (File sound) {

        try {
            // open the sound file as a Java input stream
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
            clip.start();

            Thread.sleep(clip.getMicrosecondLength() / 1000);
        } catch (Exception e){

        }
    }
}

*/


/*
public class sound
{
    public static void main(String[] args)
            throws Exception
    {
        try {
            // open the sound file as a Java input stream
            String gongFile = "/../../../../res/sounds/pacman_beginning.wav";
            InputStream in = new FileInputStream(gongFile);

            // create an audiostream from the inputstream
            sound audioStream = new sound(in);

            // play the audio clip with the audioplayer class
            sound.player.start(audioStream);
        }
        catch ()
    }
}
*/

/*
public class Sound implements Playable {

    private final Path wavPath;
    private final CyclicBarrier barrier = new CyclicBarrier(2);

    Sound(final Path wavPath) {

        this.wavPath = wavPath;
    }

    @Override
    public void play() throws LineUnavailableException, IOException, UnsupportedAudioFileException {

        try (final AudioInputStream audioIn = AudioSystem.getAudioInputStream(wavPath.toFile());
             final Clip clip = AudioSystem.getClip()) {

            listenForEndOf(clip);
            clip.open(audioIn);
            clip.start();
            waitForSoundEnd();
        }
    }

    private void listenForEndOf(final Clip clip) {

        clip.addLineListener(event -> {
            if (event.getType() == LineEvent.Type.STOP) waitOnBarrier();
        });
    }

    private void waitOnBarrier() {

        try {

            barrier.await();
        } catch (final InterruptedException ignored) {
        } catch (final BrokenBarrierException e) {

            throw new RuntimeException(e);
        }
    }

    private void waitForSoundEnd() {

        waitOnBarrier();
    }
}
*/