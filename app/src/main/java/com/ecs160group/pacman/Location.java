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
	private Block block;

	/**
	 * Default ctor
	 */
	Location()
	{
		x = -1;
		y = -1;
		block = Block.EMPTY;
	}

	/**
	 * Copy constructor
	 * @param l Location to be copied
	 */
	Location (Location l) {
		this.x = l.x;
		this.y = l.y;
		this.block = l.block;
	}


	/**
	 * Constructor that takes in an object and a coordinate
	 *
	 * @param x x axis coordinate
	 * @param y y axis coordinate
	 * @param block object at the xy-coordinate
	 */
	Location(int x, int y, Block block)
	{
		this.x = x;
		this.y = y;
		this.block = block;
	}

	/**
	 * Gets the x coordinate of the location
	 *
	 * @return x coordinate of location
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * Gets the y coordinate of the location
	 *
	 * @return y coordinate of location
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * Gets the object in the location
	 *
	 * @return object located at the point
	 */
	public Block getObj()
	{
		return block;
	}

	public void setNewLoc(int newX, int newY){
		x = newX;
		y = newY;
	}
	public void updateLoc(int newX, int newY, Block newBlock){
		x = newX;
		y = newY;
		block = newBlock;
	}


	/**
	 * Gets the manhattan distance between the location passed in and the
	 * current location
	 *
	 * @param loc1 first location to find distance from
	 * @param loc2 second location to find distance from
	 * @return manhattan distance between loc1 and loc2
	 */
	public static int dist(Location loc1, Location loc2)
	{
		return Math.abs(loc1.x - loc2.x) + Math.abs(loc1.y - loc2.y);
	}
}
