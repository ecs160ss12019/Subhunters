package com.ecs160group.pacman;

/**
 * Represents each location on the grid
 * Contains data regarding position in the maze
 * as well as what is at each position on the grid such as a
 * Ghost, Pacman, Wall piece, pellets, bonus fruit, or open space
 */
public class Location
{
	// coordinate position
	private int x;
	private int y;
	// object containing what is at this position
	// TODO: rename and refactor this to something more appropriate for its purpose
	Object piece;

	// TODO: potentially add more functions and functionality such as if there is more than one object at  a location

	Location (int x, int y, Object o)
	{
		this.x = x;
		this.y = y;
		piece = o;
	}

	public int getX() {
		return x;
	}

	public int getY()
	{
		return y;
	}

	public Object getObj()
	{
		return piece;
	}
}
