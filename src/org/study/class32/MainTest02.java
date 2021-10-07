package org.study.class32;

/**
 * @author phil
 * @date 2021/10/6 9:30
 */
public class MainTest02 {

    public static class IndexTree2D {
        private final int[][] tree;
        private final int[][] nums;
        private final int rows;
        private final int cols;

        public IndexTree2D(int[][] matrix) {


            this.rows = matrix.length;
            this.cols = matrix[0].length;


            this.nums = new int[rows][cols];
            this.tree = new int[rows+1][cols+1];


            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    update(i, j, matrix[i][j]);
                }
            }

        }

        public void update(int row, int col, int value) {
            if (row >= rows || col >= cols || row < 0 || col < 0) {
                return;
            }

            int add = value - nums[row][col];

            nums[row][col] = value;

            for (int i = row + 1; i <= rows; i += (i & -i)) {
                for (int j = col + 1; j <= cols; j += (j & -j)) {
                    tree[i][j] += add;
                }
            }
        }

        public int sum(int row, int col) {
            int answer = 0;
            for (int i = row + 1; i > 0; i -= (i & -i)) {
                for (int j = col + 1; j > 0; j -= (j & -j)) {
                    answer += tree[i][j];
                }
            }

            return answer;

        }
    }

    public static void main(String[] args) {
        int[][] test = new int[][]{
                {1,2,3,4,5,6},
                {1,2,3,4,5,6},
                {1,2,3,4,5,6},
                {1,2,3,4,5,6},
                {1,2,3,4,5,6}
        };

        IndexTree2D indexTree2D = new IndexTree2D(test);

        System.out.println(indexTree2D.sum(4,5));

        int asn = indexTree2D.sum(4,5) -indexTree2D.sum(2,5) - indexTree2D.sum(4,3)+indexTree2D.sum(2,3);

        System.out.println(asn);
    }
}
