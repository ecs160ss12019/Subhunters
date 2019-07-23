package com.ecs160group.pacman;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/*

    main class for Pacman game

 */

public class PacmanGame extends SurfaceView implements Runnable{
        //for debugging purposes
        private final boolean DEBUGGING = true;
        private long mFPS; //frames per second
        private final int MILLIS_IN_SECOND = 1000;

        //holds resolution of screen
        private int mScreenX;
        private int mScreenY;
        //how big will the text be?
        private int mFontSize;
        private int mFontMargin;

        private float joystickX;
        private float joystickY;

        //game objects
        private Pacman mPacman;
        private Ghost mGhost;
        private Maze mMaze;
        private FakeJoy mFakeJoy;
/*      private Inky mInky;
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
        private PointF blockSize;
        final private PointF fakePosition;

        //Sounds to be played during gameplay.
        private sound PacmanSounds;
        //thread + control variables to know when to stop/start the thread
        private Thread mGameThread = null;
        private volatile boolean mPlaying;
        private boolean mPaused = true;
        public Context activityContext;
        public MediaPlayer PacmanGameStart;
        //constructor
        public PacmanGame(Context context, int x, int y) {
                //Super... calls the parent class
                //constructor of SurfaceView provided by Android

                super(context);
                activityContext = context;

                //PacmanSounds.setContext(context);
                fakePosition = new PointF(200, 700);
                blockSize = new PointF();
                blockSize.x = (float) x / 100;
                blockSize.y = (float) x / 100;
                mScreenX = x;
                mScreenY = y;

                mFontSize = mScreenX / 30; //5%(1/20) of screen width
                mFontMargin = mScreenX / 75; //1.5%(1/75) of scren width

                mOurHolder = getHolder();
                mPaint = new Paint();
                joystickX = 0;
                joystickY = 0;

                mPacman = new Pacman(mScreenX, 1000, 700);
                mGhost = new Ghost(mScreenX, 800, 400);
                mFakeJoy = new FakeJoy(200, 100, blockSize, fakePosition);

                //Initialize objects(maze, pacman, ghost);

                //start the game LETS GET PACCING
                update();
                draw();
                startNewGame();

        }

        //runs when a player lost all lives and restarts
        //or starting the first game
        public void startNewGame() {
                //reset maze level
                //PacmanGameStart.start();


                //initialize the position of pacman and ghosts
                mPacman.reset(mScreenX, mScreenY);
                mGhost.reset(mScreenX, mScreenY);

                //resetting score/lives/direction
                mScore = 0;
                mLives = 3;
                mFakeJoy.setCenter();
                mPacman.updateNextDirection('l');
                // Play intro music..
                //pauseStartDeath(2000);
                PacmanGameStart = MediaPlayer.create(activityContext, R.raw.pacman_beginning);
                PacmanGameStart.start();
                //pauseStartDeath(4000);
        }

        public void deathRestart(){
                //reset maze level
                //PacmanSounds.pacmanBeginning();
                mPaused = true;
                //initialize the position of pacman and ghosts
                mPacman.reset(mScreenX, mScreenY);
                mGhost.reset(mScreenX, mScreenY);

                //resetting score/lives/States/Direction
                //mScore = 0;
                mLives--;
                mPacman.setPowerUpState(0, false);
                mGhost.setDeathState(0, false);
                mPacman.updateNextDirection('l');

                //mGhost.setDeathState(0, false); // add other ghosts later.

                //Reset game if 0 lives.
                if(mLives == 0){ startNewGame(); }
                //pauseStartDeath(2000); // In milliseconds
                //TODO: Pause the game and resume

        };


        // When we start the thread with:
        // mGameThread.start();
        // the run method is continuously called by Android
        // because we implemented the Runnable interface
        // Calling mGameThread.join();
        // will stop the thread
        @Override
        public void run() {
                while (mPlaying) {
                        //time at start of loop
                        long frameStartTime = System.currentTimeMillis();

                        if (!mPaused) {
                                update();
                                detectCollisions();


                                 //Determines powerup state of pacman powerTimer decrements on every frame.
                                mPacman.checkPowerUpState();
                                mGhost.checkDeathTimer();
                        }

                        //redraw grid/ghosts/pacman/pellets
                        draw();

                        //time of loop/frame
                        long frameTime = System.currentTimeMillis() - frameStartTime;

                        //>0 condition to prevent divide by zero crashes
                        if (frameTime > 0) {
                                mFPS = MILLIS_IN_SECOND / frameTime;
                                //store in mFPS to pass to update methods of pacman/ghosts
                        }
                }
        }

        //updates pacman/ghosts/pellets/maze
        private void update() {
                //TODO: add update methods to pacman/ghost/maze classes OR do it here
                mPacman.update(mFPS);
                mGhost.update(mFPS);

        }

        //detects if pacman hit a pellet/ghost/wall
        private void detectCollisions() {
                //first check if characters are in the maze
                mPacman.isInBounds(mScreenX, mScreenY);

                //TODO: separate detection collision methods within pacman/ghost/maze classes OR do it here
                //pacman && ghost collision (add condition for super mode later)
                if (mPacman.detectCollision(mGhost.loc, mScreenX, mScreenY)) {
                        //Pacman dies, respawns without super mode
                        if(mPacman.getPowerState() == false && mPacman.getPowerTimer() >= 0){
                                PacmanGameStart = MediaPlayer.create(activityContext, R.raw.pacman_death);
                                PacmanGameStart.start();
                                draw();
                                pauseStartDeath(3000);
                                mFakeJoy.setCenter();
                                draw();
                                deathRestart();

                        }
                        else{
                                // Set timer as ghost touches Graveyard?
                                mGhost.setDeathState(0, true);
                                //Pacman is able to eat ghosts
                                //Deal with ghost returning to graveyard.
                                //mghost.moveTowardsTarget();
                        }
                }
                //pacman & pellet

                //pacman & fruit

                //pacman & wall / ghost & wall

        }

        //called by PacmanActivity when player quits game
        public void pause() {
                mPlaying = false;
                try {
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

                        mPaint.setAntiAlias(true);
                        //fill screen with solid color
                        mCanvas.drawColor(Color.argb(255, 0, 0, 0));

                        mPaint.setStyle(Paint.Style.FILL);
                        mPaint.setColor(Color.argb(255, 255, 255, 0));

                        //font size
                        mPaint.setTextSize(mFontSize);

                        //HUD
                        mCanvas.drawText("Score:  " + mScore +
                                        "  Lives: " + mLives,
                                mFontMargin, mFontSize, mPaint);

                        // white color for pacman
                        mPaint.setColor(Color.argb(255, 255, 255, 0));
                        // draw pacman as circle
                        mCanvas.drawCircle(mPacman.loc.getX(), mPacman.loc.getY()
                                , (mScreenX + mScreenY) / 200, mPaint);
                        // blue color for ghosts
                        mPaint.setColor(Color.argb(255, 0, 0, 255));
                        // draw ghosts as circle
                        mCanvas.drawCircle(mGhost.loc.getX(), mGhost.loc.getY()
                                , (mScreenX + mScreenY) / 200, mPaint);

                        mFakeJoy.draw(mCanvas, mPaint);

                        if (DEBUGGING) {
                                printDebuggingText();
                        }

                        mOurHolder.unlockCanvasAndPost(mCanvas);

                }
        }

        private void printDebuggingText() {
                int debugSize = mFontSize / 2;
                int debugStart = 150;
                mPaint.setTextSize(debugSize);
                mCanvas.drawText("FPS: " + mFPS,
                        10, debugStart + debugSize, mPaint);
                /*
                mCanvas.drawText("baseradius.x: " + mFakeJoy.baseRadius,
                        10,debugStart + debugSize + 30, mPaint);
                mCanvas.drawText("stickRadius.x: " + mFakeJoy.stickRadius,
                        10,debugStart + debugSize + 60, mPaint);
                mCanvas.drawText("baseCenter.x: " + mFakeJoy.baseCenter.x,
                        10,debugStart + debugSize + 90, mPaint);
                mCanvas.drawText("baseCenter.y: " + mFakeJoy.baseCenter.y,
                        10,debugStart + debugSize + 120, mPaint);
                mCanvas.drawText("basebaseCenter.x: " + mFakeJoy.baseCenter.x,
                        10,debugStart + debugSize + 150, mPaint);
                mCanvas.drawText("basebaseCenter.y: " + mFakeJoy.baseCenter.y,
                        10,debugStart + debugSize + 180, mPaint);
                */
                mCanvas.drawText("mGhost.loc.x: " + mGhost.loc.getX(),
                        10,debugStart + debugSize + 30, mPaint);
                mCanvas.drawText("mGhost.loc.y: " + mGhost.loc.getY(),
                        10,debugStart + debugSize + 60, mPaint);
                mCanvas.drawText("mPacman.loc.x: " + mPacman.loc.getX(),
                        10,debugStart + debugSize + 210, mPaint);
                mCanvas.drawText("mPacman.loc.y: " + mPacman.loc.getY(),
                        10,debugStart + debugSize + 240, mPaint);
                mCanvas.drawText("mPacman.direction: " + mPacman.direction,
                        10,debugStart + debugSize + 270, mPaint);
                mCanvas.drawText("mFakeJoy.direction: " + mFakeJoy.direction,
                        10,debugStart + debugSize + 300, mPaint);


        }

        @Override
        public boolean onTouchEvent(MotionEvent e) {
                mPaused = false;
                float x = e.getX();
                float y = e.getY();
                if (e.getAction() == e.ACTION_MOVE || e.getAction() == e.ACTION_DOWN) {
                        mFakeJoy.updateStick(x, y);
                        mPacman.updateNextDirection(mFakeJoy.direction);
                } else {
                        mFakeJoy.setCenter();
                }
                return true;
        }

        /**
         * to detect if Pacman lost the game
         * when all the 3 lives were used (lives = 0)
         * @return if Pacman lost
         */
        public boolean hasLost()
        {
                if (mLives == 0)
                        return true;
                return false;
        }

        void playSong(MediaPlayer mp){
                mp.start();
        }

        void pauseSong(MediaPlayer mp) {
                mp.pause();
        }

        void stopSong(MediaPlayer mp) {
                mp.stop();
                //PacmanSounds=MediaPlayer.create(activityContext, R.raw.abcd);
        }
        // In milliseconds..
        void pauseStartDeath(int t){
                try {
                        mPaused = true;
                        Thread.sleep(t);
                } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                mPaused = false;

        }
}







