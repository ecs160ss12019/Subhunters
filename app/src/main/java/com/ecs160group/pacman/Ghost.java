package com.ecs160group.pacman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import java.util.Random;


/**
 * Ghost class for movement and interaction with Pacman and the maze
 * Ghost's movement will check for closest space to target but will not choose
 * behind the ghost, since a ghost cannot make 180 degree turns and will insteda
 * turn left or right since one will be closer to target if forward is not possible
 * Only time a Ghost will turn 180 degrees is when pacman initially turns super
 */
public class Ghost implements Collision
{

	//pixel(maze) coords
	protected Pacman pacman;
	protected Location loc;
	protected Location scatterLoc;

	//ghost coords//directions
	//private int direction;
	private int next_direction;
	private char direction;

	// TODO: add explenations for these two vars here
	private int deathTimer = 0;
	private boolean isDead = false;

	// tells if ghost has been initialized into the grid and is not in waiting room
	private boolean started;


	//the draw image of ghost
	int ghostImage = 1;

	//RectF has four values (left, top, right, bottom)

	float velocity;
	float mXVelocity;
	float mYVelocity;
	float mGhostWidth;
	float mGhostHeight;
	Paint paint = new Paint();


	/**
	 * Non-parameterized Ghost ctor
	 */
	public Ghost()
	{

	}

	/**
	 * Parameterized Ghost ctor
	 * @param screenX
	 * @param locX
	 * @param locY
	 */
	public Ghost(int screenX, int locX, int locY)
	{
		paint.setColor(Color.argb(255, 0, 0, 255));
		//pacman width/height 1% of screen (change later if needed)
		mGhostWidth = (float) screenX / 100;
		mGhostHeight = (float) screenX / 100;
		velocity  = screenX / 80;

		loc = new Location(locX, locY, Block.GHOST);

	}

	/**
	 *
	 * @param dTimer
	 * @param dState
	 */
	public void setDeathState(int dTimer, boolean dState) {
		deathTimer = dTimer;
		isDead = dState;
	}

	/**
	 *
	 */
	public void checkDeathTimer(){
		if(isDead == true || deathTimer != 0){
			setDeathState(deathTimer - 1, true);
			if (deathTimer <= 0) {
				setDeathState(0, false);
			}
		}
	}

	/**
	 * Checks if the ghost has been spawned in the grid
	 * @return if the ghost has been spawned
	 */
	private boolean isInGrid()
	{
		return started;
	}


	/**
	 * updates the ball position
	 * called each frame/loop from PacmanGame update() method
	 * moves ghost based on x/y velocities and fps
	 */
	Random rand = new Random();
	int randDirection = rand.nextInt(4);
	int newRandDir = rand.nextInt(100);

	int directionCount = 15;
	boolean newDir = false;
	void update(long fps)
	{
		Log.d("ghost update:", "Random:" + randDirection);
		if (randDirection == 0) {
			loc.setNewLoc((int) (loc.getX() - (velocity / fps)), loc.getY());
			directionCount++;
			direction = 'l';
			if(directionCount > newRandDir){ // TODO: Add || on collision to change direction.
				randDirection = rand.nextInt(4);
				directionCount = 0;
			}
		}
		else if (randDirection == 1) {
			loc.setNewLoc((int) (loc.getX() + (velocity / fps)), loc.getY());
			directionCount++;
			direction = 'r';
			if(directionCount > newRandDir){
				randDirection = rand.nextInt(4);
				directionCount = 0;
			}
		}
		else if (randDirection == 2) {
			loc.setNewLoc(loc.getX(), (int) (loc.getY() - (velocity / fps)));
			directionCount++;
			direction = 'd';
			if(directionCount > newRandDir){
				randDirection = rand.nextInt(4);
				directionCount = 0;
			}
		}
		else if (randDirection == 3) {
			loc.setNewLoc(loc.getX(), (int) (loc.getY() + (velocity / fps)));
			directionCount++;
			direction = 'u';
			if(directionCount > newRandDir){
				randDirection = rand.nextInt(4);
				directionCount = 0;
			}
		}
		else{
			Log.d("ghost update:", "No Direction:");
		}
	}

	void reverseXVel()
	{
		mXVelocity = -mXVelocity;
	}

	void reverseYVel()
	{
		mYVelocity = -mYVelocity;
	}

	/**
	 * Initializes four points of mRect(defines pacman)
	 * Initializes x and y velocities (can change later)
	 */
	// TODO: not sure if we needs this
	void reset(int x, int y)
	{
		velocity = (float)(x / 3) ;
		loc.setNewLoc(800,400);

		//mXVelocity = (float) (y / 3);
		//mYVelocity = (float) -(y / 3);
	}


	//function to check if ghost is within the bounds of the maze
	void checkBounds()
	{

	}

	//function to draw the ghosts
	//can draw all four at once here or can implement functions for each of the four
	public void draw(Canvas canvas, Paint mPaint, float radius)
	{
		mPaint.setColor(Color.argb(255, 0, 0, 255));
		canvas.drawCircle(loc.getX(), loc.getY(), radius, mPaint);
	}

	/**
	 * When Pacman isn't super, ghosts chase Pacman
	 * Overwritten by child classes based on their
	 * specific behavior
	 */
	void chase(Location chaseLocation)
	{
		moveTowardsTarget(chaseLocation);
	}

	/**
	 * When Pacman is super, ghosts scatter to their
	 * appropriate corner/coordinate
	 */
	void scatter(Location scatterLocation)
	{
		moveTowardsTarget(scatterLocation);
	}

	/**
	 * Private helper used by chase and scatter that moves towards target
	 *
	 * @param target target location to head towards
	 */
	private void moveTowardsTarget(Location target)
	{
		// keep moving towards target if pacman hasn't won and isn't dead
		if (pacman != null && !pacman.hasWon()) {
			if (target != null && isInGrid()) { // target exists within grid
				// TODO: get next location to move to in grid
				// TODO: if can move/next location exists: move, else: turn

			}
		}
	}

	/*
    check if ghost is in bounds
    the function will later be used to check collision
    between ghost and wall probably
 */
	public void isInBounds(int mScreenX, int mScreenY) {
		float radius = (mScreenX + mScreenY) / 200;

		//if ghost hits the right screen wall, stop
		if ( (loc.getX() + radius) > mScreenX) {
			Log.d("ghost has hit a wall:", "direction:" + direction);
			loc.setNewLoc((int) (mScreenX - radius), loc.getY());
		}

		//if ghost hits the left screen wall, stop
		// TODO: CHANGE IT TO IF HE HITS THE MAZE's LEFT WALL
		if ( (loc.getX() - radius) < 0) {
			Log.d("ghost has hit a wall:", "direction:" + direction);
			loc.setNewLoc( (int) (0 + radius) , loc.getY());

		}
		//if ghost hits the bottom screen wall
		if  ( (loc.getY() + radius) > mScreenY) {
			Log.d("ghost hit the bottom:", "direction:" + direction);
			loc.setNewLoc(loc.getX(), (int)(mScreenY - radius));
		}

		//if ghost hits the top screen wall
		if  ( (loc.getY() - radius) < 0)  //up
		{
			Log.d("ghost hit upper wall:", "direction:" + direction);
			loc.setNewLoc(loc.getX(), (int)(0 + radius) );
		}

	}

	/**
	 * check if there is a collision for ghost at the given location
	 * @param loc
	 * @param mScreenX
	 * @param mScreenY
	 * @return
	 */
	public boolean detectCollision(Location loc, int mScreenX, int mScreenY)
	{
		return false;
	}
}