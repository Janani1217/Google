You are controlling a robot that is located somewhere in a room. 
The room is modeled as an m x n binary grid where 0 represents a wall and 1 represents an empty slot.

The robot starts at an unknown location in the room that is guaranteed to be empty, and you do not have access to the grid, 
but you can move the robot using the given API Robot.

You are tasked to use the robot to clean the entire room (i.e., clean every empty cell in the room).
The robot with the four given APIs can move forward, turn left, or turn right. Each turn is 90 degrees.

When the robot tries to move into a wall cell, its bumper sensor detects the obstacle, 
and it stays on the current cell.

Design an algorithm to clean the entire room using the following APIs:

interface Robot {
  // returns true if next cell is open and robot moves into the cell.
  // returns false if next cell is obstacle and robot stays on the current cell.
  boolean move();

  // Robot will stay on the same cell after calling turnLeft/turnRight.
  // Each turn will be 90 degrees.
  void turnLeft();
  void turnRight();

  // Clean the current cell.
  void clean();
}
Note that the initial direction of the robot will be facing up. You can assume all four edges of the grid are all surrounded by a wall.

 

Custom testing:

The input is only given to initialize the room and the robot's position internally. You must solve this problem "blindfolded". 
In other words, you must control the robot using only the four mentioned APIs without knowing the room layout and the initial robot's position.










/**
 * // This is the robot's control interface.
 * // You should not implement it, or speculate about its implementation
 * interface Robot {
 *     // Returns true if the cell in front is open and robot moves into the cell.
 *     // Returns false if the cell in front is blocked and robot stays in the current cell.
 *     public boolean move();
 *
 *     // Robot will stay in the same cell after calling turnLeft/turnRight.
 *     // Each turn will be 90 degrees.
 *     public void turnLeft();
 *     public void turnRight();
 *
 *     // Clean the current cell.
 *     public void clean();
 * }
 */
class Solution {
    // the order of movement is vvi : always move right
    int[][] direc = {{-1,0}, {0,1}, {1,0}, {0,-1}};

    Set<Pair<Integer, Integer>> vis = new HashSet<>();
    Robot robot;

    public void goBack() {
        robot.turnRight();
        robot.turnRight();

        robot.move();
        
        robot.turnRight();
        robot.turnRight();
    }

    public void backtrack(int r, int c, int d) {
        vis.add(new Pair(r,c));
        robot.clean();

        // move in 4 direcrt: in clockwise since 90 degree movement allowed
        // 0 = up , 1 = rt, 2 = down, 3 = left
        for(int i=0; i<4; ++i) {
            int nd = (d+i)%4;
            int nr = r + direc[nd][0];
            int nc = c + direc[nd][1];

            if(!vis.contains(new Pair(nr, nc)) && robot.move()) {
                backtrack(nr, nc, nd);
                goBack();
            }

            robot.turnRight();
        }
    }

    public void cleanRoom(Robot robot) {
        this.robot = robot;
        // u can pick any starting coords
        backtrack(0, 0, 0);
    }
}
