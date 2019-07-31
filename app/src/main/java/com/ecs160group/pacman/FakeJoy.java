package com.ecs160group.pacman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.MotionEvent;


/**
 * Cited Site:
 * https://www.instructables.com/id/A-Simple-Android-UI-Joystick/
 *
 * We took ideas of the joystick from this tutorial. We attempted to use a
 * Surface view at first but then reconfigured it to work without it.
 * This tutorial helped us draw and understand the constraints the balltop must have
 * with the base of the virtual joystick.
 */

public class FakeJoy {

    public final float baseRadius;
    public final float stickRadius;

    private PointF blockSize;
    public final PointF baseCenter;
    public PointF stickPosition;

    //private Pacman pacman;

    private final int ratio = 2;

    public Path base;
    public Path stick;

    public char direction;



    /**
     * CONSTRUCTOR
     */

    public FakeJoy(float radius, float hRadius,
                   PointF block_size, PointF position) {

        blockSize = new PointF(block_size.x, block_size.y);

        baseRadius = radius;
        stickRadius = hRadius;

        baseCenter = new PointF(position.x, position.y * blockSize.y / blockSize.x);
        stickPosition = new PointF(baseCenter.x, baseCenter.y);

        base = new Path();
        stick = new Path();

        base.addCircle(baseCenter.x, baseCenter.y,
                baseRadius, Path.Direction.CW);
        stick.addCircle(baseCenter.x, baseCenter.y,
                stickRadius, Path.Direction.CW);

        direction = 'l'; //initialize as left, doesn't matter since pacman wont move until user touches joystick


    }

    /**
     * sets the stick back to the center / called when user lifts his finger from joystick
     * updates the stick's x and y position variables to the center
     */
    public void setCenter () {
        stick.reset();
        stick.addCircle(baseCenter.x,baseCenter.y,
                stickRadius, Path.Direction.CW);
        stickPosition.x = baseCenter.x;
        stickPosition.y = baseCenter.y;
    }
    /**
     * called when user touches the joystick
     * updates/draws the stick as user moves it around
     * calls updateNextDirection - updates the direction of the stick, which will be read into pacman's direction
     * @param x - new x coordinate
     * @param y - new y coordinate
     */
    public void updateStick(float x, float y) {
        float displacement = (float) Math.sqrt((Math.pow(x - baseCenter.x, 2))
                + Math.pow(y - baseCenter.y, 2));
        if (displacement < baseRadius) {
            stickPosition.x = x;
            stickPosition.y = y;
            updateNextDirection((x - baseCenter.x) / baseRadius, (y - baseCenter.y) / baseRadius);
        } else { //to prevent stick from going out of bounds (too far away from base)

            float ratio = baseRadius / displacement;
            float constrainedX = baseCenter.x + (x - baseCenter.x) * ratio;
            float constrainedY = baseCenter.y + (y - baseCenter.y) * ratio;
            stickPosition.x = constrainedX;
            stickPosition.y = constrainedY;
            updateNextDirection((constrainedX - baseCenter.x) / baseRadius,
                    (constrainedY - baseCenter.y)/ baseRadius);
        }
        //draw new position of stick
        stick.rewind();
        stick.addCircle(stickPosition.x, stickPosition.y,
                stickRadius, Path.Direction.CW);
    }

    /**
     * called by draw function in PacmanGame
     * draws the base and stick
     * @param canvas
     * @param paint
     */
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.argb(255,0, 0, 255));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(7);
        canvas.drawPath(base, paint);

        paint.setColor(Color.argb(255, 255, 0 , 0));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(stick, paint);
    }

    void updateNextDirection(float xPercent, float yPercent){
        // After retrieving user input and After Updating direction check for Collision!
        // Use MovePacman(Pacman p, char direction)
        if(yPercent <= -.35 && xPercent <= .75 && xPercent >= -.75){ // UP
            direction = 'u';
            //Move(Pacman, direction); // Here?
            //Log.d("Pacman-Direction: ", "Move: " + direction);
        }
        else if(yPercent >= .35 && xPercent <= .75 && xPercent >= -.75){ // DOWN
            direction = 'd';
            //Log.d("Pacman-Direction: ", "Move: " + direction);
        }
        else if(xPercent <= -.35 && yPercent < .75 && yPercent > -.75){ // LEFT
            direction = 'l';
            //Log.d("Pacman-Direction: ", "Move: " + direction);
        }
        else if(xPercent >= .35 && yPercent < .75 && yPercent > -.75){ // RIGHT
            direction = 'r';
            //Log.d("Pacman-Direction: ", "Move: " + direction);
        }
    }

}

