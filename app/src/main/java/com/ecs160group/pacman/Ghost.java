package com.ecs160group.pacman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
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

	protected Maze maze;

	//ghost coords//directions
	//private int direction;
	private char direction;

	// TODO: add explanations for these two vars here
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
	public Location spawnLoc;


	private Point gridCoord;
	public Location gridLocation;

	/**
	 * Parameterized Ghost ctor
	 *
	 * @param screenX
	 * @param spawnLoc
	 * @param maze
	 */
	public Ghost(int screenX, Location spawnLoc, Maze maze, Pacman pacman)
	{
		this.maze = maze;
		paint.setColor(Color.argb(255, 0, 0, 255));
		//pacman width/height 1% of screen (change later if needed)
		mGhostWidth = (float) screenX / 100;
		mGhostHeight = (float) screenX / 100;
		velocity = screenX / 15;

		gridLocation = new Location(13, 11, Block.GHOST);
		gridCoord = new Point(gridLocation.getX(), gridLocation.getY());
		this.spawnLoc = spawnLoc;
		direction = 'l';

		loc = new Location(spawnLoc.getX(), spawnLoc.getY(), Block.GHOST);
		this.pacman = pacman;
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

	public Location getGridLoc()
	{
		return gridLocation;
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
	public boolean getDeathState()
	{
		return isDead;
	}

	/**
	 *
	 */
	public void checkDeathTimer()
	{
		if (isDead == true || deathTimer > 0) {
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
	 * updates the ghost position
	 * called each frame/loop from PacmanGame update() method
	 * moves ghost based on x/y velocities and fps
	 */
	Random rand = new Random();
	int randDirection = rand.nextInt(4);
	int newRandDir = rand.nextInt(100);

	int directionCount = 15;
	boolean newDir = false;

	/*void update(long fps) {
		gridCoord.x = gridLocation.getX();
		gridCoord.y = gridLocation.getY();
		Location l;
		l = new Location(loc.getX(), loc.getY(), Block.GHOST);
		//Log.d("ghost update:", "Random:" + randDirection);
		if (randDirection == 0){
			l.setNewLoc((int) (loc.getX() - (velocity / fps)), loc.getY());
			if (!detectCollision(l)) {
				loc.setNewLoc((int) (loc.getX() - (velocity / fps)), loc.getY());
				gridLocation.setNewLoc(gridCoord.x - 1, gridCoord.y);
				directionCount++;
				direction = 'l';
				if (directionCount > newRandDir) { // TODO: Add || on collision to change direction.
					randDirection = rand.nextInt(4);
					directionCount = 0;
				}
			}
		} else if (randDirection == 1) {
			loc.setNewLoc((int) (loc.getX() + (velocity / fps)), loc.getY());
			gridLocation.setNewLoc(gridCoord.x + 1, gridCoord.y);
			directionCount++;
			direction = 'r';
			if (directionCount > newRandDir) {
				randDirection = rand.nextInt(4);
				directionCount = 0;
			}
		} else if (randDirection == 2) {
			loc.setNewLoc(loc.getX(), (int) (loc.getY() + (velocity / fps)));
			gridLocation.setNewLoc(gridCoord.x, gridCoord.y + 1);
			directionCount++;
			direction = 'd';
			if (directionCount > newRandDir) {
				randDirection = rand.nextInt(4);
				directionCount = 0;
			}
		} else if (randDirection == 3)  {
			loc.setNewLoc(loc.getX(), (int) (loc.getY() - (velocity / fps)));
			gridLocation.setNewLoc(gridCoord.x, gridCoord.y - 1);
			directionCount++;
			direction = 'u';
			if (directionCount > newRandDir) {
				randDirection = rand.nextInt(4);
				directionCount = 0;
			}
		} else {
			*//*loc.setNewLoc(loc.getX(), loc.getY());
			directionCount++;
			randDirection = rand.nextInt(4);
			if (directionCount > newRandDir) {
				randDirection = rand.nextInt(4);
				directionCount = 0;*//*
			}

			//update(fps);
		}
*/

	/**
	 * detects the collisions of pacman with ghost, pellets, fruits,
	 * TODO: TESTING TO CHECK WALL DETECTION HERE
	 */
	// currently used for Pacman and ghost collision detection
	public boolean detectCollision(Location loc)
	{
		boolean collided = false;
		float radius = 29;
		if (Math.abs(this.loc.getX() - loc.getX()) <= radius * 2
				&& Math.abs(this.loc.getY() - loc.getY()) <= radius * 2) {
			collided = true;
		}
		return collided;
	}


	void update(long fps)
	{
		gridCoord.x = gridLocation.getX();
		gridCoord.y = gridLocation.getY();
		//Log.d("ghost update:", "Random:" + randDirection);
		//Log.d("Ghost-update: ", "Current_LOC: " + direction + " Location: " + gridCoord.x + "," + gridCoord.y);
		if (randDirection == 0 && canMoveTo(maze.getMaze()[gridCoord.x - 1][gridCoord.y])) {
			loc.setNewLoc((int) (loc.getX() - (velocity / fps)), loc.getY());
			gridLocation.setNewLoc(gridCoord.x - 1, gridCoord.y);
			directionCount++;
			direction = 'l';
			if (directionCount > newRandDir) { // TODO: Add || on collision to change direction.
				randDirection = rand.nextInt(4);
				directionCount = 0;
			}
		} else if (randDirection == 1 && canMoveTo(maze.getMaze()[gridCoord.x + 1][gridCoord.y])) {
			loc.setNewLoc((int) (loc.getX() + (velocity / fps)), loc.getY());
			gridLocation.setNewLoc(gridCoord.x + 1, gridCoord.y);
			directionCount++;
			direction = 'r';
			if (directionCount > newRandDir) {
				randDirection = rand.nextInt(4);
				directionCount = 0;
			}
		} else if (randDirection == 2 && canMoveTo(maze.getMaze()[gridCoord.x][gridCoord.y + 1])) {
			loc.setNewLoc(loc.getX(), (int) (loc.getY() + (velocity / fps)));
			gridLocation.setNewLoc(gridCoord.x, gridCoord.y + 1);
			directionCount++;
			direction = 'd';
			if (directionCount > newRandDir) {
				randDirection = rand.nextInt(4);
				directionCount = 0;
			}
		} else if (randDirection == 3 && canMoveTo(maze.getMaze()[gridCoord.x][gridCoord.y - 1])) {
			loc.setNewLoc(loc.getX(), (int) (loc.getY() - (velocity / fps)));
			gridLocation.setNewLoc(gridCoord.x, gridCoord.y - 1);
			directionCount++;
			direction = 'u';
			if (directionCount > newRandDir) {
				randDirection = rand.nextInt(4);
				directionCount = 0;
			}
		} else {
			loc.setNewLoc(loc.getX(), loc.getY());
			directionCount++;
			randDirection = rand.nextInt(4);
			if (directionCount > newRandDir) {
				randDirection = rand.nextInt(4);
				directionCount = 0;
			}
			//update(fps);
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
		gridLocation.setNewLoc(13, 11);
		//deathTimer = 0; // Dealt with when added and removed to Graveyard
		//isDead = false;
		//mXVelocity = (float) (y / 3);
		//mYVelocity = (float) -(y / 3);
	}

	/**
	 * Called when the ghost is eaten
	 * Sets the timer and moves ghost to waiting room
	 */
	void eaten()
	{
		setDeathState(9000, true);
		// TODO: do more with this
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
		if (maze.isInBounds(chaseLocation)) {
			// if ghost has a chase that's out of bounds(Inky), don't look for the object in the maze, it's empty
			chaseLocation.setObj(maze.getMaze()[chaseLocation.getX()][chaseLocation.getY()].getObj());
		}
		moveTowardsTarget(chaseLocation);
	}

	/**
	 * When Pacman is super, ghosts scatter to their
	 * appropriate corner/coordinate
	 */
	void scatter(Location scatterLocation)
	{
		// sets the object at the target location before scattering
		scatterLocation.setObj(maze.getMaze()[scatterLocation.getX()][scatterLocation.getY()].getObj());
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
		next.setObj(maze.getMaze()[next.getX()][next.getY()].getObj());
		if (canMoveTo(next)) {
			minDist = Location.dist(next, target);
		}
		// compare with left
		Location left = loc.getLeft(direction);
		left.setObj(maze.getMaze()[left.getX()][left.getY()].getObj());
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
		right.setObj(maze.getMaze()[right.getX()][right.getY()].getObj());
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
		if (!Location.isValid(l) || l.isWall() || l.isGhostGate()) { // location exists, is not in bounds, or is a wall
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
		if (pacman != null && !pacman.hasWon(maze)) {
			// target exists and ghost is initialized in the grid
			if (target != null && isInGrid()) {
				Location next = canMove(minDistance(target));
				if (next != null) {
					beginMove(next);
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
	private void beginMove(Location next)
	{
		if (Location.isValid(next)) {
			Location ahead = loc.getAdjacentLocation(direction);
			if (ahead.getX() != next.getX() || ahead.getY() != next.getY()) {
				turn(next); // change direction if the location is not directly ahead
			}
		}
		moveTo(next);
	}


	/**
	 * Helper to turn towards a direction
	 * Gets the next location
	 *
	 * @param next next location to move to
	 */
	private void turn(Location next)
	{
		if (next == null) { // next could be null so just turn a random direction
			direction = rand.nextBoolean() ? 'r' : 'l';
		} else {
			if (next.isRight(direction)) { // if next is right, change direction to right
				direction = 'r';
			} else if (next.isLeft(direction)) { // if next is left, change direction to left
				direction = 'l';
			}
		}
	}

	/**
	 * Moves the Ghost to the neighboring location
	 *
	 * @param loc location to move to
	 */
	private void moveTo(Location loc)
	{
		Log.d("Doing a Move: ", "Moving " + this + "to " + loc.toString());
		gridLocation = loc;
		gridLocation.setObj(Block.GHOST);
	}


	/**
	 * To String representation of a Ghost
	 *
	 * @return string representation of a Ghost
	 */
	@Override
	public String toString()
	{
		return "Ghost at x=" + getLoc().getX() + ", y=" + getLoc().getY();
	}
}
