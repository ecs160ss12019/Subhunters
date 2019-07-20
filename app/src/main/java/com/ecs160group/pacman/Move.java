package com.ecs160group.pacman;

import android.util.Log;

/*
    the Move class will update the movement of all the characters
    by changing their locations
    the functions of this class may be called after each draw or
    one iteration of the game
 */
public class Move{
    // function: Pacman_update
    // change the location of Pacman regarding to its current direction
    // and its velocity
    void Pacman_update(Location l)
    {
        // read the location to a temp location

        // use the velocity to update x and y coordinates

        // return the changed location to Pacman
    }

    // function: Ghost_update
    // basically the same thing as the Pacman one but need to
    // check which ghost type it is and execute the different update methods
    void Ghost_update(Location l)
    {
    }

}
