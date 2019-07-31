package com.ecs160group.pacman;

import android.content.Context;
import android.graphics.Bitmap;
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
import android.graphics.BitmapFactory;



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
        private Inky mInky;
        private Pinky mPinky;
        private Blinky mBlinky;
        private Clyde mClyde;
        //GHOST WILL BE REPLACED LATER WITH BLINKY/INKY/CLYDE/PINKY

        private Score mScore; //users score/points by eating pellets/fruits/scared ghosts
        private int mLives; //number lives user has left

        //variables to handle drawing
        private Bitmap bitmap;
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
        public sound PacmanGameStart;

        private Boolean updatePacman;

        private Location mGrid;
        private int xPac; // Sote loc.x & loc.y coordinates
        private int yPac;
        //int pacmanGridValues[];
        Location pacmanGridValues;

        // Not needed anymore, Maze will deal with win condition.
        ///private int pellet; // Eventually needs to be deleted TODO: Let maze handle win condition, check if no more pellets.
        //private int MAX_PELLETS;

       // Block mBlock;

        public float PacGhostRadius;

        private int xScaled;
        private int yScaled;

        //constructor
        public PacmanGame(Context context, int x, int y) {
                //Super... calls the parent class
                //constructor of SurfaceView provided by Android

                super(context);
                activityContext = context;

                fakePosition = new PointF(200, 700);
                blockSize = new PointF();
                blockSize.x = (float) x / 55;
                blockSize.y = (float) x / 55;
                mScreenX = x;
                mScreenY = y;

                xScaled = mScreenX / 2;
                yScaled = mScreenY / 12;

                mFontSize = mScreenX / 30; //5%(1/20) of screen width
                mFontMargin = mScreenX / 75; //1.5%(1/75) of scren width


                //Setup sound
                PacmanSounds = new sound(activityContext);

                mScore = new Score();

                mOurHolder = getHolder();
                mPaint = new Paint();
                joystickX = 0;
                joystickY = 0;
                
                //set flags for Pacman and Ghost
                boolean updatePacman = true;
                boolean updateGhost = true;
                
                //Initialize objects(maze, pacman, ghost, joystick)
                PacGhostRadius = (float) (mScreenX + mScreenY) / 200;

                //initialize maze first so pacman and ghost can use its grid to find initial location
                mMaze = new Maze(activityContext, mScreenX, mScreenY, blockSize);

                mPacman = new Pacman(mScreenX, mMaze.pacSpawn, PacGhostRadius, mScore, activityContext);
                mGhost = new Ghost(mScreenX, mMaze.ghostSpawn, mMaze);
                //mInky = new Inky(mGhost);
                //mPinky = new Pinky(mGhost);
                //mBlinky = new Blinky(mGhost);
                //mClyde = new Clyde(mGhost);

                mFakeJoy = new FakeJoy(200, 100, blockSize, fakePosition);
                //bitmap
                bitmap = Bitmap.createBitmap(mScreenX, mScreenY, Bitmap.Config.ARGB_8888);
                mCanvas = new Canvas(bitmap);

                //start the game LETS GET PACCING
              //  update(true, true);
               // draw();
                startNewGame();

        }

        //runs when a player lost all lives and restarts
        //or starting the first game
        public void startNewGame() {
                //reset maze level

                //initialize the position of pacman and ghosts, also resets timers and states
                actorReset();
                //resetting score/lives/direction/pellets
                mScore.reset();
                mLives = 3;
                //pellet = 0; // TODO: Have maze check for not pellets instead.
                mFakeJoy.setCenter();
                mPacman.updateNextDirection('l');

                // Should reset the Maze, refresh pellets
                mMaze = new Maze(activityContext, mScreenX, mScreenY, blockSize);

                //draw();
                PacmanSounds.pacmanBeginning();
                mPaused = true; // Resume on user touch
                 //pauseStartDeath(5000);
                //PacmanSounds.pacmanIntermission();

        }

        public void deathRestart(){
                //reset maze level
                mPaused = true; // Resume on user touch
                //initialize the position of pacman and ghosts, also resets timers and states
                actorReset();
                mLives--;
                //Reset game if 0 lives.
                if(mLives <= 0){ startNewGame(); }
                //pauseStartDeath(3000); // In milliseconds

        }
        public void StageCleared(){
                //initialize the position of pacman and ghosts, also resets timers and states
                mPaused = true; // Resume on user touch
                actorReset();
                //resetting /States/Direction
                //pellet = 0; // New map, reset pellet counter
                mGhost.setDeathState(0, false);
                // Movement reset.
                mFakeJoy.setCenter();

                // In this case we just reset the maze,
                // TODO: Add more levels.
                //mMaze = new Maze(activityContext, mScreenX, mScreenY);
                //pauseStartDeath(5000);
                PacmanSounds.pacmanBeginning();


        }

        public void actorReset(){
            mGhost.reset(); // TODO: Remove later
            //mInky.reset();
            //mPinky.reset();
            //mBlinky.reset();
            //mClyde.reset();
            mPacman.reset();
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
                                //Boolean updateGhost = mGhost.wallDetection(mMaze);

                                //if(frameStartTime % 2 == 0) {
                                        updatePacman = mPacman.wallDetection(mMaze);
                                        update(updatePacman, true);
                                        detectCollisions();
                                        //Determines powerup state of pacman powerTimer decrements on every frame.
                                        mPacman.checkPowerUpState();
                                        mGhost.checkDeathTimer();
                                //}
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
        private void update(boolean updatePacman, boolean updateGhost) {
                //TODO: add update methods to pacman/ghost/maze classes OR do it here
                
                // now has a if-condition to check whether Pacman and ghost will hit a wall
                // so there is no need for update
                //Log.d("update: ", "Updating pacmman/ghost: ");
                //Log.d("update: ", "pacmanLoc: " + "Location: " + mPacman.loc.getX() + "," + mPacman.loc.getY());
                //Log.d("update: ", "pacmanLoc: " + "GridLocation: " + mPacman.getGridLoc().getX() + "," + mPacman.getGridLoc().getY());

                if (updatePacman)
                        mPacman.update(mFPS);
                if (updateGhost)
                        mGhost.update(mFPS);

        }

        //detects if pacman hit a pellet/ghost/wall
        private void detectCollisions() {

                //TODO: separate detection collision methods within pacman/ghost/maze classes OR do it here
                //pacman && ghost collision (add condition for super mode later)

                // Eventually, must use Location to detect collion not screen position.
                //if (mPacman.detectCollision(mGhost.loc, mScreenX, mScreenY)) {

                if (mPacman.getGridLoc().getX() == mGhost.getGridLoc().getX() &&
                        mPacman.getGridLoc().getY() == mGhost.getGridLoc().getY()) {
                        //Pacman dies, respawns without super mode
                        if(mPacman.getPowerState() == false && mPacman.getPowerTimer() >= 0){
                                PacmanSounds.pacmanDeath();
                                draw();
                                pauseStartDeath(3000);
                                mFakeJoy.setCenter();
                                draw();
                                deathRestart();

                        }
                        else{
                                // Set timer as ghost touches Graveyard?
                                PacmanSounds.pacmanEatGhost();
                                mScore.ateGhost();
                                pauseStartDeath(500);
                                mGhost.setDeathState(9000, true);
                                mGhost.gridLocation.setNewLoc(13, 11);
                                //Pacman is able to eat ghosts
                                //Deal with ghost returning to graveyard.
                                //mghost.moveTowardsTarget();
                        }
                }


                // Handle collisions with Pacman
                mPacman.collisionInteraction(null, null, null, null, mMaze, mScore); // Null for debugging.
                //mPacman.collisionInteraction(mInky, mPinky, mBlinky, mClyde, mMaze);
                // Check interactions, then win condition.
                if(mPacman.hasWon(mMaze) == true){
                        //Log.d("Debugging", "In Collision Interact: PELLET, STAGE COMPLETE");
                        draw();
                        PacmanSounds.pacmanChomp(); // Consume pellet..
                        pauseStartDeath(4000);
                        mFakeJoy.setCenter();
                        draw();
                        StageCleared();
                } // Win condition reached. All pellets cleared.

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
                        mCanvas.drawText("Score:  " + mScore.getScore() +
                                        "  Lives: " + mLives,
                                mFontMargin, mFontSize, mPaint);

                        // draw all member objects
                        mMaze.draw(mCanvas, mPaint);
                        mFakeJoy.draw(mCanvas, mPaint);

                        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.pacman);


                        Bitmap sizedB = Bitmap.createScaledBitmap(b, (int) PacGhostRadius * 2,
                                (int) PacGhostRadius * 2, false);


                        mCanvas.drawBitmap(sizedB, mPacman.gridLocation.getX() * 28 + xScaled - PacGhostRadius,
                                mPacman.gridLocation.getY() * 28 + yScaled - PacGhostRadius, null);

                        //mCanvas.drawBitmap(sizedB, mPacman.loc.getX() - PacGhostRadius,
                         //       mPacman.loc.getY() - PacGhostRadius, null);

                        b = BitmapFactory.decodeResource(getResources(), R.drawable.blinky);

                        sizedB = Bitmap.createScaledBitmap(b, (int) PacGhostRadius * 2,
                                (int) PacGhostRadius * 2, false);

                        mCanvas.drawBitmap(sizedB, (mGhost.gridLocation.getX() * 28 + xScaled) - PacGhostRadius,
                                (mGhost.gridLocation.getY() * 28 + yScaled) - PacGhostRadius, null);

                        mPaint.setColor(Color.argb(255, 0, 0, 255));
                        //redPaint.setColor(Color.argb(0,255, 0, 0));
                        // Draw the vertical lines of the maze*/


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
                mCanvas.drawText("mGhost.gridLocation.x: " + mGhost.gridLocation.getX(),
                        10,debugStart + debugSize + 90, mPaint);
                mCanvas.drawText("mGhost.gridLocation.x: " + mGhost.gridLocation.getY(),
                        10,debugStart + debugSize + 120, mPaint);
                mCanvas.drawText("xScaled: " + xScaled,
                        10,debugStart + debugSize + 150, mPaint);
                mCanvas.drawText("yScaled: " + yScaled,
                        10,debugStart + debugSize + 180, mPaint);
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

        // In milliseconds..
        void pauseStartDeath(int t){
                try {
                       // mPaused = true;
                        Thread.sleep(t);
                } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                //mPaused = false;

        }
}







