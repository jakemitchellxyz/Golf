/**
 * Jake Mitchell (jmitch32), Koushul Ramjattun (kramjatt)
 * Golf_Mitchell_Ramjattun
 * 13 October 2016
 *
 * TAs: Jiageng Zhang, Dallis Polashenski
 */


- Packages: Default
		- GameEngine
		- Golf
		- ScoreKeeper
		- UserInteraction
	  : physics
		- Ball
		- Club
		- PhysicsEngine
	  : terrain
		- Course
		- Hole
		- Point
		- TerrainGenerator
		- Visualizer


The main method is called in the Golf class, which just creates a GameEngine and runs it.
The GameEngine creates a new instance of UserInteration [ which has all the methods to inetract with the user, get information from him and display information back to him]
					 Course 	[ which creates different terrains with varying number of holes and with different difficulty levels ]
					 PhysicsEngine  [ Does all the math and awesome physics. Takes into account air resistance. Calculates the height of the ball to see if it can go above obstacles]
					 ScoreKeeper    [ Keeps track of the user's score ]

*************************************************************************************************************************************************************************************************

Our terrains have 4 types of obstacles: Trees, Rocks, Ponds, Cactus. Each has a randomly generated height and radius. SO, we have a 3D terrain. With a ball object the can move around freely in a three-dimensional space.

The TerrainGenerator procedurally generates new terrains based on different biomes. FORRESTS have more trees than anything else, DESERTS have more rocks and cactus, SWAMPS have more ponds.
A path is randomly created each time running from the tee to the hole. And we create a "green area" arounf the hole for putting. The path is created by combining 3 random sine curves. Obstacles are then randomly generated all around the path.
Any coordinate with stuff on it is "blocked" so no other obstacle can be generated on that point. Every other coordinate is free for the ball to move onto.

{{ The Visualizer }}

When the user wants to look around, the visualizer object is called, which returns all the obstacles within 10.9361 yards of the user's current location.
The user can thus know what obstacles are within 30 degrees to his left and 30 degrees to his right, while looking directly at the  (Peripheral vision of a normal person)

The visualizer checks for obstacles from -30 degrees to + 30 degrees in crements of 5 degrees, checking for obstacles.

{{ Clubs }}

We give the user the choice between 10 different clubs, each with different ranges, loft angles and accuracy.
Clubs with greater ranges come with greater inaccuracies, therefore we randomly add a small angle to the angle the user chose to shoot at, to make the game more realistic.

Keeping track of the location of everything is the Point Array Array object that acts like a 2D grid. With the location of the tee, hole, path and every obstacles.

**************************************************************************************************************************************************************************************************

++++++ PLAYING ++++++

The user picks a terrain, a difficulty level.
He/She then picks a Club and the power to hit it with.
If the ball goes out of bounds, we reset it to its original location.
If it hits an obstacles, we stop right there and tell the user what happened.


