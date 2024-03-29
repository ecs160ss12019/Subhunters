package com.ecs160group.pacman;

/**
 * Blinky follows Pacman around. Uses Pacman's current location
 * and moves toward that location
 */
public class Blinky extends Ghost
{
	/**
	 * Static scatter location for Blinky
	 * is in wall so that Blinky scatters and patrols an area in the maze
	 * Scatters to the top right of maze
	 */
	private static final Location SCATTER_LOC = new Location(27, 0, Block.WALL);

	public Blinky(int screenX, Location spawnLoc, Maze maze, Pacman p)
	{
		super(screenX, spawnLoc, maze, p);
	}

	/**
	 * Gets Blinky's scatter Location
	 *
	 * @return the scatter target
	 */
	public Location getScatterLocation()
	{
		return SCATTER_LOC;
	}

	/**
	 * Gets the chase location for Blinky to chase to
	 * Blinky's chase location is pacman's current location
	 *
	 * @return pacman's location, Blinky's location to move
	 * towards when Pacman isn't super
	 */
	public Location getChaseLocation()
	{
		return pacman.getGridLoc();
	}

	/**
	 * Controls Blinky's movement around the board
	 */
	public void move()
	{
		if (pacman.isSuper()) {
			scatter(SCATTER_LOC);
		} else {
			chase(getChaseLocation());
		}
	}
}
