package com.ecs160group.pacman;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;

/*
    User's controller for Pacman game
    Allows user to move left, right, up, and down.
    Will be located on bottom-left side of screen
*/

public class Dpad extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener{

    private float centerX;
    private float centerY;
    private float baseRadius;
    private float hatRadius;
    private DpadListener DpadCallback;
    private final int ratio = 2; // The smaller, the more shading will occur

    // Uses Device's screen dimensions to scale the controller.
    private void setupDimensions()
    {
        centerX = getWidth() / 6;   // Set positioning for X, CURRENTLY at center, MOVE to bottom left.
        centerY = getHeight() / 8;  // Set positioning for Y, SurfaceView methods getW, getH
        baseRadius = Math.min(getWidth(), getHeight()) / 8; // Size of base
        hatRadius = Math.min(getWidth(), getHeight()) / 14; // Size of hat, "balltop"
    }

    public Dpad(Context context)
    {
        super(context);
        getHolder().addCallback(this); // Set the callback methods in this class to be the ones to be called when those events happen
        setOnTouchListener(this); // SurfaceView to use onTouch method to handle user touch inputs,
        if(context instanceof DpadListener)
            DpadCallback = (DpadListener) context; // Allows us to call DpadPressed in class representing the activity at any time.
    }

    public Dpad(Context context, AttributeSet attributes, int style)
    {
        super(context, attributes, style);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(context instanceof DpadListener)
            DpadCallback = (DpadListener) context;
    }

    public Dpad (Context context, AttributeSet attributes)
    {
        super(context, attributes);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        if(context instanceof DpadListener)
            DpadCallback = (DpadListener) context;
    }

    private void drawDpad(float newX, float newY)
    {
        if(getHolder().getSurface().isValid()) // Prevents exceptions at runtime.
        {
            Canvas myCanvas = this.getHolder().lockCanvas(); // Need to use canvas to draw controller
            Paint colors = new Paint(); // Prepare paint
            myCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); // Clear the background

            // First determine the sin and cos of the angle that the touched point is at relative to the center of the joystick
            // Prevent the balltop from coming off base*
            float hypotenuse = (float) Math.sqrt(Math.pow(newX - centerX, 2) + Math.pow(newY - centerY, 2));
            float sin = (newY - centerY) / hypotenuse; //sin = o/h
            float cos = (newX - centerX) / hypotenuse; //cos = a/h

            // Draw the base first before shading
            colors.setARGB(150, 1, 255, 255); // Set the color for the base.
            myCanvas.drawCircle(centerX, centerY, baseRadius, colors); // Draw base with ^ color.
            for(int i = 1; i <= (int) (baseRadius / ratio); i++)
            {
                colors.setARGB(255/i, 1, 255, 255); // Gradually decrease the shade of black drawn to create a nice shading effect
                myCanvas.drawCircle(newX - cos * hypotenuse * (ratio/baseRadius) * i,
                        newY - sin * hypotenuse * (ratio/baseRadius) * i, i * (hatRadius * ratio / baseRadius), colors); // Gradually increase the size of the shading effect
            }
            //Drawing the joystick hat
            for(int i = 0; i <= (int) (hatRadius / ratio); i++) // Multiple layers fpr shading purposes
            {
                colors.setARGB(100, 255, (int) (i * (255 * ratio / hatRadius)), (int) (i * (255 * ratio / hatRadius))); // Change the joystick color for shading purposes
                myCanvas.drawCircle(newX, newY, hatRadius - (float) i * (ratio) / 2 , colors); // Draw the shading for the hat
            }
            getHolder().unlockCanvasAndPost(myCanvas); // Write the new drawing to the SurfaceView, Show to user.
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        setupDimensions();
        drawDpad(centerX, centerY);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public boolean onTouch(View v, MotionEvent e) // Automatically called when user moves controller.
    {
        if(v.equals(this))
        {
            if(e.getAction() != e.ACTION_UP) // Make sure to activate only on user touch, reset on release
            {
                // Calculate displacement from resting position (center).
                float displacement = (float) Math.sqrt((Math.pow(e.getX() - centerX, 2)) + Math.pow(e.getY() - centerY, 2));
                if(displacement < baseRadius) // Valid input, no need to constrain the balltop.
                {
                    drawDpad(e.getX(), e.getY()); // Coordinates in pixels, Sends hat to be drawn at that location
                    DpadCallback.DpadPressed((e.getX() - centerX)/baseRadius, (e.getY() - centerY)/baseRadius);
                }
                else // When controller is not in use. Reset to center.
                {
                    float ratio = baseRadius / displacement;
                    float constrainedX = centerX + (e.getX() - centerX) * ratio;
                    float constrainedY = centerY + (e.getY() - centerY) * ratio;
                    drawDpad(constrainedX, constrainedY);
                    DpadCallback.DpadPressed((constrainedX-centerX)/baseRadius, (constrainedY-centerY)/baseRadius);
                }
            }
            else // Nothing happens, draw at center.
                drawDpad(centerX, centerY);
            DpadCallback.DpadPressed(0,0);
        }
        return true;
    }

    public interface DpadListener // Used to communicate position of balltop.
    {
        // Any info needed can be returned, Add as an argument.
        void DpadPressed(float xPercent, float yPercent); // int id only needed to express more than 1 player. CAN remove later..
    }

}
