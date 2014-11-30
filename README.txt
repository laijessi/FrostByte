FrostByte
-----------

Instructions to run from Eclipse:

1) You must have Gradle installed in your Eclipse
	a) Go to 'Help' on the menu bar
	b) Click 'Install new software..'
	c) Copy and Paste http://dist.springsource.com/snapshot/TOOLS/gradle/nightly into "Work with:"
	d) Press 'Add'
	e) Select all new files, and press Finish

2) Unzip all the files and import them as "Gradle files"
	a) Unzip the files into a directory of your choice
	b) Go to 'File' -> 'Import'
	c) Go under 'Gradle' and select 'Gradle Project' and click 'Next >'
	d) Find the directory using 'Browse..' and click "Build Model"
	e) Select all generated Gradle projects and hit Finish
		i) Make sure there are 3 generated Gradle projects
			- CSCI201_Project-core
			- CSCI201_Project-desktop
			- csci201project

3) In order to play the game
	a) First run the server
		i) Click CSCI201_Project-core
		ii) Go to src -> com.csci201.project
		iii) Open and run Server.java
	b) Next run the game itself
		i) Click CSCI201_Project-desktop
		ii) Right click the src directory and click 'Run As' and then 'Java Application'
		

		
What works and what doesn't:

1) The game itself works flawlessly
	a) Characters are able to move on both screens
	b) Projectiles are properly displayed and cause damage to opponent
	c) Energybar implemented such that players can not spam attacks constantly
	d) Items can be picked up and affect the character (speed up, slow down, add health)
	e) Game properly ends when a player loses
	f) Chat box to communicate between the two players operates with full functionality
	g) Gameplay statistics are saved into a properly setup MySQL database
		i) Username, time spent playing, wins, losses, total projectiles shot
	
2) What doesn't work
	a) Originally had sound effects with projectiles and background music
		i) Caused the game to run slower than intended, therefore this was removed
	b) Wanted to have a 4-player battle, but caused immense lag on the server
		i) Hopefully would be able to implement this one day
	c) Removed a minimap from the user's interface
		i) Was unnecessary due to the small size of the overall map
	d) Originally wanted a notification of what item was picked up via sound effect
	e) Wanted to have a double damage item for characters, but this was removed due to program architecture