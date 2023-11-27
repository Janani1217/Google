You are given an integer n, which indicates that there are n courses labeled from 1 to n. 
You are also given an array relations where relations[i] = [prevCoursei, nextCoursei], representing a 
prerequisite relationship between course prevCoursei and course nextCoursei: course prevCoursei has to be 
taken before course nextCoursei.

In one semester, you can take any number of courses as long as you have taken all the prerequisites 
in the previous semester for the courses you are taking.

Return the minimum number of semesters needed to take all courses. If there is no way to take 
all the courses, return -1.


Input: n = 3, relations = [[1,3],[2,3]]
Output: 2



// TOPO : BFS APPROACH 

import java.util.*;

class Solution {
    public int minimumSemesters(int n, int[][] relations) {
        Map<Integer, List<Integer>> adjList = new HashMap<>();
        int[] inDegree = new int[n + 1];

        // Build the adjacency list and calculate in-degrees
        for (int[] relation : relations) {
            int u = relation[0];
            int v = relation[1];
            adjList.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
            inDegree[v]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        int semesters = 0;
        int coursesTaken = 0;

        // Enqueue courses with in-degree 0
        for (int i = 1; i <= n; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
                coursesTaken++;
            }
        }

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int currentCourse = queue.poll();

                // Check prerequisites for the current course
                if (adjList.containsKey(currentCourse)) {
                    for (int nextCourse : adjList.get(currentCourse)) {
                        inDegree[nextCourse]--;
                        if (inDegree[nextCourse] == 0) {
                            queue.offer(nextCourse);
                            coursesTaken++;
                        }
                    }
                }
            }

            semesters++;
        }

        // Check if all courses are taken
        return (coursesTaken == n) ? semesters : -1;
    }
}
