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
        private FakeJoy mFakeJoy;
/*      private Inky mInky;
        private Pinky mPinky;
        private Blinky mBlinky;
        private Clyde mClyde;*/
        //GHOST WILL BE REPLACED LATER WITH BLINKY/INKY/CLYDE/PINKY

        private int mScore; //users score/points by eating pellets/fruits/scared ghosts
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
        public MediaPlayer PacmanGameStart;

        private Location mGrid;
        private int xPac; // Grabs loc.x & loc.y coordinates
        private int yPac;

        private int pellet;
        private int MAX_PELLETS;
        Block mBlock;

        private float PacGhostRadius;

        //constructor
        public PacmanGame(Context context, int x, int y) {
                //Super... calls the parent class
                //constructor of SurfaceView provided by Android

                super(context);
                activityContext = context;

                //PacmanSounds.setContext(activityContext);
                fakePosition = new PointF(200, 700);
                blockSize = new PointF();
                blockSize.x = (float) x / 55;
                blockSize.y = (float) x / 55;
                mScreenX = x;
                mScreenY = y;

                mFontSize = mScreenX / 30; //5%(1/20) of screen width
                mFontMargin = mScreenX / 75; //1.5%(1/75) of scren width

                mOurHolder = getHolder();
                mPaint = new Paint();
                joystickX = 0;
                joystickY = 0;

                //Initialize objects(maze, pacman, ghost, joystick)
                PacGhostRadius = (float) (mScreenX + mScreenY) / 200;
                mPacman = new Pacman(mScreenX, 13, 23);
                mGhost = new Ghost(mScreenX, 13, 11);
                mFakeJoy = new FakeJoy(200, 100, blockSize, fakePosition);
                pellet = 0;
                MAX_PELLETS = 100; // TODO: Update max pellets to maze.
                //bitmap
                bitmap = Bitmap.createBitmap(mScreenX, mScreenY, Bitmap.Config.ARGB_8888);
                mCanvas = new Canvas(bitmap);

                mMaze = new Maze(activityContext, mScreenX, mScreenY, blockSize);

                //start the game LETS GET PACCING
                update();
                draw();
                startNewGame();

        }

        //runs when a player lost all lives and restarts
        //or starting the first game
        public void startNewGame() {
                //reset maze level

                //initialize the position of pacman and ghosts
                mPacman.reset(mScreenX, mScreenY);
                mGhost.reset(mScreenX, mScreenY);

                //resetting score/lives/direction/pellets
                mScore = 0;
                mLives = 3;
                pellet = 0;
                mFakeJoy.setCenter();
                mPacman.updateNextDirection('l');

                // testing maze and level creator
              //  mMaze = new Maze(activityContext, mScreenX, mScreenY);

                PacmanGameStart = MediaPlayer.create(activityContext, R.raw.pacman_beginning);
                PacmanGameStart.start();

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

        }
        public void StageCleared(){
                // TODO: reset using maze coordinates, rather than screen position.
                mPacman.reset(mScreenX, mScreenY);
                mGhost.reset(mScreenX, mScreenY);

                //resetting /States/Direction
                pellet = 0; // New map, reset pellet counter
                mPacman.setPowerUpState(0, false);
                mGhost.setDeathState(0, false);
                // Movement reset.
                mFakeJoy.setCenter();
                //mPacman.updateNextDirection('l');

                // In this case we just reset the maze,
                // TODO: Add more levels.
                //mMaze = new Maze(activityContext, mScreenX, mScreenY);
                PacmanGameStart = MediaPlayer.create(activityContext, R.raw.pacman_beginning);
                PacmanGameStart.start();



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
                mGhost.isInBounds(mScreenX, mScreenY);

                //TODO: separate detection collision methods within pacman/ghost/maze classes OR do it here
                //pacman && ghost collision (add condition for super mode later)

                // Eventually, must use Location to detect collion not screen position.
                //if (mPacman.detectCollision(mGhost.loc, mScreenX, mScreenY)) {
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


                /* TODO: Because Pacman's location is updated by the screen's position it cannot use .loc.getX or .loc.getY
                         Create new variables to keep track of coordinate position within MAZE/GRID!
                //mGrid = mMaze.getMaze();
                xPac = mPacman.loc.getX();
                yPac = mPacman.loc.getY();
                //if(mGrid[xPac][yPac].getObj() == EMPTY){
                //}

                switch(mMaze.getMaze()[xPac][yPac].getObj()){
                        //TODO: handle pacman & ghost collision seperateley.
                        // Ghost collision should be handled by their own private x,y coordinates instead.
                        // Maze cannot keep track of all Pellet/Pacman/Ghost at same location.
                        // This case should be stand alone within pacman?
                        case GHOST:
                                // !!! Death sequence, Pacman and Ghost same tile. Which Ghost does not matter.
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
                                        // Ghost matters, set the specific ghost's deathState.
                                        // TODO: Set timer as ghost touches Graveyard?
                                        mGhost.setDeathState(0, true);
                                        //Pacman is able to eat ghosts
                                        // TODO: Send ghost back to graveyard.
                                        //mghost.moveTowardsTarget(  ); // Graveyard spawn coordinates.
                                }
                                break;
                	    case WALL: // Prevent movement here, but pacman MUST continue moving
                                // TODO: Pacman collision here
                                break;
                        case PELLET:
                                mMaze.getMaze()[xPac][yPac].updateLoc(xPac, yPac, mBlock.EMPTY);
                                if(pellet >= MAX_PELLETS){ // On game complete TODO: Change pellet to correct amount.
                                        Log.d("Debugging", "In Collision Interact: POWER_PELLET");
                                        draw();
                                        pauseStartDeath(3000);
                                        mFakeJoy.setCenter();
                                        draw();
                                        StageCleared();
                                }
                                // TODO: Have grid set location to empty Object.
                                //mGrid[xPac][yPac].updateLoc(xPac, yPac, EMPTY ); // Set empty?
                                pellet++;
                                break;
                         case POWER_PELLET: // Encounter PowerPellet, set state
                                 Log.d("Debugging", "In Collision Interact: POWER_PELLET");
                                 mPacman.setPowerUpState(2000,true); // 2000 is amount of frames time to be decremented EVERY FRAME
                                // TODO: Set exact amount of frames the powerup lasts, for now infinite.
                                break;
                        case WARP_SPACE:
                                // TODO: Find out both locations of warp_space. Have pacman swap positions.
                                //mPacman.loc.setNewLoc( ); // Change to locate grid coordinates, not screen position
                                break;
                        case GHOST_GATE: // Prevent movement. Collision
                                Log.d("Debugging", "In Collision Interact: GHOST_GATE");
                                // TODO: Pacman collision here
                                break;
                        case PAC_SPAWN: // *Nothing* Not needed? Same as empty.
                                break;
                        case GHOST_SPAWN: // Nothing at the moment/ Prevent movement?
                                // TODO: Pacman collision here
                                break;
                        case EMPTY: // empty space, Nothing happens. Continue movement.
                                Log.d("Debugging", "In Collision Interact: EMPTY");
                                break;
                        default:
                                Log.d("Debugging", "In Collision Interact");
                                break;
                }
        */
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

                        // draw all member objects
                        mMaze.draw(mCanvas, mPaint);
                        mFakeJoy.draw(mCanvas, mPaint);

                        float xScaled = (float) mScreenX / 2;
                        float yScaled = (float) mScreenY / 12;
                        //mPaint.setColor(Color.argb(255, 255, 255, 0));
                        //canvas.drawCircle(loc.getX(), loc.getY(), radius, mPaint);
                      /*  Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.pacman);


                        Bitmap sizedB = Bitmap.createScaledBitmap(b, (int) PacGhostRadius * 2,
                                (int) PacGhostRadius * 2, false);

                        mCanvas.drawBitmap(sizedB, (mPacman.loc.getX() * 28 + xScaled) - PacGhostRadius,
                                (mPacman.loc.getY() * 28 + yScaled) - PacGhostRadius , null);



                        b = BitmapFactory.decodeResource(getResources(), R.drawable.blinky);

                        sizedB = Bitmap.createScaledBitmap(b, (int) PacGhostRadius * 2,
                                (int) PacGhostRadius * 2, false);

                        mCanvas.drawBitmap(sizedB, (mGhost.loc.getX() * 28 + xScaled) - PacGhostRadius,
                                (mGhost.loc.getY() * 28 + yScaled) - PacGhostRadius , null);*/

                        mPaint.setColor(Color.argb(255, 0, 0, 255));
                        //redPaint.setColor(Color.argb(0,255, 0, 0));
                        // Draw the vertical lines of the maze*/

                        mPaint.setColor(Color.argb(255, 0, 0, 255));




                        mCanvas.drawLine(blockSize.y * 25, blockSize.y * 1,
                                blockSize.y * 25, blockSize.y * 30,
                                mPaint);
                        mCanvas.drawLine(blockSize.y * 53, blockSize.y * 1,
                                blockSize.y * 53, blockSize.y * 30,
                                mPaint);

                        // Draw the horizontal lines of the maze
                        mCanvas.drawLine(blockSize.x * 25, blockSize.x * 1,
                                blockSize.x * 53, blockSize.x * 1,
                                mPaint);
                        mCanvas.drawLine(blockSize.x * 25, blockSize.x * 30,
                                blockSize.x * 53, blockSize.x * 30,
                                mPaint);





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







