package com.ecs160group.pacman;

public class Score
{
	int score;

	/**
	 * Default ctor for the score class
	 */
	Score()
	{
		score = 0;
	}

	/**
	 *
	 * @return
	 */
	int getScore()
	{
		return score;
	}

	/**
	 * Called when Pacman eats a pellet
	 */
	void atePellet()
	{
		score += 10;
	}

	/**
	 *  Called when Pacman eats a power pellet
	 */
	void atePowerPellet()
	{
		score += 100;
	}

	/**
	 * Called when Pacman eats a ghost
	 */
	void ateGhost()
	{
		score += 500;
	}

	/**
	 * Called when Pacman eats a fruit
	 */
	// TODO: extend this for multiple fruit types
	void ateFruit()
	{
		score += 1000;
	}
}
