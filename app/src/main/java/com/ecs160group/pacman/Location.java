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
	Object obj;

	/**
	 * Constructor for the Location class
	 *
	 * @param x x axis position
	 * @param y y acis position
	 * @param o object held in the location
	 */
	Location(int x, int y, Object o)
	{
		this.x = x;
		this.y = y;
		obj = o;
	}

	/**
	 * Gets the x position of the location
	 *
	 * @return x position of the location
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * Gets the y position of the location
	 *
	 * @return y position of the location
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * Gets the object that is contained at the location
	 *
	 * @return the object contained at the location
	 */
	public Object getObj()
	{
		return obj;
	}

	/**
	 * Gets manhattan distance between current location and another location
	 * @param loc location to compute distance away from
	 * @return manhattan distance from loc
	 */
	int dist(Location loc)
	{
		return Math.abs(loc.x - x) + Math.abs(loc.y - y);
	}
}
