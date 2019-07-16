package com.ecs160group.pacman;

//import android.appcompat.app;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.graphics.Point;
import android.view.Display;

public class PacmanActivity extends AppCompatActivity implements Dpad.DpadListener{

	private PacmanGame mPacmanGame;


	//Maze maze = new Maze(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Dpad dpad = new Dpad(this); // User Touch Controller 

		//set landscape mode, take out "Pacman" title
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);


		mPacmanGame = new PacmanGame(this, size.x, size.y);
		setContentView(mPacmanGame);
		//setContentView(userController); // displays touch controller on screen.

		//Log.d("Debugging", "In onCreate");
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

	@Override
	public void DpadPressed(float xPercent, float yPercent) { // id needed if more than 1 player.
		Log.d("User-Controller: ", "X percent: " + xPercent + " Y percent: " + yPercent);
		mPacmanGame.getPacman().updateNextDirection(xPercent, yPercent);
	}

}
