package com.ecs160group.pacman;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
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

        loc = new Location(locX, locY, pacImage);

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
