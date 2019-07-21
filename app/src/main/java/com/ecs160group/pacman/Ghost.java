package com.ecs160group.pacman;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Random;

/**
 * Ghost class for movement and interaction with Pacman and the maze
 * Ghost's movement will check for closest space to target but will not choose
 * behind the ghost, since a ghost cannot make 180 degree turns and will insteda
 * turn left or right since one will be closer to target if forward is not possible
 */
public class Ghost
{

	//pixel(maze) coords
	protected Pacman pacman;
	protected Location loc;

	//ghost coords//directions
	private int direction;
	private int next_direction;

	// tells if ghost has been initialized into the grid and is not in waiting room
	private boolean started;


	//the draw image of ghost
	int ghostImage = 1;

	//RectF has four values (left, top, right, bottom)

	float mXVelocity;
	float mYVelocity;
	float mGhostWidth;
	float mGhostHeight;
	Paint paint = new Paint();


	public Ghost()
	{

	}

	public Ghost(int screenX, int locX, int locY)
	{

		paint.setColor(Color.argb(255, 0, 0, 255));
		//pacman width/height 1% of screen (change later if needed)
		mGhostWidth = (float) screenX / 100;
		mGhostHeight = (float) screenX / 100;


		loc = new Location(locX, locY, Block.GHOST);

	}

	private boolean isInGrid()
	{
		return started;
	}


	/**
	 * updates the ball position
	 * called each frame/loop from PacmanGame update() method
	 * moves ghost based on x/y velocities and fps
	 */
	void update(long fps)
	{

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


		mXVelocity = (float) (y / 3);
		mYVelocity = (float) -(y / 3);


	}


	//function to check if ghost is within the bounds of the maze
	void checkBounds()
	{

	}

	//function to draw the ghosts
	//can draw all four at once here or can implement functions for each of the four
	public void draw()
	{

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
}
