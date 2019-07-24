package com.ecs160group.pacman;

import android.util.Log;

/*
    the Move class will update the movement of all the characters
    by changing their locations
    the functions of this class may be called after each draw or
    one iteration of the game
 */
public class Move{

	private Location loc;
	private Pacman pacman;
	private Ghost ghost;

	/**
	 * Constructor for Pacman's movement
	 * @param p pacman's object
	 * @param direction direction of movement
	 * Depending on direction given and traversability, Pacman will attempt to move.
	 * Helper function checkCollision, checks next cell and deals with it. If pellet, ghost,fruit, ect..
	 */
	public Move(Pacman p, char direction) // Change direction to char? "Easier to Read" udlr
    {
		pacman = p;
		// TODO: extend this to introduce movement based on the direction given

		int pacX = pacman.loc.getX();
		int pacY = pacman.loc.getY();

		// Check neighboring cell for collisions!!
		if(direction == 'u' && checkCollisionPacman(pacX, pacY + 1)){ // GO to coordinate (x,y+1)
			pacman.loc.setNewLoc(pacX, pacY + 1);
			Log.d("Pacman-Moved: ", "Move: " + direction + "Location: " + pacX + "," + pacY);

		}
		if(direction == 'd' && checkCollisionPacman(pacX, pacY - 1)){
			pacman.loc.setNewLoc(pacX, pacY - 1);
			Log.d("Pacman-Moved: ", "Move: " + direction + "Location: " + pacX + "," + pacY);

		}
		if(direction == 'l' && checkCollisionPacman(pacX - 1, pacY)){
			pacman.loc.setNewLoc(pacX -1, pacY);
			Log.d("Pacman-Moved: ", "Move: " + direction + "Location: " + pacX + "," + pacY);

		}
		if(direction == 'r' && checkCollisionPacman(pacX + 1, pacY)){
			pacman.loc.setNewLoc(pacX + 1, pacY);
			Log.d("Pacman-Moved: ", "Move: " + direction + "Location: " + pacX + "," + pacY);

		}

	}

	/**
	 * Constructor for Ghost's movement
	 * @param g singular ghost object
	 * @param direction direction of movement
	 */
	public Move(Ghost g, char direction)
    {

    	// Because this code repeats, we can possibly do just within the previous function MovePacman?
		ghost = g;
	    // TODO: extend this to introduce movement based on the direction given
		int ghostX = g.loc.getX();
		int ghostY = g.loc.getY();


		// Check neighboring cell for collisions!!
		if(direction == 'u' && checkCollisionGhost(ghostX, ghostY + 1)){ // GO to coordinate (x,y+1)
			pacman.loc.setNewLoc(ghostX, ghostY + 1);
			Log.d("Pacman-Moved: ", "Move: " + direction + "Location: " + ghostX + "," + ghostY);

		}
		if(direction == 'd' && checkCollisionGhost(ghostX, ghostY - 1)){
			pacman.loc.setNewLoc(ghostX, ghostY - 1);
			Log.d("Pacman-Moved: ", "Move: " + direction + "Location: " + ghostX + "," + ghostY);

		}
		if(direction == 'l' && checkCollisionGhost(ghostX - 1, ghostY)){
			pacman.loc.setNewLoc(ghostX - 1, ghostY);
			Log.d("Pacman-Moved: ", "Move: " + direction + "Location: " + ghostX + "," + ghostY);

		}
		if(direction == 'r' && checkCollisionGhost(ghostX + 1, ghostY)){
			pacman.loc.setNewLoc(ghostX + 1, ghostY);
			Log.d("Pacman-Moved: ", "Move: " + direction + "Location: " + ghostX + "," + ghostY);

		}
	}

	/**
	 * Check next cell, do what's needed here?
	 * @param x X-coord to move
	 * @param y Y-coord to move
	 */
	public boolean checkCollisionPacman(int x, int y){
		boolean b = false; // Unable to traverse by default?
		/*
		If(loc[x][y].block == EMPTY) {
			return true;
		}
	*/
		// maze[x][y] != walkable space... to return True.
		//if()
		return b;
	}
	public boolean checkCollisionGhost(int x, int y){
		boolean b = false; // Unable to traverse by default?
		/*
		If(loc[x][y].block == EMPTY) {
			return true;
		}
	*/
		// maze[x][y] != walkable space... to return True.
		//if()
		return b;
	}
	
    // function: Pacman_update
    // change the location of Pacman regarding to its current direction
    // and its velocity
    void Pacman_update(Pacman pac)
    {
        // read the location to a temp location
        Location loc;
        loc = new Location(pac.loc.getX(), pac.loc.getY(), pac.loc.getObj());
        // use the velocity to update x and y coordinates
        // read the velocity floats of x and y
        // add value to each for next step in location

        // return the changed location to Pacman
    }

    // function: Ghost_update
    // basically the same thing as the Pacman one but need to
    // check which ghost type it is and execute the different update methods
    void Ghost_update(Ghost ghost)
    {

    }

}
