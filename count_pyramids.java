A farmer has a rectangular grid of land with m rows and n columns that can be divided into unit cells. 
Each cell is either fertile (represented by a 1) or barren (represented by a 0). 
All cells outside the grid are considered barren.

A pyramidal plot of land can be defined as a set of cells with the following criteria:

The number of cells in the set has to be greater than 1 and all cells must be fertile.

The apex of a pyramid is the topmost cell of the pyramid. The height of a pyramid is the 
number of rows it covers. Let (r, c) be the apex of the pyramid, and its height be h. 
Then, the plot comprises of cells (i, j) where r <= i <= r + h - 1 and c - (i - r) <= j <= c + (i - r).

An inverse pyramidal plot of land can be defined as a set of cells with similar criteria:

The number of cells in the set has to be greater than 1 and all cells must be fertile.

The apex of an inverse pyramid is the bottommost cell of the inverse pyramid. 

The height of an inverse pyramid is the number of rows it covers. Let (r, c) be the apex of the pyramid, 
and its height be h. Then, the plot comprises of cells (i, j) 
where r - h + 1 <= i <= r and c - (r - i) <= j <= c + (r - i).

Given a 0-indexed m x n binary matrix grid representing the farmland, return the total number of 
pyramidal and inverse pyramidal plots that can be found in grid.


// DP approach : think of : very direct :


class Solution {
    public int countPyramids(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        // dp approach :
        int[][] dp = new int[n][m];
        int cnt = 0; // cnt the no. of overall pyramids

        // 1. cnt inverted pyramid
        for(int i = 1; i < n; i++) {
            for(int j=1; j<m-1; j++) { // first and last col are ignored since boundary cells cant proceed fourther
               if(grid[i][j] == 1 && grid[i-1][j-1]==1 && grid[i-1][j] == 1 && grid[i-1][j+1]== 1) {
                    dp[i][j] = 1 + Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i-1][j+1])); // 1+ becoz of ht 
                    cnt += dp[i][j];
               }
            }
        }

        dp = new int[n][m]; // re init the dp arr ******

        //  2. cnt the normal pyramids
        for(int i=n-2; i>=0; i--) {
            for(int j=m-2; j>=1; j--) {
                if(grid[i][j] == 1 && grid[i+1][j-1]==1 && grid[i+1][j]==1 && grid[i+1][j+1]==1) {
                    dp[i][j] = 1 + Math.min(dp[i+1][j+1], Math.min(dp[i+1][j], dp[i+1][j-1])); // 1+ becoz of ht 
                    cnt += dp[i][j];
                }
            }
        }

        return cnt;
    }
}
