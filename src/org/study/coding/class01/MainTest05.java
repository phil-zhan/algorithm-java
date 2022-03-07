package org.study.coding.class01;

public class MainTest05 {

    public int longestIncreasingPath1(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }
        int ans = 0;
        int[][] dp = new int[matrix.length][matrix[0].length];
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                ans = Math.max(ans, process(row, col, matrix,dp));
            }
        }

        return ans;
    }


    public int process(int row, int col, int[][] matrix,int[][] dp) {
        if (dp[row][col] != 0){
            return dp[row][col];
        }

        int up = row > 0 && matrix[row - 1][col] > matrix[row][col] ? process(row - 1, col, matrix,dp) : 0;
        int down = row < matrix.length - 1 && matrix[row + 1][col] > matrix[row][col] ? process(row + 1, col, matrix,dp) : 0;
        int left = col > 0 && matrix[row][col - 1] > matrix[row][col] ? process(row, col - 1, matrix,dp) : 0;
        int right = col < matrix[0].length - 1 && matrix[row][col + 1] > matrix[row][col] ? process(row, col + 1, matrix,dp) : 0;

        dp[row][col] = Math.max(Math.max(up, down), Math.max(left, right)) + 1;
        return dp[row][col];
    }


}
