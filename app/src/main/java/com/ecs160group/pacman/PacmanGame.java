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
        public sound PacmanGameStart;

        private Location mGrid;
        private int xPac; // Grabs loc.x & loc.y coordinates
        private int yPac;
        int gridValues[];
        private int pellet; // Eventually needs to be deleted TODO: Let maze handle win condition, check if no more pellets.
        private int MAX_PELLETS;

        Block mBlock;

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


                mPacman = new Pacman(mScreenX, mMaze.pacSpawn, PacGhostRadius);
                mGhost = new Ghost(mScreenX, mMaze.ghostSpawn);
                mFakeJoy = new FakeJoy(200, 100, blockSize, fakePosition);
                pellet = 0;
                MAX_PELLETS = 50; // testing purposes TODO: Update max pellets to maze.
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
                mPacman.reset();
                mGhost.reset();

                //resetting score/lives/direction/pellets
                mScore = 0;
                mLives = 3;
                pellet = 0; // TODO: Have maze check for not pellets instead.
                mFakeJoy.setCenter();
                mPacman.updateNextDirection('l');

                // Should reset the Maze, refresh pellets
                //mMaze = new Maze(activityContext, mScreenX, mScreenY, blockSize);

                //draw();
                PacmanSounds.pacmanBeginning();
                //pauseStartDeath(5000);
                //PacmanSounds.pacmanIntermission();

        }

        public void deathRestart(){
                //reset maze level
                mPaused = true;
                //initialize the position of pacman and ghosts, also resets timers and states
                mPacman.reset();
                mGhost.reset();
                mLives--;

                //Reset game if 0 lives.
                if(mLives <= 0){ startNewGame(); }
                pauseStartDeath(3000); // In milliseconds
                //TODO: Pause the game and resume

        }
        public void StageCleared(){
                // TODO: reset using maze coordinates, rather than screen position.
                //initialize the position of pacman and ghosts, also resets timers and states
                mPacman.reset();
                mGhost.reset();

                //resetting /States/Direction
                pellet = 0; // New map, reset pellet counter
                mGhost.setDeathState(0, false);
                // Movement reset.
                mFakeJoy.setCenter();

                // In this case we just reset the maze,
                // TODO: Add more levels.
                //mMaze = new Maze(activityContext, mScreenX, mScreenY);

                PacmanSounds.pacmanBeginning();

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
                                //Boolean updatePacman = mPacman.wallDetection(mMaze);
                               // Boolean updateGhost = mGhost.wallDetection(mMaze);

                                update(true, true);
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
        private void update(boolean updatePacman, boolean updateGhost) {
                //TODO: add update methods to pacman/ghost/maze classes OR do it here
                
                // now has a if-condition to check whether Pacman and ghost will hit a wall
                // so there is no need for update
                if (updatePacman)
                        mPacman.update(mFPS);
                if (updateGhost)
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
                                PacmanSounds.pacmanDeath();
                                draw();
                                pauseStartDeath(3000);
                                mFakeJoy.setCenter();
                                draw();
                                deathRestart();

                        }
                        else{
                                // Set timer as ghost touches Graveyard?
                                mGhost.setDeathState(0, true);
                                pauseStartDeath(500);
                                PacmanSounds.pacmanEatGhost();
                                //Pacman is able to eat ghosts
                                //Deal with ghost returning to graveyard.
                                //mghost.moveTowardsTarget();
                        }
                }

                gridValues = mMaze.getGridValues(mPacman.getLoc());
                xPac = gridValues[0];
                yPac = gridValues[1];
                Location[][] mGrid = mMaze.getMaze();
                Log.d("Debugging: ", "detect collision OBJECT gameactivity: " + mGrid[xPac][yPac].getObj());
                switch(mGrid[xPac][yPac].getObj()){
                        case GHOST:
                                // !!! Death sequence, Pacman and Ghost same tile. Which Ghost does not matter.
                                if(mPacman.getPowerState() == false && mPacman.getPowerTimer() >= 0){
                                        PacmanSounds.pacmanDeath();
                                        draw();
                                        pauseStartDeath(3000);
                                        mFakeJoy.setCenter();
                                        draw();
                                        deathRestart();
                                }
                                else{
                                        // Ghost matters, set the specific ghost's deathState.
                                        // TODO: Set timer as ghost touches Graveyard?
                                        mGhost.setDeathState(540, true); // 540 frames?
                                        //Pacman is able to eat ghosts
                                        // TODO: Send ghost back to graveyard.
                                        //mghost.moveTowardsTarget(  ); // Graveyard spawn coordinates.
                                }
                                break;
                	    case WALL: // Prevent movement here, but pacman MUST continue moving, Dealt within Pacman update.
                                // TODO: Pacman collision here? Already done in update collision check?
                                break;
                        case PELLET:
                                // Placeholder, encounters pellet set empty.
                                mMaze.getMaze()[xPac][yPac].updateLoc(xPac, yPac, mBlock.EMPTY);
                                pellet++;
                                Log.d("Debugging", "In Collision Interact: POWER_PELLET");
                                if(pellet >= MAX_PELLETS){ // On game complete TODO: Allow maze to handle, instead "check no pellets exist"
                                        Log.d("Debugging", "In Collision Interact: PELLET, STAGE COMPLETE");
                                        draw();
                                        PacmanSounds.pacmanChomp(); // Consume pellet..
                                        pauseStartDeath(3000);
                                        mFakeJoy.setCenter();
                                        draw();
                                        StageCleared();
                                }
                                break;
                         case POWER_PELLET: // Encounter PowerPellet, set state
                                 Log.d("Debugging", "In Collision Interact: POWER_PELLET");
                                 PacmanSounds.pacmanPowerup();
                                 mPacman.setPowerUpState(540,true); // 2000 is amount of frames time to be decremented EVERY FRAME
                                break;
                        case FRUIT:
                                // TODO: give score depending on spawned fruit.
                                // Score(); // score class?
                                mMaze.getMaze()[xPac][yPac].updateLoc(xPac, yPac, mBlock.FRUIT_SPAWN);
                                PacmanSounds.pacmanEatFruit();
                                break;
                        case WARP_SPACE: // Swap Pacman's location depending on warp entrance.
                                // Possible loop here? Updating Pacman's location on top of WARP location! Double check.
                                if(xPac == 0 && yPac == 14){
                                        Log.d("Debugging", "In Collision Interact: WARP_SPACE, Pacman warped[Right->Left].");
                                        mPacman.loc.setNewLoc(27, 14); // Change to locate grid coordinates, not screen position
                                }
                                else{
                                        //Assumed opposite side. Change Pacman's location
                                        Log.d("Debugging", "In Collision Interact: WARP_SPACE, Pacman warped[Left->Right].");
                                        mPacman.loc.setNewLoc(0, 14);
                                }
                                Log.d("Debugging", "In Collision Interact: WARP_SPACE, Pacman warped.");
                                break;
                        case GHOST_GATE: // Prevent movement. Collision
                                Log.d("Debugging", "In Collision Interact: GHOST_GATE");
                                // TODO: Pacman collision here
                                break;
                        case PAC_SPAWN: // *Nothing* Not needed? Same as empty.
                                Log.d("Debugging", "In Collision Interact: PAC_SPAWN");
                                break;
                        case GHOST_SPAWN: // Nothing at the moment/ Prevent movement in update collision check?
                                Log.d("Debugging", "In Collision Interact: GHOST_SPAWN");
                                // TODO: Pacman collision here
                                break;
                        case EMPTY: // empty space, Nothing happens. Continue movement.
                                Log.d("Debugging", "In Collision Interact: EMPTY");
                                break;
                        default:
                                Log.d("Debugging", "In Collision Interact Default");
                                break;
                }

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

                        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.pacman);


                        Bitmap sizedB = Bitmap.createScaledBitmap(b, (int) PacGhostRadius * 2,
                                (int) PacGhostRadius * 2, false);

                       

                        mCanvas.drawBitmap(sizedB, mPacman.loc.getX() - PacGhostRadius,
                                mPacman.loc.getY() - PacGhostRadius, null);

                        b = BitmapFactory.decodeResource(getResources(), R.drawable.blinky);

                        sizedB = Bitmap.createScaledBitmap(b, (int) PacGhostRadius * 2,
                                (int) PacGhostRadius * 2, false);

                        mCanvas.drawBitmap(sizedB, mGhost.loc.getX() - PacGhostRadius,
                                mGhost.loc.getY() - PacGhostRadius, null);

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
                        mPaused = true;
                        Thread.sleep(t);
                } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                mPaused = false;

        }
}







