package com.ecs160group.pacman;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Used to read in the text file and create a level from
 * the file
 */
public class LevelCreator
{
//	// File needed to read
//	String fileName = "raw/lvl1.txt";
	// maze object instance needed to create the maze
	Location[][] maze;
	// Objects needed to read a file
	Context context;


	/**
	 * Constructor for LevelCreator
	 * @param m maze to be updated
	 */
	LevelCreator(Location[][] m, Context context)
	{
		this.maze = m;
		this.context = context;
		readAndProcessFile();
	}

	/**
	 * Reads the file and process the file to make a maze
	 */
	void readAndProcessFile()
	{
		//Log.d("Debugging", "readAndProcessFile");

//		Resources res = Resources.getSystem();
        //ClassLoader classloader = Thread.currentThread().getContextClassLoader();

		//InputStream inputStream = ClassLoaderUtil.getResourceAsStream("lvl1", LevelCreator.class);
		//InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
		//BufferedReader reader = new BufferedReader(streamReader);

		InputStream is = context.getResources().openRawResource(R.raw.lvl1);



//        InputStream is = res.openRawResource(R.raw.lvl1);
//        InputStream is = (InputStream) LevelCreator.class.getResourcesAsStream(R.raw.lvl1);
		InputStreamReader reader = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(reader);

		try {
			String eachLine = br.readLine();
			int lineNum = 0;
			while (eachLine != null) { // process file line at a time
				// the pieces of the maze in the file are separated by spaces, so to get each piece,
				// each is a single character
				String[] mazePieces = eachLine.split(" ");
				//Log.d("LINE#: ", "lineNum in readAndProcess(): " + lineNum);
				processLine(mazePieces, lineNum);
				// process next line of buffer after processing the line
				eachLine = br.readLine();
				lineNum++;
			}
		} catch (Exception e) {
			// TODO: handle exception and check which exception and make better
			e.printStackTrace();
		}
	}

	/**
	 * Processes a line of the text file to create a horizontal line of the maze
	 * @param pieces A line of pieces of the maze
	 * @param lineNum line number we are on in the text file (y coord of Maze)
	 */
	void processLine(String[] pieces, int lineNum)
	{
		//for (int i = 0; i < pieces.length && pieces[i] != null; i++) {
			for (int i = 0; i < pieces.length; i++) {
			//Log.d("txt chars: ", "pieces[ " + i + "]:" + pieces[i]); // For debugging
			processBlock(pieces[i], i, lineNum);
		}
	}

	/**
	 * Processes a block of the text file to put in the starting maze
	 * @param s string/char to process
	 * @param x x coordinate of the block to process
	 * @param y y coordinate of the block to process
	 */
	void processBlock(String s, int x, int y)
	{
		char c = s.charAt(0); // convert to character before processing
		switch(c) {
			case '|': // vertical wall
				maze[x][y] = new Location (x, y, Block.VERTICAL_WALL);
				break;
			case '-': // horizontal wall
				maze[x][y] =  new Location (x, y, Block.HORIZONTAL_WALL);
				break;
			case '/': // bottom right or top left corner wall
				maze[x][y] = new Location (x, y, Block. BOT_RIGHT_TOP_LEFT_WALL);
				break;
			case '\\': // bottom left or top right corner wall
				maze[x][y] = new Location (x, y, Block.BOT_LEFT_TOP_RIGHT_WALL);
				//maze[x][y] = new Location(x, y, Block.WALL);
				break;
			/*case '|': // vertical wall
			case '-': // horizontal wall
			case '/': // bottom right or top left corner wall
			case '\\': // bottom left or top right corner wall
				maze[x][y] = new Location(x, y, Block.WALL);
				break;*/
			case '.': // pellet
				maze[x][y] = new Location(x, y, Block.PELLET);
				break;
			case '*': // power pellet
				maze[x][y] = new Location(x, y, Block.POWER_PELLET);
				break;
			case '1': // warp space
				maze[x][y] = new Location(x, y, Block.WARP_SPACE);
				break;
			case 's': // pacman start space
				// start space is basically null space, nothing there except pacman when game starts
				maze[x][y] = new Location(x, y, Block.PAC_SPAWN);
			case 't': // bonus fruit drop space
				// is null except when fruit is placed
				maze[x][y] = new Location(x, y, Block.FRUIT_SPAWN);
				break;
			case 'x': // ghost drop in space
				// is null space except when ghost drops in from waiting room
				maze[x][y] = new Location(x, y, Block.GHOST_SPAWN);
				break;
			case 'g': // ghost entrance gate to waiting room left
			case 'h': // ghost entrance gate to waiting room right
				maze[x][y] = new Location(x,y, Block.GHOST_GATE);
				break;
			case 'a': // ghost waiting room space
				// is empty space except when ghost is moving around in it
				maze[x][y] = new Location(x,y, Block.GHOST_WAIT);
				break;
			case 'o': // empty space
			default:
				maze[x][y] = new Location(x, y, Block.EMPTY);
				break;
		}
	}



}
