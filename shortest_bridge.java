You are given an n x n binary matrix grid where 1 represents land and 0 represents water.

An island is a 4-directionally connected group of 1's not connected to any other 1's. 
There are exactly two islands in grid.

You may change 0's to 1's to connect the two islands to form one island.

Return the smallest number of 0's you must flip to connect the two islands.

// use DFS + BFS : to get shortest bridge::: 


class Cell {
    int x;
    int y;
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Solution {
    int[] dr = {1, 0, -1, 0};
    int[] dc = {0, 1, 0, -1};

    public void dfs(int[][]grid, int n, int m, int r, int c) {
        if(r>=n || c>=m || r<0 || c<0)
            return;
        if(grid[r][c] != 1)
            return;

        grid[r][c] = 2;

        for(int i=0; i<4; i++){
            int nr = r+dr[i];
            int nc = c+dc[i];
            dfs(grid, n, m, nr, nc);
        } 
    }
    public int shortestBridge(int[][] grid) {
        int min_count = 0;
        int n = grid.length;
        int m = grid[0].length;
        boolean flag = false;
        
        // mark the first island
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++) {
                if(grid[i][j] == 1) {
                    dfs(grid, n, m, i, j);
                    flag = true;
                    break;
                }
            }
            if(flag == true)
                break;
        }

        // bfs : to reach shortest cell in next island
        Queue<Cell> q = new LinkedList<>();

        // put all cells in island1 into queue
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++) {
                if(grid[i][j] == 2)
                    q.offer(new Cell(i, j));
            }
        }

        // traverse from cells of i1 to i2
        while(!q.isEmpty()){
            List<Cell> newbfs = new ArrayList<>();

            // run for all the cells with 2 : island1
            for(Cell pair : q) {
                int r = pair.x;
                int c = pair.y;

                // now for each cell in island1 , travel in all 4 directions and check
                for(int i=0; i<4; i++){
                    int nr = r + dr[i];
                    int nc = c + dc[i];

                    // check boundaries
                    if(nr>=0 && nc>=0 && nr<n && nc<m) {
                    
                        if(grid[nr][nc] == 0) { // found water
                            newbfs.add(new Cell(nr, nc));
                            grid[nr][nc] = -1; // mark as vis
                        }else if(grid[nr][nc] == 1)
                            return min_count;
                    }
                }
            }

            q = new LinkedList<>(newbfs);
            min_count++; // one level is finished , now contunue with next levels until u find island2 = 1
        }
        return min_count-1;
    }
}

