package com.ecs160group.pacman;

/**
 * Enum for typing of things in locations within the maze
 * Used across multiple classes
 */
public enum Block
{
	PACMAN, GHOST, FRUIT, WALL, PELLET, POWER_PELLET, WARP_SPACE, FRUIT_SPAWN, PAC_SPAWN,
		GHOST_SPAWN, GHOST_WAIT, GHOST_GATE, EMPTY, BLINKY, INKY, PINKY, CLYDE, VERTICAL_WALL, HORIZONTAL_WALL,
		BOT_RIGHT_TOP_LEFT_WALL, BOT_LEFT_TOP_RIGHT_WALL
}

// TODO: give a more appropriate name for the enum