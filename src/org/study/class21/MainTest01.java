package org.study.class21;

/**
 * @author phil
 * @date 2021/8/6 16:43
 */
public class MainTest01 {

    public static int minPathSum1(int[][] checkerboard) {
        if (null == checkerboard || checkerboard.length == 0) {
            return 0;
        }
        return process(checkerboard, 0, 0, checkerboard.length - 1, checkerboard[0].length - 1);
    }

    private static int process(int[][] checkerboard, int curRow, int curCol, int tarRow, int tarCol) {
        if (curRow < 0 || curCol < 0 || curRow >= checkerboard.length || curCol >= checkerboard[0].length) {
            return Integer.MAX_VALUE;
        }
        if (curRow == tarRow && curCol == tarRow) {
            return checkerboard[curRow][curCol];
        }

        // 向右
        int right = process(checkerboard, curRow, curCol + 1, tarRow, tarCol);
        // 向下
        int down = process(checkerboard, curRow + 1, curCol, tarRow, tarCol);

        return Math.min(right, down) + checkerboard[curRow][curCol];

    }

    public static int minPathSum2(int[][] arr) {

        if (null == arr || arr.length == 0) {
            return 0;
        }

        int row = arr.length;
        int col = arr[0].length;
        int[][] dp = new int[row][col];
        dp[0][0] = arr[0][0];
        for (int i = 1; i < col; i++) {
            dp[0][i] = dp[0][i - 1] + arr[0][i];
        }

        for (int j = 1; j < row; j++) {
            dp[j][0] = dp[j - 1][0] + arr[j][0];
        }

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + arr[i][j];
            }
        }
        return dp[row - 1][col - 1];
    }

    public static int minPathSum3(int[][] arr) {
        if (null == arr || arr.length == 0) {
            return 0;
        }
        int row = arr.length;
        int col = arr[0].length;
        int[] dp = new int[col];
        dp[0] = arr[0][0];
        for (int k = 1; k < col; k++) {
            dp[k] = dp[k - 1] + arr[0][k];
        }

        for (int i = 1; i < row; i++) {
            for (int j = 0; j < col; j++) {
                dp[j] = j == 0 ? dp[j] + arr[i][j] : Math.min(dp[j], dp[j - 1]) + arr[i][j];
            }
        }

        return dp[col - 1];
    }


    public static int minPathSum4(int[][] arr) {
        if (null == arr || arr.length == 0) {
            return 0;
        }
        int row = arr.length;
        int col = arr[0].length;
        int[] dp = new int[row];
        dp[0] = arr[0][0];

        for (int i = 1; i < row; i++) {
            dp[i] = dp[i - 1] + arr[i][0];
        }

        for (int j = 1; j < col; j++) {
            for (int i = 0; i < row; i++) {
                dp[i] = i == 0 ? dp[i] + arr[i][j] : Math.min(dp[i - 1], dp[i]) + arr[i][j];
            }
        }


        return dp[row - 1];
    }

    public static void main(String[] args) {
        int rowSize = 10;
        int colSize = 10;
        int[][] m = Code01_MinPathSum.generateRandomMatrix(rowSize, colSize);
        // 递归实现
        System.out.println(minPathSum1(m));

        // 二维表实现
        System.out.println(minPathSum2(m));

        // 一维数组竖着滚
        System.out.println(minPathSum3(m));

        // 一维数组横着滚
        System.out.println(minPathSum4(m));

    }
}
