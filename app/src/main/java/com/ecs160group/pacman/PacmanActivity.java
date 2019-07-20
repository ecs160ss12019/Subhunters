package com.ecs160group.pacman;

//import android.appcompat.app;
import android.app.Activity;
import android.content.pm.ActivityInfo;
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
import android.view.Window;
import android.widget.ImageView;
import java.util.Random;

public class PacmanActivity extends AppCompatActivity implements Joystick.JoystickListener{

	private PacmanGame mPacmanGame;


	//Maze maze = new Maze(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		//Joystick userController = new Joystick(this);


		//set landscape mode, take out "Pacman" title
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		mPacmanGame = new PacmanGame(this, size.x, size.y);

		//mPacmanGame.startNewGame();
		//mPacmanGame = new PacmanGame(this, size.x, size.y);

		setContentView(mPacmanGame);
		//setContentView(userController); // displays touch controller on screen.
		//setContentView(mPacmanGame);
		//setContentView(userController);
		Log.d("Debugging", "In onCreate");
		//maze.draw();
	}

	@Override
	protected void onResume() {
		super.onResume();

		mPacmanGame.resume();
	}

	@Override
	protected void onPause() {
		super.onPause();

		mPacmanGame.pause();
	}

	@Override // When controller touched this will be called.
	public void JoystickMoved(float xPercent, float yPercent) {
		Log.d("User-Controller: ", "X percent: " + xPercent + " Y percent: " + yPercent);
		// include pacman controls here, Must convert percent values into directional values up,down,left,right
		mPacmanGame.getPacman().updateNextDirection(xPercent, yPercent);
	}




}
