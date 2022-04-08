package org.study.coding.class10;

/**
 * @author phil
 * @date 2022-04-07 17:21:41
 */
public class MainTest03 {
    public static int kInversePairs1(int num, int k) {
        if (num < 1 || k < 0) {
            return 0;
        }
        int[][] dp = new int[num + 1][k + 1];
        dp[0][0] = 1;
        int mod = 1000000007;
        for (int i = 1; i <= num; i++) {
            dp[i][0] = 1;
            for (int j = 1; j <= k; j++) {
                dp[i][j] = (dp[i - 1][j] + dp[i][j - 1]) % mod;
                if (j >= i) {
                    dp[i][j] = (dp[i][j] - dp[i - 1][j - i] + mod) % mod;
                }
            }
        }

        return dp[num][k];
    }


    public static int kInversePairs2(int num, int k) {
        if (num < 1 || k < 0) {
            return 0;
        }
        int[][] dp = new int[num + 1][k + 1];
        dp[0][0] = 1;
        for (int row = 1; row <= num; row++) {
            dp[row][0] = 1;
            for (int col = 1; col <= k; col++) {
                dp[row][col] = dp[row - 1][col] + dp[row][col - 1];
                if (col >= row) {
                    dp[row][col] -= dp[row - 1][col - row];
                }
            }
        }

        return dp[num][k];
    }
}
