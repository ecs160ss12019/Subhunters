package com.ecs160group.pacman;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.Log;

import static android.icu.text.UnicodeSet.CASE;

public class BitmapDrawer {


    //needed to offset the bitmapdraw
    private final float pacGhostRadius;

    //private Location[] locations;
    public Bitmap[] bitmaps;

    public Context context;

    private final int xScaled;
    private final int yScaled;

    //ints to keep track of where in the bitmaps array we are storing each specific bitmap
    private final int pacInt;
    private final int blinkyInt;
    private final int inkyInt;
    private final int pinkyInt;
    private final int clydeInt;

    public BitmapDrawer(PacmanGame game) {

        pacGhostRadius = game.PacGhostRadius;

        this.xScaled = game.xScaled;
        this.yScaled = game.yScaled;

        this.context = game.activityContext;
        pacInt = 0;
        blinkyInt = 1;
        inkyInt = 2;
        pinkyInt = 3;
        clydeInt = 4;
        bitmaps = new Bitmap[5];
        initBitmaps();

    }

    /**
     * initializes the bitmaps by calling findAndResizeBitmap, which will store the bitmaps into bitmaps[]
     */
    private void initBitmaps() {
        findAndResizeBitmap(pacInt);
        findAndResizeBitmap(blinkyInt);
        findAndResizeBitmap(inkyInt);
        findAndResizeBitmap(pinkyInt);
        findAndResizeBitmap(clydeInt);
    }

    /**
     * finds and resizes the bitmap, takes in a string that notifies what object it is (ghost or pacman)
     * returns the bitmap
     * @param bitmapIndex - takes in the object indices that notifies what object it is (ghost(s) or pacman)
     */
    private void findAndResizeBitmap(int bitmapIndex) {
        Bitmap b;
        Bitmap sizedB;
        switch (bitmapIndex) {
            case 0://-700016
                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.pacman);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                bitmaps[0] = sizedB;
                break;
            case 1:
                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.blinky);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                bitmaps[1] = sizedB;
                break;
            case 2:
                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.inky);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                    (int) pacGhostRadius * 2, false);
                bitmaps[2] = sizedB;
                break;
            case 3:
                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.pinky);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                bitmaps[3] = sizedB;
                break;
            case 4:
                b = BitmapFactory.decodeResource(context.getResources(), R.drawable.clyde);
                sizedB = Bitmap.createScaledBitmap(b, (int) pacGhostRadius * 2,
                        (int) pacGhostRadius * 2, false);
                bitmaps[4] = sizedB;
                break;
            default:
                break;
        }
    }

    /**
     * draws pacman
     * @param mPacman - from PacmanGame
     */
    public void draw(@NonNull Pacman mPacman, Canvas canvas) {
        canvas.drawBitmap(bitmaps[0], (mPacman.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                (mPacman.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
    }

    /**
     * draws ghost (this one is temporary for testing, will delete later and use the specific ghost draws
     * @param ghost - from PacmanGame
     */
    private void draw (@NonNull Ghost ghost, Canvas canvas) {
        canvas.drawBitmap(bitmaps[1], (ghost.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                (ghost.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);

    }

    /**
     * draws blinky
     * @param blinky - from PacmanGame
     */
    private void draw(@NonNull Blinky blinky, Canvas canvas) {
        canvas.drawBitmap(bitmaps[0], (blinky.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                (blinky.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
    }

    /**
     * draws inky
     * @param inky - from PacmanGame
     */
    private void draw(@NonNull Inky inky, Canvas canvas ) {
        canvas.drawBitmap(bitmaps[0], (inky.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                (inky.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
    }

    /**
     * draws pinky
     * @param pinky - from PacmanGame
     */
    private void draw(@NonNull Pinky pinky, Canvas canvas) {
        canvas.drawBitmap(bitmaps[0], (pinky.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                (pinky.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
    }

    /**
     * draws clyde
     * @param clyde - from PacmanGame
     */
    private void draw(@NonNull Clyde clyde, Canvas canvas) {
        canvas.drawBitmap(bitmaps[0], (clyde.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                (clyde.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
    }

    /**
     * draws pacman and all the ghosts
     * called from draw() method in PacmanGame
     */
    public void draw(Pacman pacman, Ghost ghost, Blinky blinky, Inky inky, Pinky pinky, Clyde clyde, Canvas canvas) {

        draw(pacman, canvas);
        //draw(ghost, canvas);
        draw(blinky, canvas);
        //draw(inky, canvas);
        //draw(pinky, canvas);
        //draw(clyde, canvas);*/


    }
    

}


