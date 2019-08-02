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
import java.util.concurrent.BlockingQueue;

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
	public char direction;


	// Pacman's power-up state and timer.
	private boolean powerState;
	private int powerTimer;
	Block block;

	public Location spawnLoc;
	private boolean isDead;
	private Score score;

	//Sounds to be played during gameplay.
	private sound PacmanSounds;

	// use an integer to temporarily replace the draw of Pacman
	// this will be modified under the draw function
	int pacImage = 1;

	//RectF has four values (left, top, right, bottom)

	float velocity;
	private float radius;
	final Paint paint = new Paint();

	Location[][] mGrid;
	private Location leftOfPac;
	private Location rightOfPac;
	private Location bottomOfPac;
	private Location topOfPac;

	Pacman(int screenX, Location spawnLoc, float radius, Score score, Context activityContext, Maze mMaze)
	{
		mGrid = mMaze.getMaze();
		this.score = score;
		PacmanSounds = new sound(activityContext);
		paint.setColor(Color.argb(255, 255, 255, 0));
		//pacman width/height 1% of screen (change later if needed)
		this.radius = radius;
		this.spawnLoc = spawnLoc;
		loc = new Location(spawnLoc.getX(), spawnLoc.getY(), Block.PACMAN);
		gridLocation = new Location(13, 29, Block.PACMAN); // 13,29 Used to keep track of own grid position.
		Log.d("Pacman-gridlocation: ", "Location: " + gridLocation.getX() + "," + gridLocation.getY());
		direction = ' ';
		powerState = false;
		powerTimer = 0;
		velocity = screenX / 15;
		leftOfPac = new Location(gridLocation.getX() - 1, gridLocation.getY(), mGrid[gridLocation.getX() - 1][gridLocation.getY()].getObj());
		rightOfPac = new Location(gridLocation.getX() + 1, gridLocation.getY(), mGrid[gridLocation.getX() + 1][gridLocation.getY()].getObj());
		topOfPac = new Location(gridLocation.getX(), gridLocation.getY() - 1, mGrid[gridLocation.getX()][gridLocation.getY() - 1].getObj());
		bottomOfPac = new Location(gridLocation.getX(), gridLocation.getY() + 1, mGrid[gridLocation.getX()][gridLocation.getY() + 1].getObj());
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
	public boolean getIsDead()
	{
		return isDead;
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
		//Log.d("Debugging", "checkPowerUpState" + powerTimer);
		if (powerState == true ||  powerTimer > 0) {
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
			return true;
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
			//loc.setNewLoc((int) (loc.getX() - (velocity / fps)), loc.getY());
			gridLocation.setNewLoc(pacGridX - 1, pacGridY);
		} else if (direction == 'r') {
			//loc.setNewLoc((int) (loc.getX() + (velocity / fps)), loc.getY());
			gridLocation.setNewLoc(pacGridX + 1, pacGridY);
		} else if (direction == 'u') {
			//loc.setNewLoc(loc.getX(), (int) (loc.getY() - (velocity / fps)));
			gridLocation.setNewLoc(pacGridX, pacGridY - 1);

		} else if (direction == 'd') {
			//loc.setNewLoc(loc.getX(), (int) (loc.getY() + (velocity / fps)));
			gridLocation.setNewLoc(pacGridX, pacGridY + 1);
		}
		//Log.d("Pacman-update: ", "New_LOC: " + direction + " , Location: " + pacGridX + "," + pacGridY);
		updateSurroundingSpaces();
	}

	/**
	 * updates the surrounding spaces of pacman
	 * is called in update() method (when we update pacman, we update its surroundings)
	 */
	void updateSurroundingSpaces(){

		//only update surrounding spaces if we aren't going out of bounds
		if (gridLocation.getX() - 1 >= 0 && gridLocation.getX() + 1 < mGrid.length && gridLocation.getY() - 1 >= 0 && gridLocation.getY() + 1 < 31 ) {
			leftOfPac.updateLoc(gridLocation.getX() - 1, gridLocation.getY(), mGrid[gridLocation.getX() - 1][gridLocation.getY()].getObj());
			rightOfPac.updateLoc(gridLocation.getX() + 1, gridLocation.getY(), mGrid[gridLocation.getX() + 1][gridLocation.getY()].getObj());
			topOfPac.updateLoc(gridLocation.getX(), gridLocation.getY() - 1, mGrid[gridLocation.getX()][gridLocation.getY() - 1].getObj());
			bottomOfPac.updateLoc(gridLocation.getX(), gridLocation.getY() + 1, mGrid[gridLocation.getX()][gridLocation.getY() + 1].getObj());
		}
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
	public boolean wallDetection()
	{


		// this value will be used for deciding update or not
		boolean update;
		update = true;

		//read in Grid and grid indices of current location
		// If current position is warp, DO NOT update position.
		if (mGrid[gridLocation.getX()][gridLocation.getY()].getObj() != block.WARP_SPACE) {
			// if Pacman will hit the right wall, stop
			if (direction == 'r') {
				if (rightOfPac.isWall()) {
					//Log.d("PACMAN HAS HIT A WALL:", "direction:" + direction);
					update = false;
				}
			}
			//if pacman will hit the left wall, stop
			if (direction == 'l') {
				if (leftOfPac.isWall()) {
					//Log.d("PACMAN HAS HIT A WALL:", "direction:" + direction);
					update = false;
				}
			}
			//if Pacman will hit the top wall, stop
			if (direction == 'u') {
				if (topOfPac.isWall()) {
					//Log.d("PACMAN HAS HIT A WALL:", "direction:" + direction);
					update = false;
				}
			}
			//if Pacman will hit the bottom wall, stop
			if (direction == 'd') {
				if (bottomOfPac.isWall()) {
					//Log.d("PACMAN HAS HIT A WALL:", "direction:" + direction);
					update = false;
				}
			}
		}
		return update;
	}

	/**
	 *
	 * @return boolean to see if joystick's current direction is valid to move Pacman into a different direction
	 *
	 */

	public void updateNextDirection(char joyStickDirection) {

		//if (mGrid[gridLocation.getX()][gridLocation.getY()].getObj() != block.WARP_SPACE) {

			if (joyStickDirection == 'l' && !(leftOfPac.isWall())) {
				direction = 'l';
			}

			if (joyStickDirection == 'r' && !(rightOfPac.isWall())) {
				direction = 'r';
			}
			if (joyStickDirection == 'u' && !(topOfPac.isWall())) {
				direction = 'u';

			}
			if (joyStickDirection == 'd' && !(bottomOfPac.isWall())) {
				direction = 'd';
			}
		//}
	}




	/**
	 * Handles the Pacman and ghost collisions using their location in the grid/maze
	 * Depending on state pacman will eat the ghost or die (Player loses a life.)
	 * @return Return death or ghost eaten
	 */
	public boolean ghostCollision(Ghost mGhost, Score mScore){
		if(gridLocation.getX() == mGhost.gridLocation.getX() && gridLocation.getY() == mGhost.gridLocation.getY()){
			if(isSuper())
			{
				mGhost.setDeathState(75, true);
				PacmanSounds.pacmanEatGhost();
				mGhost.gridLocation.setNewLoc(13, 14); // TODO: remove hardcoded values.
				mScore.ateGhost();
				pauseStartDeath(300);
				return true;
			}
			else{ // pacman death.
				PacmanSounds.pacmanDeath();
				isDead = true;
				return true;
			}
		}
		return false;
	}

	/**
	 * Handles the interactions between the objects. States, maze updates, sounds.
	 */
	void collisionInteraction(Ghost inky, Ghost pinky, Ghost blinky, Ghost clyde, Maze mMaze, Score mScore){
		// TODO: Need to fix pacman's update to also save/update the grid coordinate.

		//Log.d("Pacman-Detect_Collision: ", "pacmanGridValues: " + "Location: " + pacGridX + "," + pacGridY);
		//Log.d("Pacman-Detect_Collision: ", "detect collision OBJECT gameactivity: " + mMaze.getMaze()[xPac][yPac].getObj());


		switch(mMaze.getMaze()[pacGridX][pacGridY].getObj()){
			case PELLET:
				// Placeholder, encounters pellet set empty.
                //PacmanSounds.pacmanChomp();
				mMaze.getMaze()[pacGridX][pacGridY].updateLoc(pacGridX, pacGridY, block.EMPTY);
				mScore.atePellet();
				//Log.d("Debugging", "In Collision Interact: PELLET");
				break;
			case POWER_PELLET: // Encounter PowerPellet, set state
				//Log.d("Debugging", "In Collision Interact: POWER_PELLET");
				mMaze.getMaze()[pacGridX][pacGridY].updateLoc(pacGridX, pacGridY, block.EMPTY);
				PacmanSounds.pacmanPowerup();
				setPowerUpState(75,true);// 540 is amount of frames time to be decremented EVERY FRAME
				mScore.atePowerPellet();
				break;
			case FRUIT: // Adjust for different fruits.
				// TODO: give score depending on spawned fruit.
				// Score(); // score class?
				mMaze.getMaze()[pacGridX][pacGridY].updateLoc(pacGridX, pacGridY, block.FRUIT_SPAWN); // Reset fruit position. (FRUIT_SPAWN = Set empty)
				PacmanSounds.pacmanEatFruit();
				pauseStartDeath(100);
				break;
			case WARP_SPACE: // Swap Pacman's location depending on warp entrance.
				// Possible loop here? Updating Pacman's location on top of WARP location! Double check.
				Log.d("Pac-Detect_Collision: ", "pacmanGridValues: " + "Location: " + pacGridX + "," + pacGridY);
				if(pacGridX == 0 && pacGridY == 14){
					//Log.d("Debugging", "In Collision Interact: WARP_SPACE, Pacman warped[Left->Right].");
					gridLocation.setNewLoc(26, 14); // Change to locate grid coordinates, not screen position
				}
				else{
					//Assumed opposite side. Change Pacman's location
					//Log.d("Debugging", "In Collision Interact: WARP_SPACE, Pacman warped[Right->Left].");
					gridLocation.setNewLoc(1, 14);
				}
				Log.d("Debugging", "In Collision Interact: WARP_SPACE, Pacman warped.");
				break;
			case EMPTY: // empty space, Nothing happens. Continue movement.
				//Log.d("Debugging", "In Collision Interact: EMPTY");
				break;
			default:
				Log.d("Debugging", "In Collision Interact Default");
				break;
		}


	}

	/**
	 * resets pacman's location to its original spawn
	 * called at PacmanGame's startNewGame() and reset()
	 * (whenever user starts a new game or dies to a ghost)
	 */
	void reset()
	{

		//loc.setNewLoc(spawnLoc.getX(), spawnLoc.getY());
		gridLocation.setNewLoc(13, 23); // TODO: Hard coded spawn, change later.
		direction = ' ';
		powerTimer = 0;
		powerState = false;
		isDead = false;

	}

	//TODO: will use variable direction later on for image purposes

	//circle for now, may need to be modified

	/**
	 * Draws Pacman on screen
	 */
	public void draw(Canvas canvas, Paint mPaint, float radius)
	{
		mPaint.setColor(Color.argb(255, 255, 255, 0));
		//canvas.drawCircle(loc.getX(), loc.getY(), radius, mPaint);

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
