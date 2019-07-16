package com.ecs160group.pacman;

import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Used to read in the text file and create a level from
 * the file
 */
public class LevelCreator
{
	// File needed to read
	String fileName = "raw/lvl1.txt";
	// maze object instance needed to create the maze
	Maze maze;
	// Objects needed to read a file
	Resources res;

	LevelCreator(Maze maze)
	{
		this.maze = maze;
		res = Resources.getSystem();
		readFile();
	}

	void readFile()
	{
		InputStream is = res.openRawResource(R.raw.lvl1);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		try {
			String eachLine = br.readLine();
			while (eachLine != null) {
				// the pieces of the maze in the file are separated by spaces, so to get each piece,
				// each is a single character
				String[] mazePieces = eachLine.split(" ");
				processLine(mazePieces);
			}
		} catch (Exception e) {
			// TODO: handle exception and check which exception and make better
			e.printStackTrace();
		}
	}

	void processLine(String[] pieces)
	{
		for (int i = 0; i < pieces.length; i++) {
			processBlock(pieces[i]);
		}
	}

	void processBlock(String s)
	{
		char c = s.charAt(0); // convert to character before processing
		switch(c) {
			case '|': // vertical wal
			case '-': // horizontal wall
			case '/': // bottom right or top left corner wall
			case '\\': // bottom left or top right corner wall
				break;
			case '.': // pellet
				break;
			case '*': // power pellet
				break;
			case 'o': // empty space
				break;
			case '1': // warp space
				break;
			case 's': // pacman start space
			case 't': // bonus fruit drop space
				break;
			case 'x': // ghost drop in space
				break;
			case 'g': // ghost entrance gate to waiting room left
			case 'h': // ghost entrance gate to waiting room right
				break;
			case 'a': // ghost waiting room space
				break;
			default:
				break;
		}
	}
}
