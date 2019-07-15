package com.example.pacman;

import android.graphics.Color;
import android.util.Log;

class Maze {


    private PacmanActivity pacmanActivity;

    public Maze(PacmanActivity pacmanActivity) {
        this.pacmanActivity = pacmanActivity;
    }

    //maze set up
    void draw() {
        pacmanActivity.gameView.setImageBitmap(pacmanActivity.blankBitmap);
        //print the whole graph
        pacmanActivity.canvas.drawColor(Color.argb(255, 255, 255, 255));

        //print the wall

        //print the Pacman's position after the last order

        //using get() function in location and add the fixed value of order direction to the coordinate

        //print the ghosts

        //print items (fruits and more)

        //debugging
        Log.d("Debugging", "In draw");
    }
}
