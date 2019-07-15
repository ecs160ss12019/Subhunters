package com.ecs160group.pacman;

//import android.appcompat.app;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.util.Log;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.widget.ImageView;
import java.util.Random;

public class PacmanActivity extends AppCompatActivity {

	private PacmanGame mPacmanGame;


	//Maze maze = new Maze(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);

		mPacmanGame = new PacmanGame(this, size.x, size.y);
		setContentView(mPacmanGame);
		//Log.d("Debugging", "In onCreate");
		//maze.draw();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}





}