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
	 * reset function to set score back to 0
	 */

	void reset() {
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
		score += 50;
	}

	/**
	 * Called when Pacman eats a ghost
	 */
	void ateGhost()
	{
		score += 200;
	}

	/**
	 * Called when Pacman eats a fruit
	 */
	void ateFruit(Fruit f)
	{
		switch(f) {
			case CHERRY:
				score += 100;
				break;
			case STRAWBERRY:
				score += 300;
				break;
			case ORANGE:
				score += 500;
				break;
			case APPLE:
				score += 700;
				break;
			case GALAGA:
				score += 2000;
				break;
			case KEY:
				score += 5000;
				break;
			default:
				break;
		}
	}
}
