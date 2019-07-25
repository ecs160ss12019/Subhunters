package com.ecs160group.pacman;

/**
 * Blinky follows Pacman around. Uses Pacman's current location
 * and moves toward that location
 */
public class Blinky extends Ghost
{
	private static final Location SCATTER = new Location(0, 27, Block.WALL);

	/**
	 * Gets Blinky's scatter Location
	 * @return the scatter target
	 */
	public Location getScatterLocation()
	{
		return SCATTER;
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
		return pacman.getLoc();
	}

	/**
	 * Controls Blinky's movement around the board
	 */
	public void move()
	{
		if (pacman.isSuper()) {
			scatter(SCATTER);
		} else {
			chase(getChaseLocation());
		}
	}
}
