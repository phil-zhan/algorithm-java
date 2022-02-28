package org.study.system.class48;

/**
 * https://leetcode-cn.com/problems/max-area-of-island/
 *
 * @author phil
 * @date 2022/2/15 15:33
 */
public class Code10_695_MaxAreaOfIsland {

    public static void main(String[] args) {
        int maxAreaOfIsland = new Code10_695_MaxAreaOfIsland().maxAreaOfIsland(new int[][]{
                {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}});
        System.out.println(maxAreaOfIsland);
    }

    public int maxAreaOfIsland(int[][] grid) {
        if (null == grid || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int rowNum = grid.length;
        int colNum = grid[0].length;
        UnionFind unionFind = new UnionFind(grid);

        // 处理第一列
        for (int i = 1; i < rowNum; i++) {
            if (grid[i][0] == 1 && grid[i - 1][0] == 1) {
                unionFind.union(i, 0, i - 1, 0);
            }
        }

        // 处理第一行
        for (int j = 1; j < colNum; j++) {
            if (grid[0][j] == 1 && grid[0][j - 1] == 1) {
                unionFind.union(0, j, 0, j - 1);
            }
        }

        for (int i = 1; i < rowNum; i++) {
            for (int j = 1; j < colNum; j++) {
                if (grid[i][j] == 1 && grid[i][j - 1] == 1) {
                    unionFind.union(i, j, i, j - 1);
                }
                if (grid[i][j] == 1 && grid[i - 1][j] == 1) {
                    unionFind.union(i, j, i - 1, j);
                }
            }
        }

        return unionFind.getMax();
    }


    private class UnionFind {
        private final int[] parent;
        private final int[] size;
        private final int[] help;
        private int max;
        private int sets;
        private final int colNum;

        public UnionFind(int[][] grid) {
            int rowNum = grid.length;
            this.colNum = grid[0].length;
            parent = new int[rowNum * colNum];
            size = new int[rowNum * colNum];
            help = new int[rowNum * colNum];

            for (int i = 0; i < rowNum; i++) {
                for (int j = 0; j < colNum; j++) {
                    if (grid[i][j] == 1) {
                        parent[getIndex(i, j)] = getIndex(i, j);
                        size[getIndex(i, j)] = 1;
                        max = 1;
                        sets++;
                    }
                }
            }
        }

        private int findFather(int index) {
            int hi = 0;
            while (parent[index] != index) {
                help[hi++] = index;
                index = parent[index];
            }
            // 注意 hi--
            for (hi--; hi >= 0; hi--) {
                parent[help[hi]] = index;
            }
            return index;
        }

        public void union(int row1, int col1, int row2, int col2) {
            int index1 = getIndex(row1, col1);
            int index2 = getIndex(row2, col2);

            int find1 = findFather(index1);
            int find2 = findFather(index2);

            if (find1 != find2) {
                if (size[find1] > size[find2]) {
                    parent[find2] = find1;
                    size[find1] += size[find2];
                    this.max = Math.max(this.max, size[find1]);
                } else {
                    parent[find1] = find2;
                    size[find2] += size[find1];
                    this.max = Math.max(this.max, size[find2]);
                }
                sets--;
            }

        }

        private int getIndex(int i, int j) {
            return i * colNum + j;
        }

        public int getMax() {
            return this.max;
        }

        public int getSets(){
            return this.sets;
        }

    }
}
