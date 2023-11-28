There are n people in a social group labeled from 0 to n - 1. 
You are given an array logs where logs[i] = [timestampi, xi, yi] 
indicates that xi and yi will be friends at the time timestampi.

Friendship is symmetric. That means if a is friends with b, then b is 
friends with a. Also, person a is acquainted with a person b if a is friends 
with b, or a is a friend of someone acquainted with b.

Return the earliest time for which every person became acquainted with 
every other person. If there is no such earliest time, return -1.





// THINK OF DISJOINT SET

class UnionFind {
    int[] par;
    int[] rank;

    public UnionFind(int n) {
        this.par = new int[n];
        this.rank = new int[n];

        for(int i=0; i<n; i++) {
            this.par[i] = i;
        }
    }

    public int findParent(int ele) {
        if(ele == par[ele])
            return ele;
        return findParent(par[ele]);
    }

    public boolean unionByRank(int a, int b) {
        int pa = findParent(a);
        int pb = findParent(b);

        if(pa == pb) {
            // belongs to same compo
            return false;
        }

        int ra = rank[pa];
        int rb = rank[pb];

        if(ra > rb) 
            par[pb] = pa;
        else if(ra < rb)
            par[pa] = pb;
        else {
            par[pa] = pb;
            rank[pa]++;
        }

        return true;
    }
}


class Solution {
    public int earliestAcq(int[][] logs, int n) {
        // sort the array based on the ts:
       Arrays.sort(logs, Comparator.comparingInt(arr -> arr[0]));

        int compo = n;
        UnionFind uf = new UnionFind(n);

        for(int[] log : logs) {
            int ts = log[0];
            int u = log[1];
            int v = log[2];

            if(uf.unionByRank(u, v) == true) {
                compo--;
            }

            if(compo == 1) {
                return ts;
            }
        }

        return -1;
    }
}
