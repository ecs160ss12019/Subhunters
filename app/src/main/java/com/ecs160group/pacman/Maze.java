package com.ecs160group.pacman;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;


class Maze
{
	final static int MAZE_WIDTH = 28;
	final static int MAZE_HEIGHT = 31;

	private Location[][] grid; // holds all the objects/pieces currently in the maze

	//variables to handle drawing
	private LevelCreator mlevelCreator;
	public Bitmap bitmap;
	public PointF blockSize;
	private Context context;

	//holds resolution of screen
	private int mScreenX;
	private int mScreenY;
	private int xScaled;
	private int yScaled;


	/**
	 * Constructor for Maze
	 *
	 * @param context context from PacmanActivity
	 */
//	public Maze(PointF blockSize,  Canvas mCanvas, Context context)
	public Maze(Context context, int xScreen, int yScreen)
	{
		this.context = context;
		mScreenX = xScreen;
		mScreenY = yScreen;
		//this.pacmanActivity = pacmanActivity;
		// initializes the grid size
		grid = new Location[MAZE_WIDTH][MAZE_HEIGHT];
		mlevelCreator = new LevelCreator(grid, context);
		xScaled = mScreenX / 2;
		yScaled = mScreenY / 12;
	}

	/**
	 * Draws the maze on the screen
	 */
	void draw(Canvas mCanvas, Paint mPaint)
	{
		//TODO: EDIT THIS
		//print the whole graph


		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				// print the grid piece on the canvas
				drawSpace(grid[i][j], mCanvas, mPaint);
			}
		}


		//print the wall
		//print the Pacman's position after the last order
		//using get() function in location and add the fixed value of order direction to the coordinate
		//print the ghosts
		//print items (fruits and more)

		/*
		//debugging
		Log.d("Debugging", "In draw");

		int xScaled = mScreenX / 2;
		int yScaled = mScreenY / 12;

		for (int i = 0; i < MAZE_WIDTH; i++) {
				for (int j = 0; j < MAZE_HEIGHT; j++) {
					//grid[i][j] // Draw only if block at the location.
					if(grid[i][j].getObj() == Block.WALL){
						mCanvas.drawCircle(i * 28 + xScaled, j * 28 + yScaled, 4, mPaint);
						Log.d("getMaze: ", "coord: (" + i + "," + j +")");
					}
				}
			}
			*/

	}


	/**
	 * Gets the array of maze locations
	 *
	 * @return array of maze locations
	 */
	Location[][] getMaze()
	{
		return grid;
	}

	void setMaze(Location[][] m)
	{
		grid = m;
	}

	/**
	 * Draws a single object in the designated space in the maze
	 *
	 * @param l location of the object to draw
	 */
	private void drawSpace(Location l, Canvas mCanvas, Paint mPaint)
	{
		Resources res = Resources.getSystem();
		int img = -1;
		// TODO: draw each image in the location and put them in res/drawable
		switch (l.getObj()) {
			case PACMAN:
				img = R.drawable.pacman;
				break;
			case GHOST: // TODO: extend and change the ghosts to input for each NAMED ghost
				img = R.drawable.blinky;
				break;
			case WALL:
				mPaint.setColor(Color.argb(255, 0, 0, 255));
				mCanvas.drawCircle(l.getX() * 28 + xScaled, l.getY() * 28 + yScaled, 6, mPaint);
				//Log.d("getMaze: ", "coord: (" + l.getX() + "," + l.getY() +")");
				//img = R.drawable.wall; // TODO: extend to input different wall type pieces
				break;
			case PELLET:
				//img = R.drawable.pellet;
				mPaint.setColor(Color.argb(255, 0, 255, 255));
				mCanvas.drawCircle(l.getX() * 28 + xScaled, l.getY() * 28 + yScaled, 3, mPaint);
				break;
			case POWER_PELLET:
				mPaint.setColor(Color.argb(255, 0, 255, 255));
				mCanvas.drawCircle(l.getX() * 28 + xScaled, l.getY() * 28 + yScaled, 4, mPaint);
				//img = R.drawable.power_pellet;
				break;
				/*
			case WARP_SPACE:
				img = R.drawable.warp;
				break;
			case GHOST_GATE:
				img = R.drawable.ghost_gate;
				break;*/

			case PAC_SPAWN:
			case GHOST_SPAWN:
			case EMPTY: // empty space, no need to draw anything
			default:
				img = 0;
				break;
		}
		if (img != -1) {// not an empty space, print image
			// TODO: draw image at the space
			//Bitmap unsizedObjBM = BitmapFactory.decodeResource(res, img);
			// TODO: get the optimal size needed for each obj in the maze and then put it here
			//Bitmap sizedBm = Bitmap.createScaledBitmap(unsizedObjBM, 50, 50, false);
			//drawImage(sizedBm, l, mCanvas);
		}
	}

	/**
	 * Draws an image to the location based on the
	 *
	 * @param bm correctly sized bitmap of the image to draw
	 * @param l  location to draw the image at
	 */
	void drawImage(Bitmap bm, Location l, Canvas mCanvas)
	{
		// TODO: make this commented out thing with location plus the sizing we need
		// bitmap width and height are predetermined widths and heights for items in the maze
		float bitmapWidth = (mScreenX + mScreenY) / 200;
		float bitmapHeight = (mScreenX + mScreenY) / 200;
		mCanvas.drawBitmap(bm, l.getX() - bitmapWidth / 2, l.getY() - bitmapHeight / 2, null);
	}

	/**
	 * Static function to check if Location is in bounds
	 * @param l location to check
	 * @return if the location is in bounds
	 */
	public static boolean isInBounds(Location l)
	{
		return (l.getX() >= 0 && l.getX() < MAZE_WIDTH)
				&& (l.getY() >= 0 && l.getY() < MAZE_HEIGHT);
	}

/*
	* May be used to test current performance issues.
	 * temp method to draw the maze (hardcoded style)
	 * @param canvas canvas to draw on
	 * @param paint paint to paint
	void tempDraw(Canvas canvas, Paint paint){



	}
*/

}
