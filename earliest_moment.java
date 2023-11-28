There are n people in a social group labeled from 0 to n - 1. 
You are given an array logs where logs[i] = [timestampi, xi, yi] 
indicates that xi and yi will be friends at the time timestampi.

Friendship is symmetric. That means if a is friends with b, then b
is friends with a. Also, person a is acquainted with a person b if 
a is friends with b, or a is a friend of someone acquainted with b.

Return the earliest time for which every person became acquainted 
with every other person. If there is no such earliest time, 
return -1.

 

Example 1:

Input: logs = [[20190101,0,1],[20190104,3,4],[20190107,2,3],[20190211,1,5],[20190224,2,4],[20190301,0,3],[20190312,1,2],[20190322,4,5]], 
n = 6
Output: 20190301



// THINK OF UNION - FIND

class Solution {
    int[] root;
    int[] rank;
    PriorityQueue<Log> pq = new PriorityQueue<>();
    int counter;

    public int earliestAcq(int[][] logs, int n) {
        root = new int[n];
        rank = new int[n];
        counter = 0;

        for (int i = 0; i < n; i++) {
            root[i] = i;
            rank[i] = 1;
        }

        for (int i = 0; i < logs.length; i++) {
            pq.offer(new Log(logs[i][0], logs[i][1], logs[i][2]));
        }

        while(!pq.isEmpty()) {
            var log = pq.poll();
            if (union(log.i, log.j)) {
                counter++;
                if (counter == n - 1) {
                    return log.timestamp;
                }
            }
        }

        return -1;
    }

    private boolean union(int i, int j) {
        var rootI = find(i);
        var rootJ = find(j);

        if (rootI != rootJ) {
            if (rank[rootI] < rank[rootJ]) {
                root[rootI] = rootJ;
            } else if (rank[rootI] > rank[rootJ]) {
                root[rootJ] = rootI;
            } else {
                root[rootI] = rootJ;
                rank[rootJ] += 1;
            }

            return true;
        }

        return false;
    }

    private int find(int node) {
        while(root[node] != node) {
            node = root[node];
        }

        return node;
    }

    record Log(int timestamp, int i, int j) implements Comparable<Log> {
        @Override
        public int compareTo(Log o) {
            return Integer.compare(this.timestamp, o.timestamp);
        }
    }

}
