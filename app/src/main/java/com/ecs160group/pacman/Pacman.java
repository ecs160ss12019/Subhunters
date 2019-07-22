package com.ecs160group.pacman;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

/*
    protagonist of Pacman game
    can eat pellets
    can eat fruits
    can eat ghosts for a period of time after power pellets are eaten
    must eat all pellets to complete level
 */

import java.util.Random;

public class Pacman {
    //pacman coords//directions
    Location loc;
    private char direction;
    private char next_direction;
    // use an integer to temporarily replace the draw of Pacman
    // this will be modified under the draw function
    int pacImage = 1;

    //RectF has four values (left, top, right, bottom)

    float mXVelocity;
    float mYVelocity;
    float mPacWidth;
    float mPacHeight;
    final Paint paint = new Paint();


    Pacman (int screenX, int locX, int locY) {

        paint.setColor(Color.argb(255,255,255,0));
        //pacman width/height 1% of screen (change later if needed)
        mPacWidth = (float)screenX/100;
        mPacHeight = (float)screenX/100;

        loc = new Location(locX, locY, Block.PACMAN );
        direction = 'l';
        next_direction = 'l';

    }

    /**
     * updates the ball position
     * called each frame/loop from PacmanGame update() method
     * moves ball based on x/y velocities and fps
     */
    void update(long fps) {

    }

    void reverseXVel(){
        mXVelocity = -mXVelocity;
    }

    void reverseYVel(){
        mYVelocity = -mYVelocity;
    }

    /**
     * Initializes four points of mRect(defines pacman)
     * Initializes x and y velocities (can change later)
     */
    void reset(int x, int y) {

        mXVelocity = (float)(y / 3);
        mYVelocity = (float)-(y / 3);


    }
    //function to check if pacman is within bounds (same function as in ghost class)
    //function to

	/**
	 * Checks if pacman is within bounds of maze
	 */
    void checkBounds() {

    }

    //TODO: will use variable direction later on for image purposes

	//circle for now, may need to be modified
	/**
	 * Draws Pacman on screen
	 */
    public void draw(){

    }


	/**
     * called by onTouchEvent in PacmanGame once user moves the stick
	 * takes in the current direction of the joystick to update Pacman's next direction
	 * @param joyDirection - read in from the joystick's direction variable
     *
	 */
    void updateNextDirection(char joyDirection){
        if (joyDirection == 'l') {
            direction = 'l';
        }
        else if (joyDirection == 'r') {
            direction = 'r';
        }

        else if (joyDirection == 'u') {
            direction = 'u';
        }

        else if (joyDirection == 'd') {
            direction = 'd';
        }
    }

    /**
     * checking if the next move of Pacman is available
     * @param x x coordinate of maze
     * @param y y coordinate of maze
     * @return true if doesn't collide with anything
     */
    public boolean checkCollisionPacman(int x, int y){
        // should be false by default
        // for now we set it true for testing
        boolean b; // Unable to traverse by default?
        b = true;
		    /*
		    If(loc[x][y].block == EMPTY) {
			    return true;
		    }
	        */
        // maze[x][y] != walkable space... to return True.
        //if()
        return b;
    }

    /**
     * Constructor for Pacman's movement
     * Depending on direction given and traversability, Pacman will attempt to move.
     * Helper function checkCollision, checks next cell and deals with it. If pellet, ghost,fruit, ect..
     */
    public void Move() // Change direction to char? "Easier to Read" udlr
    {
        // TODO: extend this to introduce movement based on the direction given

        int pacX = loc.getX();
        int pacY = loc.getY();

        // Check neighboring cell for collisions!!
        if (direction == 'u' && checkCollisionPacman(pacX, pacY + 1)) { // GO to coordinate (x,y+1)
            loc.setNewLoc(pacX, pacY - 1);
            Log.d("Pacman-Moved: ", "Move: " + direction + "Location: " + pacX + "," + pacY);

        }
        if (direction == 'd' && checkCollisionPacman(pacX, pacY - 1)) {
            loc.setNewLoc(pacX, pacY + 1);
            Log.d("Pacman-Moved: ", "Move: " + direction + "Location: " + pacX + "," + pacY);

        }
        if (direction == 'l' && checkCollisionPacman(pacX - 1, pacY)) {
            loc.setNewLoc(pacX - 1, pacY);
            Log.d("Pacman-Moved: ", "Move: " + direction + "Location: " + pacX + "," + pacY);

        }
        if (direction == 'r' && checkCollisionPacman(pacX + 1, pacY)) {
            loc.setNewLoc(pacX + 1, pacY);
            Log.d("Pacman-Moved: ", "Move: " + direction + "Location: " + pacX + "," + pacY);

        }
    }


	/**
	 * Stub of function to determine if Pacman has won
	 * Pacman wins when all the pellets and super pellets have been eaten
	 * @return if Pacman has won
	 */
	public boolean hasWon()
	{
		return false;
	}
}
