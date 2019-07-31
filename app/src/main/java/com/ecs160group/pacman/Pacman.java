package com.ecs160group.pacman;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

import java.lang.Math;
import java.util.Random;

/*
    protagonist of Pacman game
    can eat pellets
    can eat fruits
    can eat ghosts for a period of time after power pellets are eaten
    must eat all pellets to complete level
 */


public class Pacman //implements Collision
{
	//pacman coords//directions
	Location loc;
	Location gridLocation;
	private int pacGridX;
	private int pacGridY;
	private Block block;
	public char direction;
	private char next_direction;

	// Pacman's power-up state and timer.
	private boolean powerState;
	private int powerTimer;
	private PacmanGame game;

	public Location spawnLoc;
	private Score score;

	// use an integer to temporarily replace the draw of Pacman
	// this will be modified under the draw function
	int pacImage = 1;

	//RectF has four values (left, top, right, bottom)

	float velocity;
	private float radius;
	final Paint paint = new Paint();

	Pacman(int screenX, Location spawnLoc, float radius, Score score)
	{
		this.score = score;

		paint.setColor(Color.argb(255, 255, 255, 0));
		//pacman width/height 1% of screen (change later if needed)
		this.radius = radius;
		this.spawnLoc = spawnLoc;
		loc = new Location(spawnLoc.getX(), spawnLoc.getY(), Block.PACMAN);
		gridLocation = new Location(13, 29, Block.PACMAN); // 13,29 Used to keep track of own grid position.
		Log.d("Pacman-gridlocation: ", "Location: " + gridLocation.getX() + "," + gridLocation.getY());
		direction = 'l';
		next_direction = 'l';
		powerState = false;
		powerTimer = 0;
		velocity = screenX / 15;
	}

	/**
	 * Get's actual location of Pacman
	 *
	 * @return
	 */
	public Location getGridLoc()
	{
		return gridLocation;
	}

	/**
	 * Gets the time left on Pacman's super timer
	 *
	 * @return
	 */
	public int getPowerTimer()
	{
		return powerTimer;
	}

	/**
	 * Gets whether Pacman is in power state
	 *
	 * @return
	 */
	public boolean getPowerState()
	{
		return powerState;
	}

	/**
	 * Gives the current direction that Pacman is facing
	 *
	 * @return Pacman's current direction;
	 */
	public char getDirection()
	{
		return direction;
	}

	/**
	 * Sets the super state of Pacman
	 *
	 * @param pTimer amount of time left on Pacman
	 * @param pState sets the timer and powerup state of pacman
	 */
	public void setPowerUpState(int pTimer, boolean pState)
	{
		powerTimer = pTimer;
		powerState = pState;
	}


	/**
	 * Checks the power up state of Pacman and decrements the power timer
	 */
	public void checkPowerUpState()
	{
		if (powerState == true || powerTimer > 0) {
			setPowerUpState(powerTimer - 1, true);
			if (powerTimer <= 0) {
				setPowerUpState(0, false);
			}
		}
	}

	/**
	 * Checks if pacman is super
	 *
	 * @return if pacman is super
	 */
	public boolean isSuper()
	{
		if (powerState == true && powerTimer > 0)
			return powerState;
		return false;
	}


	/**
	 * updates pacman's position
	 * called each frame/loop from PacmanGame update() method
	 * moves ball based on fps and the direction user is moving joystick
	 */
	void update(long fps)
	{
		pacGridX = gridLocation.getX();
		pacGridY = gridLocation.getY();
		// Move();
		//Log.d("Pacman-update: ", "Current_LOC: " + direction + " Location: " + pacGridX + "," + pacGridY);
		if (direction == 'l') {
			loc.setNewLoc((int) (loc.getX() - (velocity / fps)), loc.getY());
			gridLocation.setNewLoc(pacGridX - 1, pacGridY);
		} else if (direction == 'r') {
			loc.setNewLoc((int) (loc.getX() + (velocity / fps)), loc.getY());
			gridLocation.setNewLoc(pacGridX + 1, pacGridY);
		} else if (direction == 'u') {
			loc.setNewLoc(loc.getX(), (int) (loc.getY() - (velocity / fps)));
			gridLocation.setNewLoc(pacGridX, pacGridY - 1);

		} else if (direction == 'd') {
			loc.setNewLoc(loc.getX(), (int) (loc.getY() + (velocity / fps)));
			gridLocation.setNewLoc(pacGridX, pacGridY + 1);
		}
		//Log.d("Pacman-update: ", "New_LOC: " + direction + " , Location: " + pacGridX + "," + pacGridY);

	}

	void reverseVel()
	{
		velocity = -velocity;
	}

	/**
	 * check if Pacman need to update by wall detection
	 * this fucntion will be executed before update
	 * by avoiding going through the wall, Pacman is inside the maze
	 * so we can predict whether a update is needed for Pacman
	 */
	// further can be used for pullet collision
	public boolean wallDetection(Maze maze)
	{
		// this value will be used for deciding update or not
		boolean update;
		update = true;
		//read in Grid and grid indices of current location
		Location[][] mGrid = maze.getMaze();

		// If current position is warp, DO NOT update position.
		if (mGrid[gridLocation.getX()][gridLocation.getY()].getObj() != block.WARP_SPACE) {
			// if Pacman will hit the right wall, stop
			if (direction == 'r') {
				if (mGrid[gridLocation.getX() + 1][gridLocation.getY()].isWall()) {
					//Log.d("PACMAN HAS HIT A WALL:", "direction:" + direction);
					update = false;
				}
			}
			//if pacman will hit the left wall, stop
			if (direction == 'l') {
				if (mGrid[gridLocation.getX() - 1][gridLocation.getY()].isWall()) {
					//Log.d("PACMAN HAS HIT A WALL:", "direction:" + direction);
					update = false;
				}
			}
			//if Pacman will hit the top wall, stop
			if (direction == 'u') {
				if (mGrid[gridLocation.getX()][gridLocation.getY() - 1].isWall()) {
					//Log.d("PACMAN HAS HIT A WALL:", "direction:" + direction);
					update = false;
				}
			}
			//if Pacman will hit the bottom wall, stop
			if (direction == 'd') {
				if (mGrid[gridLocation.getX()][gridLocation.getY() + 1].isWall()) {
					//Log.d("PACMAN HAS HIT A WALL:", "direction:" + direction);
					update = false;
				}
			}
		}
		return update;
	}


	/**
	 * detects the collisions of pacman with ghost, pellets, fruits,
	 * TODO: TESTING TO CHECK WALL DETECTION HERE
	 */
	// currently used for Pacman and ghost collision detection
	public boolean detectCollision(Location loc, int mScreenX, int mScreenY)
	{
		boolean collided = false;
		float radius = (mScreenX + mScreenY) / 200;
		if (Math.abs(this.loc.getX() - loc.getX()) <= radius * 2
				&& Math.abs(this.loc.getY() - loc.getY()) <= radius * 2) {
			collided = true;
		}
		return collided;
	}

	/**
	 * check if Pacman is inside the screen
	 */
	public void isInBounds(int mScreenX, int mScreenY)
	{
		float radius = (mScreenX + mScreenY) / 200;

		//if pacman hits the right screen wall, stop
		if ((loc.getX() + radius) > mScreenX) {
			//Log.d("pacman has hit a wall:", "direction:" + direction);
			loc.setNewLoc((int) (mScreenX - radius), loc.getY());
		}

		//if pacman hits the left screen wall, stop
		//
		if ((loc.getX() - radius) < 0) {
			//Log.d("pacman has hit a wall:", "direction:" + direction);
			loc.setNewLoc((int) (0 + radius), loc.getY());

		}
		//if pacman hits the bottom screen wall
		if ((loc.getY() + radius) > mScreenY) {
			//Log.d("pacman hit the bottom:", "direction:" + direction);
			loc.setNewLoc(loc.getX(), (int) (mScreenY - radius));
		}

		if ((loc.getY() - radius) < 0)  //up
		{
			//Log.d("pacman hit upper wall:", "direction:" + direction);
			loc.setNewLoc(loc.getX(), (int) (0 + radius));
		}


	}

	/**
	 * resets pacman's location to its original spawn
	 * called at PacmanGame's startNewGame() and reset()
	 * (whenever user starts a new game or dies to a ghost)
	 */
	void reset()
	{

		loc.setNewLoc(spawnLoc.getX(), spawnLoc.getY());
		gridLocation.setNewLoc(13, 23); // TODO: Hard coded spawn, change later.
		direction = 'l';
		powerTimer = 0;
		powerState = false;

	}

	/**
	 * Checks if pacman is within bounds of maze
	 */
	void checkBounds()
	{

	}

	//TODO: will use variable direction later on for image purposes

	//circle for now, may need to be modified

	/**
	 * Draws Pacman on screen
	 */
	public void draw(Canvas canvas, Paint mPaint, float radius)
	{
		mPaint.setColor(Color.argb(255, 255, 255, 0));
		canvas.drawCircle(loc.getX(), loc.getY(), radius, mPaint);

	}


	/**
	 * called by onTouchEvent in PacmanGame once user moves the stick
	 * takes in the current direction of the joystick to update Pacman's next direction
	 *
	 * @param joyDirection - read in from the joystick's direction variable
	 */
	void updateNextDirection(char joyDirection)
	{
		if (joyDirection == 'l') {
			direction = 'l';
		} else if (joyDirection == 'r') {
			direction = 'r';
		} else if (joyDirection == 'u') {
			direction = 'u';
		} else if (joyDirection == 'd') {
			direction = 'd';
		}
	}

	/**
	 * checking if the next move of Pacman is available
	 *
	 * @param x x coordinate of maze
	 * @param y y coordinate of maze
	 * @return true if doesn't collide with anything
	 */
	public boolean checkCollisionPacman(int x, int y)
	{
		// should be false by default
		// for now we set it true for testing
		boolean b; // Unable to traverse by default?
		b = true;
		    /*
		    If(loc[x][y].block == EMPTY) {
			    return true;
		    }
	        */
		// maze[x][y] != walkable space... to return True.
		//if()
		return b;
	}

	/**
	 * Constructor for Pacman's movement
	 * Depending on direction given and traversability, Pacman will attempt to move.
	 * Helper function checkCollision, checks next cell and deals with it. If pellet, ghost,fruit, ect..
	 */
	public void move() // Change direction to char? "Easier to Read" udlr
	{
		// TODO: extend this to introduce movement based on the direction given
		int pacX = loc.getX();
		int pacY = loc.getY();

		// Check neighboring cell for collisions!!
		if (direction == 'u' && checkCollisionPacman(pacX, pacY + 1)) { // GO to coordinate (x,y+1)
			loc.setNewLoc(pacX, pacY - 1);
			Log.d("Pacman-Moved: ", "Move: " + direction + "Location: " + pacX + "," + pacY);

		}
		if (direction == 'd' && checkCollisionPacman(pacX, pacY - 1)) {
			loc.setNewLoc(pacX, pacY + 1);
			Log.d("Pacman-Moved: ", "Move: " + direction + "Location: " + pacX + "," + pacY);

		}
		if (direction == 'l' && checkCollisionPacman(pacX - 1, pacY)) {
			loc.setNewLoc(pacX - 1, pacY);
			Log.d("Pacman-Moved: ", "Move: " + direction + "Location: " + pacX + "," + pacY);

		}
		if (direction == 'r' && checkCollisionPacman(pacX + 1, pacY)) {
			loc.setNewLoc(pacX + 1, pacY);
			Log.d("Pacman-Moved: ", "Move: " + direction + "Location: " + pacX + "," + pacY);

		}
	}


	/**
	 * Determines if Pacman has won
	 * Pacman wins when all the pellets and super pellets have been eaten
	 *
	 * @return if Pacman has won
	 */
	// TODO: make this not a stub, actually checks the pellets are all gone
	public boolean hasWon(Maze m)
	{
		return m.getNumPelletsRemaining() == 0;
	}
}
