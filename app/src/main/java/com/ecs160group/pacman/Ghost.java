package com.ecs160group.pacman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;


/**
 * Ghost class for movement and interaction with Pacman and the maze
 * Ghost's movement will check for closest space to target but will not choose
 * behind the ghost, since a ghost cannot make 180 degree turns and will insteda
 * turn left or right since one will be closer to target if forward is not possible
 * Only time a Ghost will turn 180 degrees is when pacman initially turns super
 */
public class Ghost// implements Collision
{ // TODO: check if really needs/fix interface

	//pixel(maze) coords
	protected Pacman pacman;
	protected Location loc;
	protected Location scatterLoc;

	private Location[][] maze;

	//ghost coords//directions
	//private int direction;
	private int nextDirection;
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
	private Location spawnLoc;


	/**
	 * Non-parameterized Ghost ctor
	 */
	public Ghost()
	{

	}

	/**
	 * Parameterized Ghost ctor
	 *
	 * @param screenX
	 * @param spawnLoc
	 * @param maze
	 */
	public Ghost(int screenX, Location spawnLoc, Location[][] maze)
	{
		this.maze = maze;
		paint.setColor(Color.argb(255, 0, 0, 255));
		//pacman width/height 1% of screen (change later if needed)
		mGhostWidth = (float) screenX / 100;
		mGhostHeight = (float) screenX / 100;
		velocity = screenX / 15;

		this.spawnLoc = spawnLoc;

		loc = new Location(spawnLoc.getX(), spawnLoc.getY(), Block.GHOST);

	}

	/**
	 * Gets the location of the ghost
	 *
	 * @return location of the ghost
	 */
	public Location getLoc()
	{
		return loc;
	}

	/**
	 * Sets the death state of the ghost
	 *
	 * @param dTimer amount of time left on the death timer
	 * @param dState state of the ghost
	 */
	public void setDeathState(int dTimer, boolean dState)
	{
		deathTimer = dTimer;
		isDead = dState;
	}

	/**
	 *
	 */
	public void checkDeathTimer()
	{
		if (isDead == true || deathTimer != 0) {
			setDeathState(deathTimer - 1, true);
			if (deathTimer <= 0) {
				setDeathState(0, false);
			}
		}
	}

	/**
	 * Checks if the ghost has been spawned in the grid
	 *
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
		//Log.d("ghost update:", "Random:" + randDirection);
		if (randDirection == 0) {
			loc.setNewLoc((int) (loc.getX() - (velocity / fps)), loc.getY());
			directionCount++;
			direction = 'l';
			if (directionCount > newRandDir) { // TODO: Add || on collision to change direction.
				randDirection = rand.nextInt(4);
				directionCount = 0;
			}
		} else if (randDirection == 1) {
			loc.setNewLoc((int) (loc.getX() + (velocity / fps)), loc.getY());
			directionCount++;
			direction = 'r';
			if (directionCount > newRandDir) {
				randDirection = rand.nextInt(4);
				directionCount = 0;
			}
		} else if (randDirection == 2) {
			loc.setNewLoc(loc.getX(), (int) (loc.getY() - (velocity / fps)));
			directionCount++;
			direction = 'd';
			if (directionCount > newRandDir) {
				randDirection = rand.nextInt(4);
				directionCount = 0;
			}
		} else if (randDirection == 3) {
			loc.setNewLoc(loc.getX(), (int) (loc.getY() + (velocity / fps)));
			directionCount++;
			direction = 'u';
			if (directionCount > newRandDir) {
				randDirection = rand.nextInt(4);
				directionCount = 0;
			}
		} else {
			//Log.d("ghost update:", "No Direction:");
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
	void reset()
	{

		loc.setNewLoc(spawnLoc.getX(), spawnLoc.getY());
		deathTimer = 0;
		isDead = false;
		//mXVelocity = (float) (y / 3);
		//mYVelocity = (float) -(y / 3);
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
		// sets the object at the target location before scattering
		chaseLocation.setObj(maze[chaseLocation.getX()][chaseLocation.getY()].getObj());
		moveTowardsTarget(chaseLocation);
	}

	/**
	 * When Pacman is super, ghosts scatter to their
	 * appropriate corner/coordinate
	 */
	void scatter(Location scatterLocation)
	{
		// sets the object at the target location before scattering
		scatterLocation.setObj(maze[scatterLocation.getX()][scatterLocation.getY()].getObj());
		moveTowardsTarget(scatterLocation);
	}

	/**
	 * Private helper to find location that is closest to a location
	 *
	 * @param target location ghost wants to get closest to
	 * @return Location that is closest to target location
	 */
	private Location minDistance(Location target)
	{
		int minDist = 0;
		Location next = loc.getAhead(direction);
		// set object in maze after getting the location
		next.setObj(maze[next.getX()][next.getY()].getObj());
		if (canMoveTo(next)) {
			minDist = Location.dist(next, target);
		}
		// compare with left
		Location left = loc.getLeft(direction);
		left.setObj(maze[left.getX()][left.getY()].getObj());
		if (canMoveTo(left)) {
			// get distance between left and target location
			int distLeft = Location.dist(left, target);
			if (distLeft < minDist) { // test distance between the left and target against min
				minDist = distLeft;
				next = left;
			}
		}
		// compare with right
		Location right = loc.getRight(direction);
		right.setObj(maze[right.getX()][right.getY()].getObj());
		if (canMoveTo(right)) {
			int distRight = Location.dist(right, target);
			if (distRight < minDist) {
				next = right;
			}
		}
		return next;
	}

	/**
	 * Checks if the location can be moved to
	 *
	 * @param l location to check
	 * @return if the location given can be moved to
	 */
	private boolean canMoveTo(Location l)
	{
		if (!Location.isValid(l) || l.isWall()) { // location exists, is not in bounds, or is a wall
			return false;
		}
		return l.isEmpty() || l.isPellet() || (l.isPacman() && !pacman.isSuper());
	}

	/**
	 * Private helper to find is Ghost can move to the next space
	 *
	 * @param next nex location to move to
	 * @return
	 */
	private Location canMove(Location next)
	{
		return canMoveTo(next) ? next : null;
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
			// target exists and ghost is initialized in the grid
			if (target != null && isInGrid()) {
				Location next = canMove(minDistance(target));
				if (next != null) {
					move(next);
				} else {
					turn(next);
				}
			}
		}
	}

	/**
	 * Helper to accomplish move
	 *
	 * @param next the next
	 */
	private void move(Location next)
	{
		if (Location.isValid(next)) {
			Location ahead = loc.getAdjacentLocation(direction);
			if (ahead.getX() == next.getX() && ahead.getY() == next.getY()) {
				turn(next);
			}
			// TODO: do the movement
		}
	}

	/**
	 * Helper to turn towards a direction
	 * Gets the next location 
	 * @param next
	 */
	private void turn(Location next)
	{

	}

}
