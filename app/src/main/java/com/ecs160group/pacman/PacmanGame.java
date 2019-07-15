package com.ecs160group.pacman;

import android.content.Context;
import android.view.SurfaceView;

/*

    main class for Pacman game

 */

public class PacmanGame extends SurfaceView {
        //for debugging purposes
        private final boolean DEBUGGING = false;
        private long mFPS; //frames per second
        private final int MILLIS_IN_SECOND = 1000;

        //holds resolution of screen
        private int mScreenX;
        private int mScreenY;
        //how big will the text be?
        private int mFontSize;
        private int mFontMargin;

        //game objects
        private Pacman pacman;
        private Ghost ghost;
        //GHOST WILL BE REPLACED LATER WITH BLINKY/INKY/CLYDE/PINKY

        private int mScore; //users score/points by eating pellets/fruits/scared ghosts
        private int mLives; //number lives user has left

        public PacmanGame(Context context, int x, int y){
            //Super... calls the parent class
            //constructor of SurfaceView provided by Android
            super(context);
        }


}



