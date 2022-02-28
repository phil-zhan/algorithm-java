package org.study.system.class48;

/**
 * @author phil
 * @date 2022/2/16 13:33
 */
public class Code13_322_CoinChange {
    public static void main(String[] args) {
        int min = new Code13_322_CoinChange().coinChange(new int[]{1, 2, 5}, 11);
        System.out.println(min);
    }

    public int coinChange(int[] coins, int amount) {
        return process(0, amount, coins);
    }

    /**
     * 要的是最少张数
     *
     * @since 2022-02-16 14:21:01
     */
    public int process(int index, int rest, int[] coins) {
        if (index == coins.length) {
            return rest == 0 ? 0 : Integer.MAX_VALUE;
        }

        int min = Integer.MAX_VALUE;
        for (int zhang = 0; zhang * coins[index] <= rest; zhang++) {
            int next = process(index + 1, rest - zhang * coins[index], coins);
            if (next != Integer.MAX_VALUE) {
                min = Math.min(min, next + zhang);
            }
        }
        return min;
    }


    /**
     * 动态规划
     *
     * @since 2022-02-16 14:21:17
     */
    public int coinChange2(int[] coins, int amount) {
        if (coins == null || coins.length == 0 || amount < 0) {
            return -1;
        }
        int len = coins.length;
        int[][] dp = new int[len + 1][amount + 1];
        dp[len][0] = 0;
        for (int rest = 1; rest <= amount; rest++) {
            dp[len][rest] = Integer.MAX_VALUE;
        }

        for (int index = len - 1; index >= 0; index--) {
            for (int rest = 0; rest <= amount; rest++) {
                int min = Integer.MAX_VALUE;
                // 未优化
                for (int zhang = 0; zhang * coins[index] <= rest; zhang++) {
                    int next = dp[index + 1][rest - zhang * coins[index]];
                    if (next != Integer.MAX_VALUE) {
                        min = Math.min(min, next + zhang);
                    }
                }

            }
        }
        if (dp[0][amount] == Integer.MAX_VALUE) {
            return -1;
        }
        return dp[0][amount];
    }

    /**
     * 动态规划  优化版本
     *
     * @since 2022-02-16 14:21:27
     */
    public int coinChange3(int[] coins, int amount) {
        if (coins == null || coins.length == 0 || amount < 0) {
            return -1;
        }
        int len = coins.length;
        int[][] dp = new int[len + 1][amount + 1];
        dp[len][0] = 0;
        for (int rest = 1; rest <= amount; rest++) {
            dp[len][rest] = Integer.MAX_VALUE;
        }

        for (int index = len - 1; index >= 0; index--) {
            for (int rest = 0; rest <= amount; rest++) {

                dp[index][rest] = dp[index + 1][rest];
                if (rest - coins[index] >= 0) {
                    int post = dp[index][rest - coins[index]] == Integer.MAX_VALUE ? dp[index][rest - coins[index]] : dp[index][rest - coins[index]] + 1;
                    dp[index][rest] = Math.min(dp[index][rest], post);
                }
            }
        }
        if (dp[0][amount] == Integer.MAX_VALUE) {
            return -1;
        }
        return dp[0][amount];
    }
}
