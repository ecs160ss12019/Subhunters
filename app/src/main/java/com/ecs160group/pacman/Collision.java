package com.ecs160group.pacman;

// used to detect all kind of collision between Pacman and
// ghosts, fruit, pullets, and wall
public interface Collision {
    public void isInBounds(int mScreenX, int mScreenY);
    public boolean detectCollision(Location loc, int mScreenX, int mScreenY);
}
