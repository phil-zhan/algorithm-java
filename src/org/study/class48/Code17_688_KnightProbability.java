package org.study.class48;

/**
 * @author phil
 * @date 2022/2/17 9:18
 */
public class Code17_688_KnightProbability {

    public static void main(String[] args) {


        double probability = new Code17_688_KnightProbability().knightProbability(8, 30, 6, 4);
        System.out.println(probability);
    }

    public double knightProbability(int n, int k, int row, int column) {

        int live = process(row, column, k, n);
        return (double) live / Math.pow(8, k);
    }

    private int process(int row, int col, int restTimes, int n) {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            return 0;
        }

        if (restTimes == 0) {
            return 1;
        }


        int ways = 0;
        ways += process(row - 2, col + 1, restTimes - 1, n);
        ways += process(row - 1, col + 2, restTimes - 1, n);
        ways += process(row + 1, col + 2, restTimes - 1, n);
        ways += process(row + 2, col + 1, restTimes - 1, n);

        ways += process(row + 2, col - 1, restTimes - 1, n);
        ways += process(row + 1, col - 2, restTimes - 1, n);
        ways += process(row - 1, col - 2, restTimes - 1, n);
        ways += process(row - 2, col - 1, restTimes - 1, n);

        return ways;
    }


    // 动态规划
    public double knightProbability2(int n, int k, int row, int column) {
        if (row < 0 || row >= n || column < 0 || column >= n) {
            return 0;
        }

        // [restTimes][row][col]
        long[][][] dp = new long[k + 1][n][n];

        for (int row1 = 0; row1 < n; row1++) {
            for (int col1 = 0; col1 < n; col1++) {
                dp[0][row1][col1] = 1;
            }
        }

        for (int restTimes = 1; restTimes <= k; restTimes++) {
            for (int row1 = 0; row1 < n; row1++) {
                for (int col1 = 0; col1 < n; col1++) {


                    long ways = 0;
                    ways += pick(dp, restTimes - 1, row1 - 2, col1 + 1, n);
                    ways += pick(dp, restTimes - 1, row1 - 1, col1 + 2, n);
                    ways += pick(dp, restTimes - 1, row1 + 1, col1 + 2, n);
                    ways += pick(dp, restTimes - 1, row1 + 2, col1 + 1, n);

                    ways += pick(dp, restTimes - 1, row1 + 2, col1 - 1, n);
                    ways += pick(dp, restTimes - 1, row1 + 1, col1 - 2, n);
                    ways += pick(dp, restTimes - 1, row1 - 1, col1 - 2, n);
                    ways += pick(dp, restTimes - 1, row1 - 2, col1 - 1, n);


                    dp[restTimes][row1][col1] = ways;
                }
            }
        }

        long live = dp[k][row][column];
        double total = Math.pow(8, k) ;
        return live / total;
    }

    public long pick(long[][][] dp, int restTimes, int row, int col, int n) {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            return 0;
        }
        return dp[restTimes][row][col];
    }




    public static double knightProbability3(int n, int k, int row, int col) {
        double[][][] dp = new double[n][n][k + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int t = 0; t <= k; t++) {
                    dp[i][j][t] = -1;
                }
            }
        }
        return process(row, col, n, k, dp);
    }

    public static int[] next = { -1, -2, -2, -1, -2, 1, -1, 2, 1, -2, 2, -1, 2, 1, 1, 2 };

    public static double process(int row, int col, int n, int k, double[][][] dp) {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            return 0;
        }
        if (dp[row][col][k] != -1) {
            return dp[row][col][k];
        }
        double ans = 0;
        if (k == 0) {
            ans = 1;
        } else {
            for (int i = 0; i < 16; i += 2) {
                ans += process(row + next[i], col + next[i + 1], n, k - 1, dp) / 8;
            }
        }
        dp[row][col][k] = ans;
        return ans;
    }

}
