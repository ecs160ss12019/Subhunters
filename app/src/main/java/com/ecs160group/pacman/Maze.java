package com.ecs160group.pacman;

import android.graphics.Color;
import android.util.Log;

class Maze
{
	final static int MAZE_WIDTH = 28;
	final static int MAZE_HEIGHT = 31;


	private PacmanActivity pacmanActivity;
	private Location[][] grid; // holds all the objects/pieces currently in the maze


	/**
	 * Constructor for the Maze
	 * @param pacmanActivity
	 */
	public Maze(PacmanActivity pacmanActivity)
	{
		this.pacmanActivity = pacmanActivity;
		// TODO: initialize the grid
	}

	/**
	 * Draws the maze on the screen
	 */
	void draw()
	{
		//  pacmanActivity.gameView.setImageBitmap(pacmanActivity.blankBitmap);
		//print the whole graph
		//pacmanActivity.canvas.drawColor(Color.argb(255, 255, 255, 255));

		//print the wall

		//print the Pacman's position after the last order

		//using get() function in location and add the fixed value of order direction to the coordinate

		//print the ghosts

		//print items (fruits and more)

		//debugging
		Log.d("Debugging", "In draw");
	}

	/**
	 * Gets the array of maze locations
	 * @return array of maze locations
	 */
	Location[][] getMaze() {
		return grid;
	}
}
