package com.ecs160group.pacman;

public class Score
{
	int score;

	/**
	 * Default ctor for the score class
	 * Used for a new game
	 */
	Score()
	{
		score = 0;
	}

	/**
	 * Ctor with score parameter for score class
	 * Used for resetting score when Pacman respawns but no game over
	 * or new level reached
	 * @param currentScore current score in the game
	 */
	Score(int currentScore) {
		score = currentScore;
	}

	/**
	 * Called to get the current score
	 * @return the current score
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
	// TODO: extend this for multiple fruit types, use parameters
	void ateFruit()
	{
		score += 1000;
	}
}
