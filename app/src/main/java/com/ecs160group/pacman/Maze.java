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

	private Location[][] grid;
	//so that we don't have to keep scaling every location we get to draw
	//we can just take it from this grid
	public Location[][] scaledGrid;// holds all the objects/pieces currently in the maze

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
	public Pacman mPacman;
	public Blinky mBlinky;
	public Inky mInky;
	public Pinky mPinky;
	public Clyde mClyde;
	public Ghost mGhost;

	public Location pacSpawn;

	//do all four ghosts later
	public Location ghostSpawn;

	/*public Location blinkySpawn;
	public Location inkySpawn;
	public Location pinkySpawn;
	public Location clydeSpawn;*/


	/**
	 * Constructor for Maze
	 *
	 * @param context context from PacmanActivity
	 */
//	public Maze(PointF blockSize,  Canvas mCanvas, Context context)
	public Maze(Context context, int xScreen, int yScreen, PointF blockSize)
	{
		this.blockSize = new PointF();
		this.blockSize = blockSize;
		this.context = context;
		mScreenX = xScreen;
		mScreenY = yScreen;
		//this.pacmanActivity = pacmanActivity;
		// initializes the grid size
		grid = new Location[MAZE_WIDTH][MAZE_HEIGHT];
		mlevelCreator = new LevelCreator(grid, context);
		xScaled = mScreenX / 2;
		yScaled = mScreenY / 12;
		scaledGrid = grid;
		pacSpawn = scaledGrid[13][23];
		ghostSpawn = scaledGrid[13][11];
		scaleGrid();
		//initPacAndGhostSpawns();



	}

	/**
	 * Draws the maze on the screen
	 */
	void draw(Canvas mCanvas, Paint mPaint)
	{
		//TODO: EDIT THIS
		//print the whole graph

		//update();
		drawOuterBoundary(mCanvas, mPaint);


		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				// print the grid piece on the canvas
				drawSpace(scaledGrid[i][j], mCanvas, mPaint);
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
	 * called from draw() , which is in turn called by draw() in PacmanGame
	 * draws the square that encircles the grid
	 * putting it here so PacmanGame is cleaner
	 */
	private void drawOuterBoundary(Canvas mCanvas, Paint mPaint) {

		mPaint.setColor(Color.argb(255, 0, 0, 255));

		mCanvas.drawLine(blockSize.y * 25, blockSize.y * 1,
				blockSize.y * 25, blockSize.y * 29,
				mPaint);
		mCanvas.drawLine(blockSize.y * 52, blockSize.y * 1,
				blockSize.y * 52, blockSize.y * 29,
				mPaint);
		mCanvas.drawLine(blockSize.x * 25, blockSize.x * 1,
				blockSize.x * 52, blockSize.x * 1,
				mPaint);
		mCanvas.drawLine(blockSize.x * 25, blockSize.x * 29,
				blockSize.x * 52, blockSize.x * 29,
				mPaint);

	}

	/**
	 * function to scale the grid (all x and y coordinates of the locations)
	 * so that we don't have to keep scaling the locations every time we want to draw/update a location
	 */
	private void scaleGrid(){
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				scaledGrid[i][j].updateLoc(grid[i][j].getX() * 28 + xScaled,
						grid[i][j].getY() * 28 + yScaled, grid[i][j].getObj());
			}
		}
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
	 * function to change pac_spawn and ghost_spawn to pacman and ghost respectively in grid
	 */
	private void initPacAndGhostSpawns() {

		//for loop later to find the pacman/ghost spawns

		pacSpawn = scaledGrid[13][23];
		ghostSpawn = scaledGrid[13][11];



		//no need to do this anymore since we are drawing ghost/pac in pacmangame, just need the locations
		//		to pass into the pacmna and ghost objects in pacmangame
		/*grid[13][11].updateLoc(grid[13][11].getX(), grid[13][11].getY(), Block.GHOST);
		grid[14][11].updateLoc(grid[14][11].getX(), grid[13][11].getY(), Block.GHOST);
		grid[13][23].updateLoc(grid[13][23].getX(), grid[13][23].getY(), Block.PACMAN);*/


		//for loop not working to detect PAC_SPAWN for some reason
      /*  for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                // print the grid piece on the canvas

                if (grid[i][j].getObj() == Block.PAC_SPAWN) {
                	Log.d("in pac_spawn object: ", "changing to pacman: " + grid[i][i].getObj());
                    grid[i][j].updateLoc(grid[i][j].getX(), grid[i][j].getY(), Block.PACMAN);
					Log.d("in pac_spawn object: ", "changing to pacman: " + grid[i][i].getObj());
                }
                if (grid[i][j].getObj() == Block.GHOST_SPAWN) {
                    grid[i][j].updateLoc(grid[i][j].getX(), grid[i][j].getY(), Block.GHOST);
                }


            }
        }*/


    }

	/**
	 * Draws a single object in the designated space in the maze
	 *
	 * @param l location of the object to draw
	 */
	private void drawSpace(Location l, Canvas mCanvas, Paint mPaint)
	{
		//Resources res = Resources.getSystem();

		int img = -1;
		// TODO: draw each image in the location and put them in res/drawable
		switch (l.getObj()) {
			/*case PACMAN:
				*//*mPaint.setColor(Color.argb(255, 255, 255, 0));
				mCanvas.drawCircle(l.getX() * 28 + xScaled, l.getY() * 28 + yScaled, 14, mPaint);*//*

				//Log.d("hi im pacman: ", "im pacman" + l.getObj());
				img = R.drawable.pacman;
				break;
			case GHOST:
				 //TODO: extend and change the ghosts to input for each NAMED ghost
				*//*mPaint.setColor(Color.argb(255, 0, 0, 255));
				mCanvas.drawCircle(l.getX() * 28 + xScaled, l.getY() * 28 + yScaled, 14, mPaint);*//*
				//Log.d("hi im a ghost", "im a ghost" + l.getObj());
				img = R.drawable.blinky;
				break;*/
			case HORIZONTAL_WALL:
				mPaint.setColor(Color.argb(255, 0, 0, 255));
				mCanvas.drawLine(l.getX() - 16, l.getY() , l.getX() + 16, l.getY(), mPaint);
				break;
			case VERTICAL_WALL:
				mPaint.setColor(Color.argb(255, 0, 0, 255));
				mCanvas.drawLine(l.getX(), l.getY() - 16 , l.getX(), l.getY() + 16, mPaint);
				break;
			case BOT_LEFT_TOP_RIGHT_WALL:
				mPaint.setColor(Color.argb(255, 0, 0, 255));
				mCanvas.drawLine(l.getX() - 5, l.getY() - 6, l.getX() + 5, l.getY() + 6, mPaint);
				break;
			case BOT_RIGHT_TOP_LEFT_WALL:
				mPaint.setColor(Color.argb(255, 0, 0, 255));
				mCanvas.drawLine(l.getX() - 5, l.getY() + 6, l.getX() + 5, l.getY() - 6, mPaint);
				break;
			case WALL:
				mPaint.setColor(Color.argb(255, 0, 0, 255));
				mCanvas.drawCircle(l.getX(), l.getY(), 6, mPaint);
				//Log.d("getMaze: ", "coord: (" + l.getX() + "," + l.getY() +")");
				//img = R.drawable.wall; // TODO: extend to input different wall type pieces
				break;
			case PELLET:
				mPaint.setColor(Color.argb(255, 155, 155, 0));
				mCanvas.drawCircle(l.getX(), l.getY(), 3, mPaint);
				break;
			case POWER_PELLET:
				mPaint.setColor(Color.argb(255, 255, 155, 0));
				mCanvas.drawCircle(l.getX(), l.getY(), 10, mPaint);
				break;
				//img = R.drawable.power_pellet;
				/*
			case WARP_SPACE:
				img = R.drawable.warp;
				break;
			case GHOST_GATE:
				img = R.drawable.ghost_gate;
				break;*/
			/*case PAC_SPAWN:
				Log.d("in pac_spawn", "hi pac_spawn" +l.getObj());
				//img = 0;
				//mPacman = new Pacman(mScreenX, l.getX(), l.getY());
				l.updateLoc(l.getX(), l.getY(), Block.PACMAN);
				Log.d("in pac_spawn", "hi pac_spawn" +l.getObj());//changes this to PACMAN
				break;*/
			/*case GHOST_SPAWN:
				img = 0;
			case EMPTY:
				img = 0;// empty space, no need to draw anything*/
			default:
				//img = 0;
				break;
		}
		if (img != -1) {// not an empty space, print image
			//Log.d("in yesDraw", "yesDraw" +l.getObj());
		//	Log.d("x coord: ", "l.getX(): " + (l.getX() * 28 + xScaled));
        //    Log.d("y coord: ", "l.getY(): " + (l.getY() * 28 + xScaled));
			/*Bitmap unsizedBitmap = BitmapFactory.decodeResource(context.getResources(), img);
			Bitmap sizedBitmap = Bitmap.createScaledBitmap(unsizedBitmap, 29, 29, false);
			mCanvas.drawBitmap(sizedBitmap, (l.getX() * 28 + xScaled) - + ((mScreenX + mScreenY) / 200),
					(l.getY() * 28 + yScaled) - + ((mScreenX + mScreenY) / 200) , null);*/
		}
	}

	/**
	 * Draws an image to the location based on the
	 *
	 * @param bm correctly sized bitmap of the image to draw
	 * @param l  location to draw the image at
	 */
	void drawImage(Bitmap bm, Location l, Canvas mCanvas) {
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

	/**
	 * function to change the BLOCK of PAC_SPAWN to PACMAN
	 * @param l location of PAC_SPAWN
	 */


	/** May be used to test current performance issues.
	 * temp method to draw the maze (hardcoded style)
	 * @param mCanvas canvas to draw on
	 * @param paint paint to paint
	 * @param l
	 */
	void tempDraw(Canvas mCanvas, Paint paint, Location l){
		Bitmap unsizedBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.blinky);
		Bitmap sizedBitmap = Bitmap.createScaledBitmap(unsizedBitmap, 29, 29, false);
		mCanvas.drawBitmap(sizedBitmap, l.getX() * 28
				+ xScaled, l.getY() * 28 + xScaled, null);


	}


	/**
	 * function to update the location of pacman (and the ghosts later)
	 *
	 */
	private void update() {

//		int x = mPacman.loc.getX();
		//int y = mPacman.loc.getY();
		//grid[x][y].updateLoc(x, y, Block.PACMAN);

		/*for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				Location l = grid[i][j];
				if (l.getObj() == Block.PACMAN && l.getX() != x && l.getY() != y) {
					grid[i][j].updateLoc(x, y, Block.PACMAN);
				}

			}
		}
*/
	}

	/**
	 * Given the location of a object, try to find its position in the maze
	 * @param l
	 * @return the grid values i and j
	 */
	public int [] getGridValues(Location l)
	{
		int gridVal[];
		gridVal = new int[2];

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j].getX() == l.getX() && grid[i][j].getY() == l.getY())
					gridVal[0] = i;
				gridVal[1] = j;
			}
		}
		return gridVal;
	}

	public int getNumPelletsRemaining()
	{
		int numPellets = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j].getObj() == Block.PELLET || grid[i][j].getObj() == Block.POWER_PELLET)
					numPellets++;
			}
		}
		return numPellets;
	}

}
