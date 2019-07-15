package com.ecs160group.pacman;

import androidx.appcompat.app.AppCompatActivity;

//import android.app.Activity;
import android.os.Bundle;
//import android.view.MotionEvent;
import android.util.Log;
import android.graphics.Bitmap;
import android.graphics.Canvas;
//import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.widget.ImageView;
//import java.util.Random;

public class PacmanActivity extends AppCompatActivity {

    ImageView gameView;
    Bitmap blankBitmap;
    Canvas canvas;
    //Paint paint;

    //variables to initialize
    int numHorizonPix;
    int numVerticPix;

    Maze maze = new Maze(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        numHorizonPix = size.x;
        numVerticPix = size.y;

        blankBitmap = Bitmap.createBitmap(numHorizonPix,
                numVerticPix,
                Bitmap.Config.ARGB_8888);
        canvas = new Canvas(blankBitmap);
        gameView = new ImageView(this);
        //paint = new Paint();

        setContentView(gameView);
        Log.d("Debugging", "In onCreate");
        maze.draw();
    }


}
