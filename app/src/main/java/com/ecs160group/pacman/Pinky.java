package com.ecs160group.pacman;

/**
 * Ghost that attempts to get ahead of Pacman to ambush him
 */
public class Pinky extends Ghost
{
	// scatters to the top left
	private static final Location SCATTER_LOC = new Location(0, 0, Block.WALL);

	/**
	 * Default ctor which inherits from the ghost class
	 */
	public Pinky(int screenX, Location spawnLoc, Maze m)
	{
		super(screenX, spawnLoc, m);
	}

	/**
	 * Gives the scatter location
	 *
	 * @return scatter location
	 */
	public Location getScatterLocation()
	{
		return SCATTER_LOC;
	}

	/**
	 * Gives the location to target as the chase location
	 * Pinky tries to cut off pacman by being ahead of him 4 in bounds spaces ahead
	 *
	 * @return
	 */
	public Location getChaseLocation()
	{
		char pacDir = pacman.getDirection();
		Location pacLoc = pacman.getGridLoc();
		// get spaces ahead of pacman if pacman exists, otherwise get location 4 ahead of self
		Location adj1 = pacLoc != null ? pacLoc.getAdjacentLocation(pacDir) : getGridLoc();
		Location adj2 = adj1.getAdjacentLocation(pacDir);
		Location adj3 = adj2.getAdjacentLocation(pacDir);
		Location adj4 = adj3.getAdjacentLocation(pacDir);
		// return farthest from pacman in bounds location
		if (Maze.isInBounds(adj4))
			return adj4;
		if (Maze.isInBounds(adj3))
			return adj3;
		if (Maze.isInBounds(adj2))
			return adj2;
		// if none are valid in bounds locations, just give pacman's location
		return Maze.isInBounds(adj1) ? adj1 : pacLoc;
	}

	/**
	 * Called when Pinky needs to move
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
