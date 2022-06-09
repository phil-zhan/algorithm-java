package org.study.coding.class37;

public class MainTest04 {

    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }
        int rowNum = matrix.length;
        int colNum = matrix[0].length;

        int[][] dp = new int[rowNum][colNum];
        int max = 0;

        // row = 0
        for (int col = 0; col < colNum; col++) {
            if (matrix[0][col] == '1') {
                dp[0][col] = 1;
                max = 1;
            }
        }

        // col = 0
        for (int row = 1; row < rowNum; row++) {
            if (matrix[row][0] == '1') {
                dp[row][0] = 1;
                max = 1;
            }
        }

        // common
        for (int row = 1; row < rowNum; row++) {
            for (int col = 1; col < colNum; col++) {
                if (matrix[row][col] == '1') {
                    dp[row][col] = Math.min(
                            Math.min(dp[row - 1][col], dp[row][col-1]),
                            dp[row - 1][col - 1])

                            + 1;
                    max = Math.max(max,dp[row][col]);
                }
            }
        }

        return max * max;
    }
}
