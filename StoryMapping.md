# Team Subhunters - Pacman

## BHAG

You are Pacman stuck in a maze being chased by four ghosts, while collecting as many pellets as possible to escape, some pellets give Pacman the ability to fight back against the ghosts, and other fruits can help rejuvenate Pacman to escape the maze.

## Epic Stories

1. As Pacman, I want to collect all the pellets and power pellets to complete the game and get out of the maze.
1. As Pacman, I want to collect as many points as possible in order to get a high score in order to show my proficiency at getting out of the maze.
1. As the ghosts, I want to catch Pacman in the maze.
1. As a game developer I want to make my game pleasing to the eye.
1. As a fruit, I will spawn randomly and grant different rewards for the type of fruit I am.
1. As the maze, I want to keep Pacman and the ghosts contained within an area.

## Sprints Chart

| Epic Stories |
|  :---:       |
| Sprint 1     |
| Sprint 2     |
| Sprint 3     |

## CRC

* PacmanActivity/MainActivity: overall game lifecycle
* Grid: builds the maze for the game/activity to create paths for movement
* Blocks: individual block pieces of the maze (can be split up into more if needed)
* Pellets: add points to the score of pacman when eaten/collected. Parent of SuperPellet
* SuperPellet: add points to the score of pacman and make him super for a time
* BonusFruit: add points to the score of pacman when eaten/collected (can be split into more if needed)
* Pacman: Pacman movement and interaction with other objects in the game
* Ghost: Basic ghost parent class with movement and interaction
* Blinky: ghost that follows Pacman to location he is at
* Pinky: ghost that ambushes Pacman by cutting him off and getting in front of him
* Inky: target is same distance from Pacman as Blinky but at opposite offset to Blinky's offset from PacMan
* Clyde: Clyde goes to scatter location unless within certain distance from Pacman. Within this distance Clyde behaves like Blinky
