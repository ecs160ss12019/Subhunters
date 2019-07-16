package com.ecs160group.pacman;

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

	//initialize the position of Ghost at the beginning of the game
	void initialize(int sX, int sY){
		/*Random random = new Random();
		int xVal = random.nextInt((sX - sX/3) + 1) + sX/3;
		int yVal = random.nextInt((sY - sY/3) + 1) + sY/3;*/
		loc = new Location(sX, sY, ghostImage);
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
