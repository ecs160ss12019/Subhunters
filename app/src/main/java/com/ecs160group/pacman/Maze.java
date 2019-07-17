package com.ecs160group.pacman;

import android.util.Log;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;


class Maze
{
	final static int MAZE_WIDTH = 28;
	final static int MAZE_HEIGHT = 31;


	private PacmanActivity pacmanActivity;
	private Location[][] grid; // holds all the objects/pieces currently in the maze

	//variables to handle drawing
	private LevelCreator mlevelCreator;
	private SurfaceHolder mOurHolder;
	private Canvas mCanvas;
	private Paint mPaint;




	/**
	 * Constructor for the Maze
	 * @param pacmanActivity
	 */
	public Maze(PacmanActivity pacmanActivity)
	{
		this.pacmanActivity = pacmanActivity;
		// initializes the grid size
		grid = new Location[MAZE_WIDTH][MAZE_HEIGHT];
		mlevelCreator = new LevelCreator(grid);
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

		if (mOurHolder.getSurface().isValid()) {
			mCanvas = mOurHolder.lockCanvas();
			mCanvas.drawColor(Color.argb(255, 0, 0, 0));
			mPaint.setColor(Color.argb(255, 255, 255, 255));

			//getMaze();
			for (int i = 0; i < grid.length; i++) {

				for (int j = 0; j < grid[j].length; j++) {
					//grid[i][j] // Draw what is at the location.
					mCanvas.drawCircle(i, j, 1, mPaint);
					Log.d("getMaze: ", "coord: " + i + "," + j);

				}

			}
		}
		mOurHolder.unlockCanvasAndPost(mCanvas);

	}

	/**
	 * Gets the array of maze locations
	 * @return array of maze locations
	 */
	Location[][] getMaze() {

		return grid;
	}
	void setMaze(Location[][] m){
		grid = m;
	}
}
