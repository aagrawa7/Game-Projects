# Game-Projects
Built on processing

Please follow these instructions to get this code running on your machine.

1. Create a new folder into your machine. Clone the entire project into that folder.
2. Import this folder into your Java IDE. Recommended Eclipse.
3. Once imported, right click on the folder, select properties. Then go to "Java Build Path"
4. In the libraries tab, select "Add JARs" button on the right.
5. You will be shown all the projects in the workspace. 
6. Select this(whatever folder you imported to in your IDE) project folder, and you will see 
   7 jar files. Select these jar files and click Ok. Click on apply. 
7. The project should work fine now. 

To run the files:
1. Go to src, open assignment, right click on any of the file(except for Maz_Prims_Astar.java), 
   and Run as Java application.


There are four files in the folder. Description of each of the file is given below.

1. BehaviorTree.java : There are two characters. The blue bot is the "hero" who is chased by the other bot "villan". This program is     implementing Behavior Tree for both the bots. 
   
   The behavior is:
   For the blue bot: Initially it is wandering, but if it sees the enemy, then it tries to go to the "safe-zone" which is the blue area. The villan cant see the hero in the hiding area and villan will also start back wandering. If the hero enters the red zone, its suicide. if any of the bots enter the orangish zone, they loose speed as long as they are in there. If they enter the green zone they gain speed.

2. GraphPath.java : Click anywhere on the screen and the bot will try to reach there with the shortest path and avoiding obstacles.      There exists no path inside the obstacles.

3. Maze_RB_Astar: It's a full fledged game. Goal is to reach the finish point before the enemy catches us. The maze changes              dynamically. The enemy bot computes the shortest distance to the human in real time using A* star algorithm. The maze is              dynamically generated using the Recursive Backtracking algorithm. 

PS: Contact me for the cheats to the game! 
