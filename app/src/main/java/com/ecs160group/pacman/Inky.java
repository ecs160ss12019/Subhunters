package com.ecs160group.pacman;

/**
 * Inky's target is same distance from Blinky to Pacman, opposite offset
 * to Blinky's offset from Blinky
 */
public class Inky extends Ghost
{
	/**
	 * Scatters to the bottom right
	 */
	private static final Location SCATTER_LOC = new Location(27, 30, Block.WALL);

}
