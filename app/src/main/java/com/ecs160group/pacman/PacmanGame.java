package com.ecs160group.pacman;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/*

    main class for Pacman game

 */

public class PacmanGame extends SurfaceView implements Runnable {
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
        private Pacman mPacman;
        private Ghost mGhost;
        private Maze mMaze;
        private Dpad mDpad;
/*        private Inky mInky;
        private Pinky mPinky;
        private Blinky mBlinky;
        private Clyde mClyde;*/
        //GHOST WILL BE REPLACED LATER WITH BLINKY/INKY/CLYDE/PINKY

        private int mScore; //users score/points by eating pellets/fruits/scared ghosts
        private int mLives; //number lives user has left

        //variables to handle drawing
        private SurfaceHolder mOurHolder;
        private Canvas mCanvas;
        private Paint mPaint;

        //thread + control variables to know when to stop/start the thread
        private Thread mGameThread = null;
        private volatile boolean mPlaying;
        private boolean mPaused = true;

        //constructor
        public PacmanGame(Context context, int x, int y){
            //Super... calls the parent class
            //constructor of SurfaceView provided by Android
            super(context);

            mScreenX = x;
            mScreenY = y;


            mFontSize = mScreenX / 30; //5%(1/20) of screen width
            mFontMargin = mScreenX / 75; //1.5%(1/75) of scren width

            mOurHolder = getHolder();
            mPaint = new Paint();

            //Initialize objects(maze, pacman, ghost);

            //start the game LETS GET PACCING
            startNewGame();

        }

        //runs when a player lost all lives and restarts
        //or starting the first game
        private void startNewGame() {
                //reset maze level

                //resetting score/lives
                mScore = 0;
                mLives = 3;


        }
        // When we start the thread with:
        // mGameThread.start();
        // the run method is continuously called by Android
        // because we implemented the Runnable interface
        // Calling mGameThread.join();
        // will stop the thread
        @Override
        public void run(){
                while (mPlaying) {
                        //time at start of loop
                        long frameStartTime = System.currentTimeMillis();

                        if(!mPaused){
                                update();
                                detectCollisions();

                        }

                        //redraw grid/ghosts/pacman/pellets
                        draw();

                        //time of loop/frame
                        long frameTime = System.currentTimeMillis() - frameStartTime;

                        //>0 condition to prevent divide by zero crashes
                        if(frameTime > 0) {
                                //store in mFPS to pass to update methods of pacman/ghosts
                        }
                }
        }

        //updates pacman/ghosts/pellets/maze
        private void update() {
                //TODO: add update methods to pacman/ghost/maze classes OR do it here

        }

        //detects if pacman hit a pellet/ghost/wall
        private void detectCollisions(){
                //TODO: separate detection collision methods within pacman/ghost/maze classes OR do it here
                //don't need to worry about this for now
        }
        //called by PacmanActivity when player quits game
        public void pause() {
                mPlaying = false;
                try{
                        //stop thread
                        mGameThread.join();
                } catch (InterruptedException e) {
                        Log.e("Error:", "joining thread");
                }
        }

        //called by PacmanActivity when player starts game
        public void resume() {
                mPlaying = true;

                //creates and starts a new game thread
                mGameThread = new Thread(this);
                mGameThread.start();

        }
        //draw game objects and HUD
        private void draw() {
                if (mOurHolder.getSurface().isValid()) {
                        //Lock canvas (graphics memory) ready to draw
                        mCanvas = mOurHolder.lockCanvas();

                        //fill screen with solid color
                        mCanvas.drawColor(Color.argb(255, 0, 0, 0));

                        mPaint.setColor(Color.argb(255, 255, 255, 0));

                        //draw pacman/ghosts/maze/dpad

                        //font size
                        mPaint.setTextSize(mFontSize);

                        //HUD
                        mCanvas.drawText("Score:  " + mScore +
                                        "  Lives: " + mLives,
                                mFontMargin, mFontSize, mPaint);

                        if (DEBUGGING) {
                                printDebuggingText();
                        }

                        mOurHolder.unlockCanvasAndPost(mCanvas);

                }
        }

                private void printDebuggingText(){
                        int debugSize = mFontSize / 2;
                        int debugStart = 150;
                        mPaint.setTextSize(debugSize);
                        mCanvas.drawText("FPS: " + mFPS ,
                                10, debugStart + debugSize, mPaint);
                }



        }








