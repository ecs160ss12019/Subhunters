package com.ecs160group.pacman;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Random;

public class Ghost
{
	//used to interact to kill pacman or be eaten
	private Pacman pacman;


	//pixel(maze) coords
	protected Location loc;

	//ghost coords//directions
	private int direction;
	private int next_direction;

	//the draw image of ghost
	int ghostImage = 1;

	//RectF has four values (left, top, right, bottom)

	float mXVelocity;
	float mYVelocity;
	float mGhostWidth;
	float mGhostHeight;
	Paint paint = new Paint();

	public Ghost() {

	}

	public Ghost (int screenX, int locX, int locY) {

		paint.setColor(Color.argb(255, 0,0,255));
		//pacman width/height 1% of screen (change later if needed)
		mGhostWidth = (float)screenX/100;
		mGhostHeight = (float)screenX/100;



		loc = new Location(locX, locY, ghostImage);

	}


	/**
	 * updates the ball position
	 * called each frame/loop from PacmanGame update() method
	 * moves ball based on x/y velocities and fps
	 */
	void update(long fps) {

	}

	void reverseXVel(){
		mXVelocity = -mXVelocity;
	}

	void reverseYVel(){
		mYVelocity = -mYVelocity;
	}

	/**
	 * Initializes four points of mRect(defines pacman)
	 * Initializes x and y velocities (can change later)
	 */
	void reset(int x, int y) {


		mXVelocity = (float)(y / 3);
		mYVelocity = (float)-(y / 3);


	}


	//function to check if ghost is within the bounds of the maze
	void checkBounds(){

	}

	//function to draw the ghosts
	//can draw all four at once here or can implement functions for each of the four
	public void draw(){

	}
	/**
	 * When Pacman isn't super, ghosts chase Pacman
	 * Overwritten by child classes based on their
	 * specific behavior
	 */
	void chase()
	{

	}

	/**
	 * When Pacman is super, ghosts scatter to their
	 * appropriate corner/coordinate
	 */
	void scatter()
	{

	}

}
