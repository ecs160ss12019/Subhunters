package com.ecs160group.pacman;

/**
 * Enum for typing of things in locations within the maze
 * Used across multiple classes
 */
public enum Block
{
	PACMAN, GHOST, FRUIT, WALL, PELLET, POWER_PELLET, FRUIT_SPAWN, PAC_SPAWN,
		GHOST_SPAWN, GHOST_WAIT, EMPTY
}

// TODO: give a more appropriate name for the enum