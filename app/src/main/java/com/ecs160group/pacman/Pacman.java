package com.ecs160group.pacman;

/*
    protagonist of Pacman game
    can eat pellets
    can eat fruits
    can eat ghosts for a period of time after power pellets are eaten
    must eat all pellets to complete level
 */

public class Pacman {
    //pacman coords//directions
    Location loc;
    private int direction;
    private int next_direction;

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
