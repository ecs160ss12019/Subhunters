package com.ecs160group.pacman;

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

	/**
	 * Initialize the position of Pacman at the beginning of the game
	 * @param sX
	 * @param sY
	 */
	void initialize(int sX, int sY){
        loc = new Location(sX, sY, this);
        direction = 'l';
        next_direction = 'l';
    }

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
	 * Reads user input from dpad listener to update Pacman's next direction
	 * @param xPercent
	 * @param yPercent
	 */
    void updateNextDirection(float xPercent, float yPercent){

    }
}
