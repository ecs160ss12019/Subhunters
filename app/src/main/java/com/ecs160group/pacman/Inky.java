package com.ecs160group.pacman;

/**
 * Inky's target is same distance from Blinky to Pacman, opposite offset
 * to Blinky's offset from Blinky
 */
public class Inky extends Ghost
{
	// Scatters to the bottom right
	private static final Location SCATTER_LOC = new Location(27, 30, Block.WALL);

	private Blinky blinky;

	/**
	 * Gets the scatter location
	 *
	 * @return scatter location
	 */
	public Location getScatterLocation()
	{
		return SCATTER_LOC;
	}

	private Blinky findBlinky()
	{
		if (blinky != null) // if blinky is saved as a variable already
			return blinky;
		return new Blinky(); // TODO: fix to find blinky in maze, currently so code doesn't break
	}

	/**
	 * Get chase location
	 * Inky's target is same distance from PacMan as Blinky but at opposite offset
	 * to Blinky's offset from PacMan
	 *
	 * @return the chase target
	 */
	public Location getChaseLocation()
	{
		Location pacLoc = pacman.getLoc();
		Location blinkyLoc = findBlinky().getLoc();
		// once we have locations need to create new offsets for location to find
		// if the location of blinky or pacman don't exist, use 0 for row and column
		int pacRow = pacLoc != null ? pacLoc.getX() : 0;
		int pacCol = pacLoc != null ? pacLoc.getY() : 0;
		int blinkyRow = blinkyLoc != null ? blinkyLoc.getX() : 0;
		int blinkyCol = blinkyLoc != null ? blinkyLoc.getX() : 0;
		// get differences in locations
		int rowDelta = blinkyRow - pacRow;
		int colDelta = blinkyCol - pacCol;
		// create a location that is same distance from Pacman but at opposite offset from Blinky's
		return new Location(pacRow - rowDelta, pacCol - colDelta, Block.EMPTY);
	}

	/**
	 * Called when Inky needs to move
	 */
	public void move()
	{
		if (pacman.isSuper()) {
			scatter(getScatterLocation());
		} else {
			chase(getChaseLocation());
		}
	}

}
