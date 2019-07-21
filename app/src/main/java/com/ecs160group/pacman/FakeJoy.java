package com.ecs160group.pacman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;


public class FakeJoy {
    public float centerX;
    public float centerY;

    public float baseRadius;
    public float hatRadius;

    private PointF blockSize;
    private PointF baseCenter;
    private PointF stickPosition;

    private Pacman pacman;

    private final int ratio = 2;

    public FakeJoy(float radius, float hRadius,
                   PointF block_size, PointF position ) {

        blockSize = new PointF(block_size.x, block_size.y );

        baseRadius = radius;
        hatRadius = hRadius;

        baseCenter = new PointF(position.x, position.y * blockSize.y / blockSize.x);
        centerX = baseCenter.x;
        centerY = baseCenter.y;
        stickPosition = new PointF (baseCenter.x, baseCenter.y);



    }
    public void reset(Canvas canvas) {
        //draw(canvas, centerX, centerY);
        Paint paint = new Paint();
        paint.setColor(Color.argb(255, 255, 255, 0));
        canvas.drawCircle(centerX, centerY, hatRadius, paint);
        stickPosition.x = baseCenter.x;
        stickPosition.y = baseCenter.y;
        }



    public void draw(Canvas myCanvas,float newX, float newY) {

        Paint colors = new Paint(); // Prepare paint
        // First determine the sin and cos of the angle that the touched point is at relative to the center of the joystick
        // Prevent the balltop from coming off base*
        //myCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
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
            myCanvas.drawCircle(newX, newY, hatRadius - (float) i * (ratio) / 2, colors); // Draw the shading for the hat
        }


    }

    public void drawStick(Canvas myCanvas, float newX, float newY) {
        Paint colors = new Paint(); // Prepare paint
        // First determine the sin and cos of the angle that the touched point is at relative to the center of the joystick
        // Prevent the balltop from coming off base*
        float hypotenuse = (float) Math.sqrt(Math.pow(newX - centerX, 2) + Math.pow(newY - centerY, 2));
        float sin = (newY - centerY) / hypotenuse; //sin = o/h
        float cos = (newX - centerX) / hypotenuse; //cos = a/h

        //Drawing the joystick hat
        for(int i = 0; i <= (int) (hatRadius / ratio); i++) // Multiple layers fpr shading purposes
        {
            colors.setARGB(100, 255, (int) (i * (255 * ratio / hatRadius)), (int) (i * (255 * ratio / hatRadius))); // Change the joystick color for shading purposes
            myCanvas.drawCircle(newX, newY, hatRadius - (float) i * (ratio) / 2 , colors); // Draw the shading for the hat
        }

    }
    public void update(float x, float y, Canvas canvas) {
            // Calculate displacement from resting position (center).
            float displacement = (float) Math.sqrt((Math.pow(x - centerX, 2)) + Math.pow(y - centerY, 2));
            if(displacement < baseRadius) // Valid input, no need to constrain the balltop.
            {
                stickPosition.x = x;
                stickPosition.y = y;
                draw(canvas, x, y);
            }
            else // When we need to constrain stick, get over here BRUH
            {
                float ratio = baseRadius / displacement;
                float constrainedX = centerX + (x - centerX) * ratio;
                float constrainedY = centerY + (y - centerY) * ratio;
                stickPosition.x = constrainedX;
                stickPosition.y = constrainedY;
                draw(canvas, constrainedX, constrainedY);

            }
            //drawStick(canvas, stickPosition.x * blockSize.x, stickPosition.y*blockSize.y);
            /*Paint paint = new Paint();
            paint.setColor(Color.argb(255, 255, 255, 0));
            canvas.drawCircle(centerX, centerY, hatRadius, paint);*/
        }

}
