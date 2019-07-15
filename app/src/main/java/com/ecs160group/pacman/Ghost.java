package com.ecs160group.pacman;

public class Ghost
{
	//used to interact to kill pacman or be eaten
	private Pacman pacman;


	//pixel(maze) coords
	private int x_maze;
	private int y_maze;

	//ghost coords//directions
	int x;
	int y;
	private int direction;
	private int next_direction;


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
