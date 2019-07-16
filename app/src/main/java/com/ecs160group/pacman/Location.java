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
	Object obj;

	// TODO: potentially add more functions and functionality such as if there is more than one object at  a location

	/**
	 * Constructor that takes in an object and a coordinate
	 * @param x x axis coordinate
	 * @param y y axis coordinate
	 * @param o object at the xy-coordinate
	 */
	Location (int x, int y, Object o)
	{
		this.x = x;
		this.y = y;
		obj = o;
	}

	/**
	 * Gets the x coordinate of the location
	 * @return x coordinate of location
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y coordinate of the location
	 * @return y coordinate of location
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * Gets the object in the location
	 * @return object located at the point
	 */
	public Object getObj()
	{
		return obj;
	}

	/**
	 * Gets the manhattan distance between the location passed in and the
	 * current location
	 * @param loc location to find distance away from
	 * @return manhattan distance from loc
	 */
	public int dist(Location loc) {
		return Math.abs(loc.x - x) + Math.abs(loc.y - y);
	}
}
