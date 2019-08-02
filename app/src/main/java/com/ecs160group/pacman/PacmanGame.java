package com.ecs160group.pacman;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;



/*

    main class for Pacman game

 */

public class PacmanGame extends SurfaceView implements Runnable
{
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
	private BlockingQueue<Ghost> graveyard;
	private Bitmap bitmap;
	private SurfaceHolder mOurHolder;
	public Canvas mCanvas;
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
	private int frameCount;
	private int xPac; // Sote loc.x & loc.y coordinates
	private int yPac;
	//int pacmanGridValues[];
	Location pacmanGridValues;

	// Not needed anymore, Maze will deal with win condition.
	///private int pellet; // Eventually needs to be deleted TODO: Let maze handle win condition, check if no more pellets.
	//private int MAX_PELLETS;

	// Block mBlock;

	public float PacGhostRadius;

	private BitmapDrawer mBitmapDrawer;

	public int xScaled;
	public int yScaled;
	public int deathGhostTimer;

	//constructor
	public PacmanGame(Context context, int x, int y)
	{
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

		mPacman = new Pacman(mScreenX, mMaze.pacSpawn, PacGhostRadius, mScore, activityContext, mMaze);
//		mGhost = new Ghost(mScreenX, mMaze.ghostSpawn, mMaze);
		mBlinky = new Blinky(mScreenX, mMaze.ghostSpawn, mMaze, mPacman);
		mInky = new Inky(mBlinky, mScreenX, mMaze.ghostSpawn, mMaze, mPacman);
		mPinky = new Pinky(mScreenX, mMaze.ghostSpawn, mMaze, mPacman);
		mClyde = new Clyde(mScreenX, mMaze.ghostSpawn, mMaze, mPacman);

		graveyard = new ArrayBlockingQueue<>(4);
		addAllGhostsToGY();
		deathGhostTimer = 60; // Death Timer for Ghost

		mFakeJoy = new FakeJoy(200, 100, blockSize, fakePosition);
		//bitmap
		bitmap = Bitmap.createBitmap(mScreenX, mScreenY, Bitmap.Config.ARGB_8888);
		mCanvas = new Canvas(bitmap);

		mBitmapDrawer = new BitmapDrawer(this);
		frameCount = 0;

		//start the game LETS GET PACCING
		//  update(true, true);
		// draw();
		startNewGame();

	}

	/**
	 * Add all Ghosts to the Graveyard using the Queue, for initial adding and resets of the maze
	 * Uses A Blocking Queue to be thread safe and not have more than 4 ghosts in the graveyard
	 */
	public void addAllGhostsToGY()
	{
		if (!graveyard.isEmpty()) { // clear all ghosts from the graveyard
			graveyard.clear();
		}
		// add all the ghosts in this order
		mBlinky.setDeathState(deathGhostTimer, true); //  ATM 200 frames?
		mPinky.setDeathState(deathGhostTimer, true);
		mInky.setDeathState(deathGhostTimer, true);
		mClyde.setDeathState(deathGhostTimer, true);
		mBlinky.gridLocation.setNewLoc(13, 14);
		mPinky.gridLocation.setNewLoc(13, 14); // TODO: remove hardcoded values.
		mInky.gridLocation.setNewLoc(13, 14);
		mClyde.gridLocation.setNewLoc(13, 14);
		graveyard.add(mBlinky);
		graveyard.add(mPinky);
		graveyard.add(mInky);
		graveyard.add(mClyde);
	}

	/**
	 *
	 */
	public void removeGhostFromGY()
	{
		Ghost temp;
		if (!graveyard.isEmpty() && !graveyard.peek().isInGYard()) { // Not empty, a ghost exists in GY
			temp = graveyard.poll(); // Retrieve and removes head of queue
			//Log.d("ghost-removeGhostGY: ", "OLD! x: " + temp.gridLocation.getX() + "y: " + temp.gridLocation.getY());
			temp.gridLocation.setNewLoc(13, 11); // Take head out of graveyard. Place at entrance.
			//temp.setInGYard(true);
			temp.setDeathState(0, false);
			//Log.d("ghost-removeGhostGY: ", "NEW! x: " + temp.gridLocation.getX() + "y: " + temp.gridLocation.getY());
			if (!graveyard.isEmpty()) {
				temp = graveyard.peek();
				temp.setDeathState(deathGhostTimer, true);
			}
		}
		//graveyard.clear();

	}

	//runs when a player lost all lives and restarts
	//or starting the first game
	public void startNewGame()
	{
		//reset maze level

		//initialize the position of pacman and ghosts, also resets timers and states
		actorReset();

		//TODO: ADD ghost back to GraveYard Queue
		addAllGhostsToGY();

		//resetting score/lives/direction/pellets
		mScore.reset();
		mLives = 3;
		//pellet = 0; // TODO: Have maze check for not pellets instead.
		mFakeJoy.setCenter();
		mPacman.updateNextDirection(' ');

		// Should reset the Maze, refresh pellets
		mMaze = new Maze(activityContext, mScreenX, mScreenY, blockSize);


		draw();
		PacmanSounds.pacmanBeginning();
		mPaused = true; // Resume on user touch
		//pauseStartDeath(5000);
		//PacmanSounds.pacmanIntermission();

	}

	public void deathRestart()
	{
		//reset maze level
		mPaused = true; // Resume on user touch
		//initialize the position of pacman and ghosts, also resets timers and states
		actorReset();

		//TODO: ADD ghost back to GraveYard Queue
		addAllGhostsToGY();

		mLives--;
		//Reset game if 0 lives.
		if (mLives <= 0) {
			startNewGame();
		}
		//pauseStartDeath(3000); // In milliseconds

	}

	public void StageCleared()
	{
		//initialize the position of pacman and ghosts, also resets timers and states
		mPaused = true; // Resume on user touch
		actorReset();

		//TODO: ADD ghost back to GraveYard Queue

		//resetting /States/Direction
		//pellet = 0; // New map, reset pellet counter
		// Movement reset.
		mFakeJoy.setCenter();

		// In this case we just reset the maze,
		// TODO: Add more levels.
		mMaze = new Maze(activityContext, mScreenX, mScreenY, blockSize);
		pauseStartDeath(3000);

	}

	public void actorReset()
	{
		//mGhost.reset(); // TODO: Remove later
		mInky.reset();
		mPinky.reset();
		mBlinky.reset();
		mClyde.reset();
		mPacman.reset();
	}

	// When we start the thread with:
	// mGameThread.start();
	// the run method is continuously called by Android
	// because we implemented the Runnable interface
	// Calling mGameThread.join();
	// will stop the thread
	@Override
	public void run()
	{
		while (mPlaying) {
			//time at start of loop
			long frameStartTime = System.currentTimeMillis();
			frameCount++;
			if (!mPaused) {
				//Boolean updateGhost = mGhost.wallDetection(mMaze);

				if (frameCount % 5 == 0 || frameCount == 0) {
					//Log.d("run: ", "frameStartTime: " + frameCount);

					mPacman.updateNextDirection(mFakeJoy.direction);
					updatePacman = mPacman.wallDetection();
					mBitmapDrawer.updatePac = updatePacman;
					update(updatePacman);
					detectCollisions();
					//Determines powerup state of pacman powerTimer decrements on every frame.
					mPacman.checkPowerUpState();

					// Checks states of ghosts, If dead decrements death timer by 1.
					//mGhost.decrementDeathTimer();
					mBlinky.decrementDeathTimer();
					mPinky.decrementDeathTimer();
					mInky.decrementDeathTimer();
					mClyde.decrementDeathTimer();
					removeGhostFromGY(); // Check if head of queue is finished. Pop off and update states.

				}
			}
			detectCollisions();
			//redraw grid/ghosts/pacman/pellets
			draw();

			//time of loop/frame
			long frameTime = System.currentTimeMillis() - frameStartTime;

			//>0 condition to prevent divide by zero crashes
			if (frameTime > 0) {
				mFPS = MILLIS_IN_SECOND / frameTime;
				//store in mFPS to pass to update methods of pacman/ghosts
			}
			if (frameCount > 10000) {
				frameCount = 0;
			}
		}
	}

	/**
	 * This function is used to schedule the updates to location of pacman and the ghosts.
	 *
	 * @param updatePacman Determines if pacman is moving to a valid location. if so update position/draw
	 */
	private void update(boolean updatePacman)
	{

		//Log.d("update: ", "Updating pacmman/ghost: ");
		//Log.d("update: ", "pacmanLoc: " + "Location: " + mPacman.loc.getX() + "," + mPacman.loc.getY());
		//Log.d("update: ", "pacmanLoc: " + "GridLocation: " + mPacman.getGridLoc().getX() + "," + mPacman.getGridLoc().getY());

		if (updatePacman) {
			mPacman.update(mFPS);
		}

		//mGhost.update(mFPS);
/*
		mInky.update(mFPS);
		mPinky.update(mFPS);
		mBlinky.update(mFPS);
		mClyde.update(mFPS);
*/


		mBlinky.move();
		mPinky.move();
		mInky.move();
		mClyde.move();


	}

	private void sendToGY(Ghost tGhost)
	{
		if (tGhost.isInGYard() && !graveyard.contains(tGhost)) {
//			tGhost.setInGYard(true); // makes ghost dead/in graveyard
			graveyard.add(tGhost);
		}
	}

	//detects if pacman hit a pellet/ghost/wall
	private void detectCollisions()
	{

		//TODO: separate detection collision methods within pacman/ghost/maze classes OR do it here
		//pacman && ghost collision (add condition for super mode later)

		// Eventually, must use Location to detect collion not screen position.
		//if (mPacman.detectCollision(mGhost.loc, mScreenX, mScreenY)) {

		mPacman.collisionInteraction(mInky, mPinky, mBlinky, mClyde, mMaze, mScore);
		// Check interactions, then win condition.
		if (mPacman.hasWon(mMaze) == true) {
			//Log.d("Debugging", "In Collision Interact: PELLET, STAGE COMPLETE");
			draw();
			PacmanSounds.pacmanChomp(); // Consume pellet..
			pauseStartDeath(4000);
			mFakeJoy.setCenter();
			draw();
			StageCleared();

		} // Win condition reached. All pellets cleared.

		if (mPacman.ghostCollision(mInky, mScore) ||
				mPacman.ghostCollision(mPinky, mScore) ||
				mPacman.ghostCollision(mBlinky, mScore) ||
				mPacman.ghostCollision(mClyde, mScore)) {
			if (mPacman.getIsDead()) {
				draw();
				pauseStartDeath(3000);
				mFakeJoy.setCenter();
				draw();
				deathRestart();
			}
		}
		sendToGY(mInky); // Checks death state, send to Graveyard if eaten.
		sendToGY(mPinky);
		sendToGY(mBlinky);
		sendToGY(mClyde);
	}

	//called by PacmanActivity when player quits game
	public void pause()
	{
		mPlaying = false;
		try {
			//stop thread
			mGameThread.join();
		} catch (InterruptedException e) {
			Log.e("Error:", "joining thread");
		}
	}

	//called by PacmanActivity when player starts game
	public void resume()
	{
		mPlaying = true;

		//creates and starts a new game thread
		mGameThread = new Thread(this);
		mGameThread.start();

	}

	//draw game objects and HUD
	private void draw()
	{
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
			//draws pac and all ghosts/specific ghosts null for now, will take out mGhost later
			mBitmapDrawer.draw(mPacman, null, mBlinky, mInky, mPinky, mClyde, mCanvas);

			if (DEBUGGING) {
				printDebuggingText();
			}

			mOurHolder.unlockCanvasAndPost(mCanvas);

		}
	}

	private void printDebuggingText()
	{
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
/*
		mCanvas.drawText("mGhost.gridLocation.x: " + mGhost.gridLocation.getX(),
				10, debugStart + debugSize + 90, mPaint);
		mCanvas.drawText("mGhost.gridLocation.x: " + mGhost.gridLocation.getY(),
				10, debugStart + debugSize + 120, mPaint);
*/
		mCanvas.drawText("xScaled: " + xScaled,
				10, debugStart + debugSize + 150, mPaint);
		mCanvas.drawText("yScaled: " + yScaled,
				10, debugStart + debugSize + 180, mPaint);
/*
		mCanvas.drawText("mGhost.loc.x: " + mGhost.loc.getX(),
				10, debugStart + debugSize + 30, mPaint);
		mCanvas.drawText("mGhost.loc.y: " + mGhost.loc.getY(),
				10, debugStart + debugSize + 60, mPaint);
*/
		mCanvas.drawText("mPacman.loc.x: " + mPacman.gridLocation.getX(),
				10, debugStart + debugSize + 210, mPaint);
		mCanvas.drawText("mPacman.loc.y: " + mPacman.gridLocation.getY(),
				10, debugStart + debugSize + 240, mPaint);
		mCanvas.drawText("mPacman.direction: " + mPacman.direction,
				10, debugStart + debugSize + 270, mPaint);
		mCanvas.drawText("mFakeJoy.direction: " + mFakeJoy.direction,
				10, debugStart + debugSize + 300, mPaint);


	}

	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		mPaused = false;
		float x = e.getX();
		float y = e.getY();
		if (e.getAction() == e.ACTION_MOVE || e.getAction() == e.ACTION_DOWN) {
			mFakeJoy.updateStick(x, y);
		} else {
			mFakeJoy.setCenter();
		}
		return true;
	}

	// In milliseconds..
	void pauseStartDeath(int t)
	{
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







