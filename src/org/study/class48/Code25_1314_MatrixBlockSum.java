package org.study.class48;

import java.util.Arrays;

/**
 * @author phil
 * @date 2022/2/21 16:33
 */
public class Code25_1314_MatrixBlockSum {
    public static void main(String[] args) {
        IndexTree indexTree = new IndexTree(10);
        for (int i = 0; i < 10; i++) {
            indexTree.add(i + 1, i);
        }

        System.out.println(indexTree.sum(4));

        IndexTree2D indexTree2D = new IndexTree2D(new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });
        int sum1 = indexTree2D.sum(3, 3);
        int[][] sum2 = indexTree2D.sum(2);
        System.out.println(Arrays.deepToString(sum2));

    }

    public static class IndexTree2D {
        public int[][] tree;
        public int rowNum;
        public int colNum;

        public IndexTree2D(int[][] mat) {
            this.rowNum = mat.length;
            this.colNum = mat[0].length;
            this.tree = new int[rowNum + 1][colNum + 1];

            for (int row = 0; row < rowNum; row++) {
                for (int col = 0; col < colNum; col++) {
                    add(row + 1, col + 1, mat[row][col]);
                }
            }
        }
        public void add(int row, int col, int val) {
            for (int i = row; i <= rowNum; i += i & (-i)) {
                for (int j = col; j <= colNum; j += j & (-j)) {
                    tree[i][j] += val;
                }
            }
        }

        public int sum(int row, int col) {
            int ans = 0;
            for (int i = row; i > 0; i -= i & (-i)) {
                for (int j = col; j > 0; j -= j & (-j)) {
                    ans += tree[i][j];
                }
            }

            return ans;
        }

        public int[][] sum(int k) {
            int[][] res = new int[this.rowNum][this.colNum];
            for (int i = 0; i < this.rowNum; i++) {
                for (int j = 0; j < this.colNum; j++) {

                    // sum[i+k,j+k] -sum[i-k-1,j+K] - sum[i+k,j-k-1] + sum[i-k-1,j-k-1]
                    res[i][j] = sum(Math.min(i + k + 1, rowNum), Math.min(j + k + 1,colNum));
                    if(i-k>=1){
                        res[i][j] -= sum(i - k, Math.min(j + k + 1,colNum));
                    }
                    if(j-k>=1){
                        res[i][j] -= sum(Math.min(i + k + 1, rowNum), j - k);
                    }
                    if(i - k>=1 && j - k>=1){
                        res[i][j] += sum(i - k, j - k);
                    }
                }
            }
            return res;
        }
    }

    public static class IndexTree {
        public int[] tree;
        public int len;

        public IndexTree(int size) {
            this.tree = new int[size + 1];
            this.len = size;
        }

        public void add(int index, int val) {
            while (index <= len) {
                tree[index] += val;
                index += index & (-index);
            }
        }

        public int sum(int index) {
            int ans = 0;
            while (index > 0) {
                ans += tree[index];
                index -= index & (-index);
            }
            return ans;
        }
    }
}
