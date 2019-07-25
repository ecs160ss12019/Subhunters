package com.ecs160group.pacman;

/**
 * Clyde goes to scatter location/region unless within a certain distance
 * of Pacman when pacman is not super. Within this distance Clyde acts like Blinky
 */
public class Clyde extends Blinky
{
	/**
	 * Static scatter location for Clyde
	 *  is in wall so that Clyde scatters and patrols an area in the maze
	 */
	private static final Location SCATTER_LOC = new Location(0, 30, Block.WALL);
	private static final int MAX_CHASE_DIST = 8; // maximum distance clyde can be away to chase Pacman

	/**
	 * Gets Clyde's scatter location
	 * @return Clyde's scatter location
	 */
	@Override
	public Location getScatterLocation() {
		return SCATTER_LOC;
	}

	/**
	 * Called when Clyde needs to move
	 * Clyde goes to scatter location unless he's within the maximum manhattan distance to
	 * chase pacman. Within that distance, Clyde behaves like Clyde
	 */
	public void move()
	{
		if (Location.dist(loc, pacman.getLoc()) < MAX_CHASE_DIST && !pacman.isSuper()) {
			// if within minimum distance and pacman isn't super, be like Blinky
			super.move();
		} else { // otherwise patrol scatter location
			scatter(getScatterLocation());
		}
	}
}
