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
	 *
	 * @param l Location to be copied
	 */
	Location(Location l)
	{
		this.x = l.x;
		this.y = l.y;
		this.block = l.block;
	}


	/**
	 * Constructor that takes in an object and a coordinate
	 *
	 * @param x     x axis coordinate
	 * @param y     y axis coordinate
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

	public void setNewLoc(int newX, int newY)
	{
		x = newX;
		y = newY;
	}

	public void setObj(Block b)
	{
		block = b;
	}

	public void updateLoc(int newX, int newY, Block newBlock)
	{
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

	/**
	 * Gets the adjacent location to the current one based on the direction from the
	 * current location assumes an empty space
	 *
	 * @param direction direction to give the adjacent location to
	 * @return the adjacent location based on the location, assumes an empty space
	 */
	public Location getAdjacentLocation(char direction)
	{
		switch (direction) {
			case 'l': // location to left of current
				return new Location(x - 1, y, Block.EMPTY);
			case 'r': // location to the right of current
				return new Location(x + 1, y, Block.EMPTY);
			case 'u': // location above current
				return new Location(x, y - 1, Block.EMPTY);
			case 'd': // location below current, only option left
			default:
				return new Location(x, y + 1, Block.EMPTY);
		}
	}

	/**
	 * Checks if the location is a wall of not
	 *
	 * @return if the location is a wall
	 */
	public boolean isWall()
	{
		return block == Block.HORIZONTAL_WALL || block == Block.VERTICAL_WALL
				|| block == Block.BOT_LEFT_TOP_RIGHT_WALL
				|| block == Block.BOT_RIGHT_TOP_LEFT_WALL || isGhostGate();
	}

	public boolean isGhostGate()
	{
		return block == Block.GHOST_GATE;
	}

	/**
	 * Checks if the location contains a pellet
	 *
	 * @return if the location contains a pellet
	 */
	public boolean isPellet()
	{
		return block == Block.PELLET || block == Block.POWER_PELLET;
	}

	/**
	 * Checks if the location is empty
	 *
	 * @return if the location is empty
	 */
	public boolean isEmpty()
	{
		return block == Block.EMPTY;
	}

	/**
	 * Checks if the location contains Pacman
	 *
	 * @return if the location contains Pacman
	 */
	public boolean isPacman()
	{
		return block == Block.PACMAN;
	}

	/**
	 * Checks if the location contains a Ghost
	 *
	 * @return if the location contains a Ghost
	 */
	public boolean isGhost()
	{
		return block == Block.BLINKY || block == Block.INKY
				|| block == Block.PINKY || block == Block.CLYDE || block == Block.GHOST;
	}

	/**
	 * Checks if the location is a fruit
	 *
	 * @return if the location is a fruit
	 */
	public boolean isFruit()
	{
		return block == Block.FRUIT;
	}

	/**
	 * Checks if the location is a warp space
	 *
	 * @return if the location is a warp space
	 */
	public boolean isWarp()
	{
		return block == Block.WARP_SPACE;
	}

	/**
	 * Checks if the location is a spawn location
	 *
	 * @return if the location is a spawn location
	 */
	public boolean isSpawn()
	{
		return block == Block.FRUIT_SPAWN || block == Block.GHOST_SPAWN || block == Block.PAC_SPAWN;
	}

	/**
	 * Gets the location in front of the direction facing
	 *
	 * @param dirFacing Direction the object is facing
	 * @return location immediately in front of current location
	 */
	public Location getAhead(char dirFacing)
	{
		return getAdjacentLocation(dirFacing);
	}

	/**
	 * Gets the location to the left of the direction facing
	 *
	 * @param dirFacing Direction the object is facing
	 * @return location immediately to the left of current location
	 */
	public Location getLeft(char dirFacing)
	{
		switch (dirFacing) {
			case 'l':
				return getAdjacentLocation('d');
			case 'r':
				return getAdjacentLocation('u');
			case 'u':
				return getAdjacentLocation('l');
			case 'd':
			default:
				return getAdjacentLocation('r');
		}
	}

	/**
	 * Gets the location to the right of the direction facing
	 *
	 * @param dirFacing Direction the object is facing
	 * @return location immediately to the right of current location
	 */
	public Location getRight(char dirFacing)
	{
		switch (dirFacing) {
			case 'l':
				return getAdjacentLocation('u');
			case 'r':
				return getAdjacentLocation('d');
			case 'u':
				return getAdjacentLocation('r');
			case 'd':
			default:
				return getAdjacentLocation('l');
		}
	}

	/**
	 * Checks if the location is the current right direction
	 *
	 * @param dirFacing direction the object is facing
	 * @return if the location is to the right of the object
	 */
	public boolean isRight(char dirFacing, Location next)
	{
		Location right = getRight(dirFacing);
		return right.getX() == next.x && right.getY() == next.y;
	}

	/**
	 * Checks if the location is the current left direction
	 *
	 * @param dirFacing direction the object is facing
	 * @return if the location is to the left of the object
	 */
	public boolean isLeft(char dirFacing, Location next)
	{
		Location left = getLeft(dirFacing);
		return left.getX() == next.x && left.getY() == next.y;
	}


	/**
	 * Checks if a location is in bounds
	 *
	 * @param l location to check
	 * @return if location is in bounds
	 */
	public static boolean isValid(Location l)
	{
		return l != null || Maze.isInBounds(l);
	}

	/**
	 * To String function for a location
	 *
	 * @return string representation of a Location
	 */
	@Override
	public String toString()
	{
		return "x=" + x + ",y=" + y + ",obj=" + block;
	}
}
