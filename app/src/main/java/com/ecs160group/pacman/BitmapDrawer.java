package com.ecs160group.pacman;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import static android.icu.text.UnicodeSet.CASE;

public class BitmapDrawer {


    //needed to offset the bitmapdraw
    private final float pacGhostRadius;

    public Context context;

    private final int xScaled;
    private final int yScaled;

    private int pacFrameCounter;
    private int ghostFrameCounter;

    private Bitmap curBitmap;

    private BitmapCreator bitmaps;

    public boolean updatePac;



    public BitmapDrawer(PacmanGame game) {
        pacGhostRadius = game.PacGhostRadius;

        this.xScaled = game.xScaled;
        this.yScaled = game.yScaled;

        pacFrameCounter = 0;
        context = game.activityContext;

        bitmaps = new BitmapCreator(game);

        updatePac = true;

    }

    public void draw(Pacman pacman, Ghost ghost, Blinky blinky, Inky inky, Pinky pinky, Clyde clyde, Canvas canvas) {

        if (pacman.direction != ' ') {
            draw(pacman, canvas);
        }
        else {
            drawStillPacman(pacman, canvas);
        }
        //draw(ghost, canvas);
        if (pacman.isSuper()) {
            drawScared(blinky, canvas);
            drawScared(inky, canvas);
            drawScared(pinky, canvas);
            drawScared(clyde, canvas);
        }
        else {
            draw(blinky, canvas);
            draw(inky, canvas);
            draw(pinky, canvas);
            draw(clyde, canvas);
        }
       counter();
    }



    /**
     * increments and keeps track of pacFrameCounter and ghostFrameCounter
     */
    private void counter() {
        if (updatePac) {
            pacFrameCounter++;
        }
            if (pacFrameCounter > 30) {
                pacFrameCounter = 0;
            }

        ghostFrameCounter++;
        if (ghostFrameCounter > 20) {
            ghostFrameCounter = 0;
        }

    }

    /**
     * Draw Scared Ghost depending on Pacman's State
     */
    private void drawScared(@NonNull Ghost ghost, Canvas canvas) {
        if (ghostFrameCounter <= 10) {
            canvas.drawBitmap(bitmaps.scaredGhostMaps[0], (ghost.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (ghost.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        } else if (ghostFrameCounter <= 20) {
            canvas.drawBitmap(bitmaps.scaredGhostMaps[1], (ghost.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (ghost.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        }
    }

    /**
     * draws pacman
     * @param mPacman - from PacmanGame
     */
    public void draw(@NonNull Pacman mPacman, Canvas canvas) {
        switch (mPacman.direction) {
            case 'l':
                leftDraw(canvas, mPacman);
                break;
            case 'r':
                rightDraw(canvas, mPacman);
                break;
            case 'u':
                upDraw(canvas, mPacman);
                break;
            case 'd':
                downDraw(canvas, mPacman);
                break;
            default:
                break;
        }
    }

    /**
     * draws blinky
     * @param blinky - from PacmanGame
     */
    private void draw(@NonNull Blinky blinky, Canvas canvas) {
        switch (blinky.getDirection()) {
            case 'l':
                leftDraw(canvas, blinky);
                break;
            case 'r':
                rightDraw(canvas, blinky);
                break;
            case 'u':
                upDraw(canvas, blinky);
                break;
            case 'd':
                downDraw(canvas, blinky);
                break;
            default:
                break;
        }
    }

    /**
     *
     * draws inky
     * @param inky - from PacmanGame
     *
     */
    private void draw(@NonNull Inky inky, Canvas canvas ) {
        switch (inky.getDirection()) {
            case 'l':
                leftDraw(canvas, inky);
                break;
            case 'r':
                rightDraw(canvas, inky);
                break;
            case 'u':
                upDraw(canvas, inky);
                break;
            case 'd':
                downDraw(canvas, inky);
                break;
            default:
                break;
        }
    }

    /**
     * draws pinky
     * @param pinky - from PacmanGame
     */
    private void draw(@NonNull Pinky pinky, Canvas canvas) {
        switch (pinky.getDirection()) {
            case 'l':
                leftDraw(canvas, pinky);
                break;
            case 'r':
                rightDraw(canvas, pinky);
                break;
            case 'u':
                upDraw(canvas, pinky);
                break;
            case 'd':
                downDraw(canvas, pinky);
                break;
            default:
                break;
        }
    }


    /**
     * draws clyde
     * @param clyde - from PacmanGame
     */
    private void draw(@NonNull Clyde clyde, Canvas canvas) {
        switch (clyde.getDirection()) {
            case 'l':
                leftDraw(canvas, clyde);
                break;
            case 'r':
                rightDraw(canvas, clyde);
                break;
            case 'u':
                upDraw(canvas, clyde);
                break;
            case 'd':
                downDraw(canvas, clyde);
                break;
            default:
                break;
        }
    }


    /**
     * draws the left direction animation
     * decides on which image to draw depending on the frame passed down
     */
    private void leftDraw(Canvas canvas, Pacman mPacman) {
        if (pacFrameCounter <= 10) {
            canvas.drawBitmap(bitmaps.pacLeftMaps[0], (mPacman.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (mPacman.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        } else if (pacFrameCounter <= 20) {
            canvas.drawBitmap(bitmaps.pacLeftMaps[1], (mPacman.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (mPacman.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        } else if (pacFrameCounter <= 30) {
            canvas.drawBitmap(bitmaps.pacLeftMaps[2], (mPacman.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (mPacman.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        }
    }

    /**
     * draws the right direction animation
     * decides on which image to draw depending on the frame passed down
     */
    private void rightDraw(Canvas canvas, Pacman mPacman) {
        if (pacFrameCounter <= 10) {
            canvas.drawBitmap(bitmaps.pacRightMaps[0], (mPacman.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (mPacman.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        } else if (pacFrameCounter <= 20) {
            canvas.drawBitmap(bitmaps.pacRightMaps[1], (mPacman.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (mPacman.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        } else if (pacFrameCounter <= 30) {
            canvas.drawBitmap(bitmaps.pacRightMaps[2], (mPacman.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (mPacman.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        }
    }

    /**
     * draws the up direction animation
     * decides on which image to draw depending on the frame passed down
     */
    private void upDraw(Canvas canvas, Pacman mPacman) {
        if (pacFrameCounter <= 10) {
            canvas.drawBitmap(bitmaps.pacUpMaps[0], (mPacman.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (mPacman.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        } else if (pacFrameCounter <= 20) {
            canvas.drawBitmap(bitmaps.pacUpMaps[1], (mPacman.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (mPacman.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        } else if (pacFrameCounter <= 30) {
            canvas.drawBitmap(bitmaps.pacUpMaps[2], (mPacman.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (mPacman.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        }
    }

    /**
     * draws the down direction animation
     * decides on which image to draw depending on the frame passed down
     */
    private void downDraw(Canvas canvas, Pacman mPacman) {
        if (pacFrameCounter <= 10) {
            canvas.drawBitmap(bitmaps.pacDownMaps[0], (mPacman.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (mPacman.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        } else if (pacFrameCounter <= 20) {
            canvas.drawBitmap(bitmaps.pacDownMaps[1], (mPacman.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (mPacman.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        } else if (pacFrameCounter <= 30) {
            canvas.drawBitmap(bitmaps.pacDownMaps[2], (mPacman.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (mPacman.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        }
    }

    /**
     * draws pacman before user has input direction
     */
    private void drawStillPacman( Pacman mPacman, Canvas canvas){
        canvas.drawBitmap(bitmaps.pacLeftMaps[0], (mPacman.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                (mPacman.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
    }


    /**
     * draws the left direction animation
     * decides on which image to draw depending on the frame passed down
     */
    private void leftDraw(Canvas canvas, Blinky blinky) {
        if (ghostFrameCounter <= 10) {
            canvas.drawBitmap(bitmaps.blinkyLeftMaps[0], (blinky.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (blinky.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        } else if (ghostFrameCounter <= 20) {
            canvas.drawBitmap(bitmaps.blinkyLeftMaps[1], (blinky.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (blinky.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        }
    }

    /**
     * draws the right direction animation
     * decides on which image to draw depending on the frame passed down
     */
    private void rightDraw(Canvas canvas, Blinky blinky) {
        if (ghostFrameCounter <= 10) {
            canvas.drawBitmap(bitmaps.blinkyRightMaps[0], (blinky.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (blinky.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        } else if (ghostFrameCounter <= 20) {
            canvas.drawBitmap(bitmaps.blinkyRightMaps[1], (blinky.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (blinky.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        }
    }

    /**
     * draws the up direction animation
     * decides on which image to draw depending on the frame passed down
     */
    private void upDraw(Canvas canvas, Blinky blinky){
        if (ghostFrameCounter <= 10) {
            canvas.drawBitmap(bitmaps.blinkyUpMaps[0], (blinky.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (blinky.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        } else if (ghostFrameCounter <= 20) {
            canvas.drawBitmap(bitmaps.blinkyUpMaps[1], (blinky.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (blinky.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        }
    }

    /**
     * draws the down direction animation
     * decides on which image to draw depending on the frame passed down
     */
    private void downDraw(Canvas canvas, Blinky blinky) {
        if (ghostFrameCounter <= 10) {
            canvas.drawBitmap(bitmaps.blinkyDownMaps[0], (blinky.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (blinky.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        } else if (ghostFrameCounter <= 20) {
            canvas.drawBitmap(bitmaps.blinkyDownMaps[1], (blinky.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (blinky.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        }
    }


    /**
     * draws the left direction animation
     * decides on which image to draw depending on the frame passed down
     */
    private void leftDraw(Canvas canvas, Inky inky) {
        if (ghostFrameCounter <= 10) {
            canvas.drawBitmap(bitmaps.inkyLeftMaps[0], (inky.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (inky.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        } else if (ghostFrameCounter <= 20) {
            canvas.drawBitmap(bitmaps.inkyLeftMaps[1], (inky.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (inky.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        }
    }

    /**
     * draws the right direction animation
     * decides on which image to draw depending on the frame passed down
     */
    private void rightDraw(Canvas canvas, Inky inky) {
        if (ghostFrameCounter <= 10) {
            canvas.drawBitmap(bitmaps.inkyRightMaps[0], (inky.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (inky.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        } else if (ghostFrameCounter <= 20) {
            canvas.drawBitmap(bitmaps.inkyRightMaps[1], (inky.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (inky.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        }
    }

    /**
     * draws the up direction animation
     * decides on which image to draw depending on the frame passed down
     */
    private void upDraw(Canvas canvas, Inky inky){
        if (ghostFrameCounter <= 10) {
            canvas.drawBitmap(bitmaps.inkyUpMaps[0], (inky.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (inky.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        } else if (ghostFrameCounter <= 20) {
            canvas.drawBitmap(bitmaps.inkyUpMaps[1], (inky.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (inky.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        }
    }

    /**
     * draws the down direction animation
     * decides on which image to draw depending on the frame passed down
     */
    private void downDraw(Canvas canvas, Inky inky) {
        if (ghostFrameCounter <= 10) {
            canvas.drawBitmap(bitmaps.inkyDownMaps[0], (inky.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (inky.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        } else if (ghostFrameCounter <= 20) {
            canvas.drawBitmap(bitmaps.inkyDownMaps[1], (inky.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (inky.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        }
    }


    /**
     * draws the left direction animation
     * decides on which image to draw depending on the frame passed down
     */
    private void leftDraw(Canvas canvas, Pinky pinky) {
        if (ghostFrameCounter <= 10) {
            canvas.drawBitmap(bitmaps.pinkyLeftMaps[0], (pinky.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (pinky.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        } else if (ghostFrameCounter <= 20) {
            canvas.drawBitmap(bitmaps.pinkyLeftMaps[1], (pinky.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (pinky.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        }
    }

    /**
     * draws the right direction animation
     * decides on which image to draw depending on the frame passed down
     */
    private void rightDraw(Canvas canvas, Pinky pinky) {
        if (ghostFrameCounter <= 10) {
            canvas.drawBitmap(bitmaps.pinkyRightMaps[0], (pinky.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (pinky.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        } else if (ghostFrameCounter <= 20) {
            canvas.drawBitmap(bitmaps.pinkyRightMaps[1], (pinky.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (pinky.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        }
    }

    /**
     * draws the up direction animation
     * decides on which image to draw depending on the frame passed down
     */
    private void upDraw(Canvas canvas, Pinky pinky){
        if (ghostFrameCounter <= 10) {
            canvas.drawBitmap(bitmaps.pinkyUpMaps[0], (pinky.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (pinky.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        } else if (ghostFrameCounter <= 20) {
            canvas.drawBitmap(bitmaps.pinkyUpMaps[1], (pinky.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (pinky.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        }
    }

    /**
     * draws the down direction animation
     * decides on which image to draw depending on the frame passed down
     */
    private void downDraw(Canvas canvas, Pinky pinky) {
        if (ghostFrameCounter <= 10) {
            canvas.drawBitmap(bitmaps.pinkyDownMaps[0], (pinky.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (pinky.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        } else if (ghostFrameCounter <= 20) {
            canvas.drawBitmap(bitmaps.pinkyDownMaps[1], (pinky.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (pinky.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        }
    }


    /**
     * draws the left direction animation
     * decides on which image to draw depending on the frame passed down
     */
    private void leftDraw(Canvas canvas, Clyde clyde) {
        if (ghostFrameCounter <= 10) {
            canvas.drawBitmap(bitmaps.clydeLeftMaps[0], (clyde.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (clyde.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        } else if (ghostFrameCounter <= 20) {
            canvas.drawBitmap(bitmaps.clydeLeftMaps[1], (clyde.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (clyde.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        }
    }

    /**
     * draws the right direction animation
     * decides on which image to draw depending on the frame passed down
     */
    private void rightDraw(Canvas canvas, Clyde clyde) {
        if (ghostFrameCounter <= 10) {
            canvas.drawBitmap(bitmaps.clydeRightMaps[0], (clyde.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (clyde.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        } else if (ghostFrameCounter <= 20) {
            canvas.drawBitmap(bitmaps.clydeRightMaps[1], (clyde.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (clyde.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        }
    }

    /**
     * draws the up direction animation
     * decides on which image to draw depending on the frame passed down
     */
    private void upDraw(Canvas canvas, Clyde clyde){
        if (ghostFrameCounter <= 10) {
            canvas.drawBitmap(bitmaps.clydeUpMaps[0], (clyde.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (clyde.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        } else if (ghostFrameCounter <= 20) {
            canvas.drawBitmap(bitmaps.clydeUpMaps[1], (clyde.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (clyde.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        }
    }

    /**
     * draws the down direction animation
     * decides on which image to draw depending on the frame passed down
     */
    private void downDraw(Canvas canvas, Clyde clyde) {
        if (ghostFrameCounter <= 10) {
            canvas.drawBitmap(bitmaps.clydeDownMaps[0], (clyde.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (clyde.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        } else if (ghostFrameCounter <= 20) {
            canvas.drawBitmap(bitmaps.clydeDownMaps[1], (clyde.gridLocation.getX() * 28 + xScaled) - pacGhostRadius,
                    (clyde.gridLocation.getY() * 28 + yScaled) - pacGhostRadius, null);
        }
    }




    

}


