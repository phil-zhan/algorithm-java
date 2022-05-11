package org.study.coding.class15;

public class MainTest04 {

    public static void main(String[] args) {
        System.out.println(new MainTest04().maxProfit(new int[]{3, 2, 6, 5, 0, 3}, 2));
    }

    public int maxProfit(int[] prices, int k) {
        if (null == prices || prices.length == 0) {
            return 0;
        }
        if (k >= prices.length >> 1) {
            int ans = 0;
            for (int i = 1; i < prices.length; i++) {
                ans += Math.max(prices[i] - prices[i - 1], 0);
            }
            return ans;
        }

        int[][] dp = new int[prices.length][k + 1];
        // 状态转移
        for (int j = 1; j <= k; j++) {
            int p1 = dp[0][j];
            int best = Math.max(dp[0][j - 1] - prices[0], dp[1][j - 1] - prices[1]);
            dp[1][j] = Math.max(p1, best + prices[1]);
            for (int i = 2; i < prices.length; i++) {
                p1 = dp[i - 1][j];
                best = Math.max(best, dp[i][j - 1] - prices[i]);
                dp[i][j] = Math.max(p1, best + prices[i]);
            }
        }
        return dp[prices.length - 1][k];
    }
}
