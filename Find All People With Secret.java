You are given an integer n indicating there are n people numbered from 0 to n - 1. 
You are also given a 0-indexed 2D integer array meetings where meetings[i] = [xi, yi, timei] 
indicates that person xi and person yi have a meeting at timei. 

A person may attend multiple meetings at the same time. Finally, you are given an integer firstPerson.

Person 0 has a secret and initially shares the secret with a person firstPerson at time 0. 
This secret is then shared every time a meeting takes place with a person that has the secret. 
More formally, for every meeting, if a person xi has the secret at timei, 
then they will share the secret with person yi, and vice versa.

The secrets are shared instantaneously. That is, a person may receive the secret and share it with 
people in other meetings within the same time frame.

Return a list of all the people that have the secret after all the meetings have taken place. 
You may return the answer in any order.


// CODE: treeset + unionfind usage : 
class unionFind {
  int[] par;

  public unionFind(int n) {
    par = new int[n];

    for(int i=0; i<n; i++) {
      par[i] = i;
    }
  }

  public int findPar(int u) {
    if(par[u] != u) 
        par[u] = findPar(par[u]);
    return par[u];
  }

  public void union(int a, int b) {
    int ap = findPar(a);
    int bp = findPar(b);

    if(ap != bp) {
      par[ap] = bp;
    }
  }

  public void reset(int u) {
    par[u] = u;
  }
}

class Solution {
  public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {

    // prep treemap instead of adj list : to store ts value for sorted 
    TreeMap<Integer, List<int[]>> tree = new TreeMap<>();

    unionFind uf = new unionFind(n);
    uf.union(0, firstPerson); // *** //

    Set<Integer> res = new HashSet<>(); // store ans + handle duplicates
    res.add(0);
    res.add(firstPerson);

    // traverse through all ts stored in tree
    for(int time : tree) {
      List<int[]> ll = tree.get(time);

      // connect all the nodes present in the initial meets:
      for(int[] meet : ll) {
        uf.union(meet[0], meet[1]);
      }

      // try to connect with other members in diff meet if connects with src  :
      for(int[] meet : ll) {
        if(uf.findPar(meet[0]) == uf.findPar(0) || uf.findPar(0) == uf.findPar(meet[1])) {
          res.add(meet[0]);
          res.add(meet[1]);
        } else {
          uf.reset(meet[0]);
          uf.reset(meet[1]);
        }
      }
    }

    return new ArrayList<>(res);
    
  }
