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
    private int direction;
    private int next_direction;
    // use an integer to temporarily replace the draw of Pacman
    // this will be modified under the draw function
    int pacImage = 1;

    //initialize the position of Pacman at the beginning of the game
    void initialize(int sX, int sY){
        /*Random random = new Random();
        int xVal = random.nextInt((sX - sX/3) + 1) + sX/3;
        int yVal = random.nextInt((sY - sY/3) + 1) + sY/3;*/
        loc = new Location(sX, sY, pacImage);
    }

    //function to check if pacman is within bounds (same function as in ghost class)
    void checkBounds() {

    }

    //draws pacman on screen
    //will use variable direction later on for image purposes
    //circle for now
    public void draw(){

    }

    //will read user input from our dPad(left, right, up, or down) to update pacmans direction
    //will take in value from dpad.java
    void checkDirection(){


    }
}
