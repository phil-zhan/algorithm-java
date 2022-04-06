package org.study.coding.class08;

import java.util.Arrays;

/**
 * @author phil
 * @date 2022-04-05 11:47:05
 */
public class MainTest04 {

    public static int walk1(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                int[] ans = process(matrix, row, col);
                max = Math.max(max, Math.max(ans[0], ans[1]));
            }
        }
        return max;
    }

    public static int[] process(int[][] matrix, int row, int col) {
        if (col == 0) {
            return new int[]{matrix[row][col], -matrix[row][col]};
        }

        // left
        int[] leftAns = process(matrix, row, col - 1);
        int preUnUse = leftAns[0];
        int preUse = leftAns[1];

        // leftUp
        if (row > 0) {
            leftAns = process(matrix, row - 1, col - 1);
            preUnUse = Math.max(preUnUse, leftAns[0]);
            preUse = Math.max(preUse, leftAns[1]);

        }

        // leftDown
        if (row < matrix.length - 1) {
            leftAns = process(matrix, row + 1, col - 1);
            preUnUse = Math.max(preUnUse, leftAns[0]);
            preUse = Math.max(preUse, leftAns[1]);
        }

        int yes = -1;
        int no = -1;

        if (preUnUse >= 0) {
            no = preUnUse + matrix[row][col];
            yes = preUnUse - matrix[row][col];
        }

        if (preUse >= 0) {
            yes = Math.max(preUse + matrix[row][col], yes);
        }

        return new int[]{no, yes};
    }

    public static int walk2(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }
        int rowNum = matrix.length;
        int colNum = matrix[0].length;

        int max = Integer.MIN_VALUE;
        int[][][] dp = new int[rowNum][colNum][2];
        for (int row = 0; row < rowNum; row++) {
            dp[row][0][0] = matrix[row][0];
            dp[row][0][1] = -matrix[row][0];

            max = Math.max(max, Math.max(dp[row][0][0], dp[row][0][1]));
        }

        for (int col = 1; col < colNum; col++) {
            for (int row = 0; row < rowNum; row++) {

                // left
                int preUnUse = dp[row][col - 1][0];
                int preUse = dp[row][col - 1][1];

                // leftUp
                if (row > 0) {
                    preUnUse = Math.max(preUnUse, dp[row - 1][col - 1][0]);
                    preUse = Math.max(preUse, dp[row - 1][col - 1][1]);

                }

                // leftDown
                if (row < matrix.length - 1) {
                    preUnUse = Math.max(preUnUse, dp[row + 1][col - 1][0]);
                    preUse = Math.max(preUse, dp[row + 1][col - 1][1]);
                }

                dp[row][col][0] = -1;
                dp[row][col][1] = -1;
                if (preUnUse >= 0) {
                    dp[row][col][0] = preUnUse + matrix[row][col];
                    dp[row][col][1] = preUnUse - matrix[row][col];
                }
                if (preUse >= 0) {
                    dp[row][col][1] = Math.max(preUse + matrix[row][col], dp[row][col][1]);
                }
                max = Math.max(max, Math.max(dp[row][col][0], dp[row][col][1]));
            }
        }

        return max;
    }


    public static void main(String[] args) {
        int N = 7;
        int M = 7;
        int V = 10;
        int times = 1000000;
        for (int i = 0; i < times; i++) {
            int r = (int) (Math.random() * (N + 1));
            int c = (int) (Math.random() * (M + 1));
            int[][] matrix = Code04_SnakeGame.generateRandomArray(r, c, V);
            int ans1 = walk1(matrix);
            int ans2 = walk2(matrix);
            if (ans1 != ans2) {
                for (int j = 0; j < matrix.length; j++) {
                    System.out.println(Arrays.toString(matrix[j]));
                }
                System.out.println("Oops   ans1: " + ans1 + "   ans2:" + ans2);
                break;
            }
        }
        System.out.println("finish");
    }
}
