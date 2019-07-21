package com.ecs160group.pacman;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/*

    main class for Pacman game

 */

public class PacmanGame extends SurfaceView implements Runnable{
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

        private float joystickX;
        private float joystickY;

        //game objects
        private Pacman mPacman;
        private Ghost mGhost;
        private Maze mMaze;
        private Joystick mJoystick;
        private FakeJoy mFakeJoy;
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
        private PointF blockSize;
        final private PointF fakePosition;

        //thread + control variables to know when to stop/start the thread
        private Thread mGameThread = null;
        private volatile boolean mPlaying;
        private boolean mPaused = true;

        //constructor
        public PacmanGame(Context context, int x, int y) {
                //Super... calls the parent class
                //constructor of SurfaceView provided by Android

                super(context);

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
                //mJoystick = new Joystick(context);
                mFakeJoy = new FakeJoy(200, 100, blockSize, fakePosition);


                //Initialize objects(maze, pacman, ghost);

                //start the game LETS GET PACCING
                startNewGame();

        }

        //runs when a player lost all lives and restarts
        //or starting the first game
        public void startNewGame() {
                //reset maze level

                //initialize the position of pacman and ghosts
                mPacman.reset(mScreenX, mScreenY);
                mGhost.reset(mScreenX, mScreenY);

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
        public void run() {
                while (mPlaying) {
                        //time at start of loop
                        long frameStartTime = System.currentTimeMillis();

                        if (!mPaused) {
                                update();
                                detectCollisions();

                        }

                        //redraw grid/ghosts/pacman/pellets
                        draw();

                        //time of loop/frame
                        long frameTime = System.currentTimeMillis() - frameStartTime;

                        //>0 condition to prevent divide by zero crashes
                        if (frameTime > 0) {
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
                //TODO: separate detection collision methods within pacman/ghost/maze classes OR do it here
                //pacman && ghost collision (add condition for super mode later)
                if (mPacman.loc.getX() == mGhost.loc.getY()
                && mPacman.loc.getY() == mGhost.loc.getY()) {
                        //Pacman dies, respawns
                        mLives--;
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

                        //fill screen with solid color
                        mCanvas.drawColor(Color.argb(255, 0, 0, 0));

                        mPaint.setColor(Color.argb(255, 255, 255, 0));

                        //font size
                        mPaint.setTextSize(mFontSize);

                        //HUD
                        mCanvas.drawText("Score:  " + mScore +
                                        "  Lives: " + mLives,
                                mFontMargin, mFontSize, mPaint);

                        //draw pacman/ghosts/maze/dpad
                        // white color for pacman
                        mPaint.setColor(Color.argb(255, 255, 255, 255));
                        // draw pacman as circle
                        mCanvas.drawCircle(mPacman.loc.getX(), mPacman.loc.getY()
                                , (mScreenX + mScreenY) / 200, mPaint);
                        // blue color for ghosts
                        mPaint.setColor(Color.argb(255, 0, 0, 255));
                        // draw ghosts as circle
                        mCanvas.drawCircle(mGhost.loc.getX(), mGhost.loc.getY()
                                , (mScreenX + mScreenY) / 200, mPaint);

                        mFakeJoy.draw(mCanvas, mFakeJoy.centerX, mFakeJoy.centerY);

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
        }


        Pacman getPacman() {
                return mPacman;
        }

       /*  // When controller touched this will be called.
        @Override
        public void JoystickMoved(float xPercent, float yPercent) {
                Log.d("User-Controller: ", "X percent: " + xPercent + " Y percent: " + yPercent);
                // include pacman controls here, Must convert percent values into directional values up,down,left,right
               // mPacmanGame.getPacman().updateNextDirection(xPercent, yPercent);
                joystickX = xPercent;
                joystickY = yPercent;
        }*/

        @Override
        public boolean onTouchEvent(MotionEvent e) {


//TODO: FIX UPDATE FUNCTIONS TO NOT USE CANVAS??
            mCanvas = mOurHolder.lockCanvas();
                mPaused = false;
                //Touch coordinates are scaled to be values between 0-100
                float scaledX = e.getX() / blockSize.x;
                float scaledY = e.getY() / blockSize.x;

                //if (e.getAction() == e.ACTION_MOVE || e.getAction() == e.ACTION_DOWN) {
                if ((e.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                        mFakeJoy.update(scaledX, scaledY, mCanvas);
                } else {
                        // mFakeJoy.reset();
                }
            mOurHolder.unlockCanvasAndPost(mCanvas);
                return true;

        }

}







