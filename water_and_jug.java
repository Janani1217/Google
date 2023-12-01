https://leetcode.com/problems/water-and-jug-problem/description/


You are given two jugs with capacities jug1Capacity and jug2Capacity liters. 
There is an infinite amount of water supply available. Determine whether it is 
possible to measure exactly targetCapacity liters using these two jugs.

If targetCapacity liters of water are measurable, you must have targetCapacity 
liters of water contained within one or both buckets by the end.

Operations allowed:

Fill any of the jugs with water.
Empty any of the jugs.
Pour water from one jug into another till the other jug is completely full, 
or the first jug itself is empty.



// NOT OPTIMAL : BUT BFS APPROACH TO EXPLORE ALL STATES

class Solution {
    public boolean canMeasureWater(int jug1Capacity, int jug2Capacity, int targetCapacity) {
        int j1 = jug1Capacity;
        int j2 = jug2Capacity;
        int tc = targetCapacity;

        // tc is unreachable by max case also
        if(j1 + j2 < tc) 
            return false;
        
        // cannot measure odd cap using ecven cap
        if(j1 % 2 == 0 && j2 % 2 == 0 && tc % 2 != 0)
            return false;

        // using bfs to explore all the states possible and find whether 
        // we can reach the tc by any of the states.

        Queue<int[]> q = new LinkedList<>();
        q.add(new int[] {0, 0}); // first state = empty

        // storing the vis states:
        HashSet vis = new HashSet<>();

        while(!q.isEmpty()) {
            int[] curr = q.poll();
            int x = curr[0];
            int y = curr[1];

            // if the curr state already exists in explored states : ignore
            if(vis.contains(x + "," + y))
                continue;
            
            // check whether u have reached the TC
            if(x+y == tc) 
                return true;

            // else process the curr state:
            vis.add(x + "," + y);

            // add the all poss states into the queue:

            q.add(new int[] {0, y}); // empty j1 ; j2 will be curr same
            q.add(new int[] {x, 0}); // empty j2 ; j1 will be curr same

            q.add(new int[] {j1, y}); // fill j1 full ; j2 will be curr same
            q.add(new int[] {x, j2}); // fill j2 full ; j1 will be curr same

            // pour x -> y (y already has water = j2 - y )
            q.add(new int[] {x-Math.min(x, j2-y) , y+Math.min(x, j2-y)});

            // pour y -> x : (considering j1 has some water = j1 - x)
            q.add(new int[] {x + Math.min(y, j1-x) , y - Math.min(y, j1-x)});
        }
        return false;
    }
}



// optiomal : finding gcd of 2 jugs and check if its dividing target or not : 
// gcd is used when we need to find if some combination exixist which could give us target:



class Solution {
    public int gcd(int x, int y) {
        if(y == 0)
            return x;
        
        return gcd(y, x%y);
    }

    public boolean canMeasureWater(int jug1Capacity, int jug2Capacity, int targetCapacity) {
        if(jug1Capacity + jug2Capacity < targetCapacity)
            return false;
        
        if(targetCapacity == jug1Capacity || jug2Capacity == targetCapacity || targetCapacity == jug1Capacity + jug2Capacity)
            return true;

        if(targetCapacity % gcd(jug1Capacity, jug2Capacity) == 0)
            return true;
    
        return false;
    }
}
