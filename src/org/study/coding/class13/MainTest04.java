package org.study.coding.class13;

public class MainTest04 {

    public int[] hitBricks(int[][] matrix, int[][] hits) {
        for (int i = 0; i < hits.length; i++) {
            if (matrix[hits[i][0]][hits[i][1]] == 1) {
                matrix[hits[i][0]][hits[i][1]] = 2;
            }
        }
        UnionFind unionFind = new UnionFind(matrix);
        int[] ans = new int[hits.length];
        for (int i = 0; i < hits.length; i++) {
            if (matrix[hits[i][0]][hits[i][1]] == 2) {
                ans[i] = unionFind.finger(hits[i][0], hits[i][1]);
            }
        }


        return ans;
    }

    public static class UnionFind {
        private int[] father;
        private int[] size;
        private int[] stack;
        private int rowNum;
        private int colNum;

        private int[][] grid;

        private int cellingAll;

        private boolean[] cellingSet;

        public UnionFind(int[][] grid) {

            initSpace(grid);
            initConnect();
        }


        private void initSpace(int[][] grid) {
            this.grid = grid;
            this.rowNum = grid.length;
            this.colNum = grid[0].length;
            int allNum = rowNum * colNum;

            this.father = new int[allNum];
            this.size = new int[allNum];
            this.stack = new int[allNum];
            this.cellingAll = 0;
            this.cellingSet = new boolean[allNum];

            for (int i = 0; i < rowNum; i++) {
                for (int j = 0; j < colNum; j++) {
                    if (grid[i][j] == 1) {
                        int index = i * colNum + j;
                        father[index] = index;
                        size[index] = 1;

                        if (j == 0) {
                            cellingAll++;
                            cellingSet[index] = true;
                        }
                    }
                }
            }
        }

        public void initConnect() {
            for (int i = 0; i < rowNum; i++) {
                for (int j = 0; j < colNum; j++) {
                    union(i, j, i + 1, j);
                    union(i, j, i - 1, j);
                    union(i, j, i, j + 1);
                    union(i, j, i, j - 1);
                }
            }

        }


        public void union(int row1, int col1, int row2, int col2) {
            if (valid(row1, col1) && valid(row2, col2)) {
                int father1 = find(row1, col1);
                int father2 = find(row2, col2);
                if (father1 != father2) {

                    boolean status1 = cellingSet[father1];
                    boolean status2 = cellingSet[father2];

                    if (size[father1] >= size[father2]) {
                        father[father2] = father1;
                        size[father1] += size[father2];

                        if (status1 ^ status2) {
                            cellingSet[father1] = true;
                            cellingAll += status1 ? size[father2] : size[father1];
                        }
                    } else {
                        father[father1] = father2;
                        size[father2] += size[father1];

                        if (status1 ^ status2) {
                            cellingSet[father2] = true;
                            cellingAll += status1 ? size[father2] : size[father1];
                        }

                    }
                }
            }
        }

        public int find(int row, int col) {
            int index = row * colNum + col;
            int si = 0;
            while (father[index] != index) {
                stack[si++] = index;
                index = father[index];
            }

            while (si != 0) {
                father[--si] = index;
            }

            return index;
        }


        public boolean valid(int row, int col) {

            return row >= 0 && row < rowNum && col >= 0 && col < colNum && grid[row][col] == 1;
        }

        public int finger(int row, int col) {
            grid[row][col] = 1;
            int index = row * colNum + col;

            if (row == 0) {
                cellingSet[index] = true;
                cellingAll++;
            }

            father[index] = index;
            size[index] = 1;

            int pre = cellingAll;
            union(row, col, row - 1, col);
            union(row, col, row + 1, col);
            union(row, col, row, col - 1);
            union(row, col, row, col + 1);

            int now = cellingAll;
            if (row == 0) {
                return now - pre;
            } else {
                return now == pre ? 0 : now - pre - 1;
            }
        }
    }
}
