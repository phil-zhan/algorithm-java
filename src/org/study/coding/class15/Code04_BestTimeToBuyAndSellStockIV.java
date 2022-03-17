package org.study.coding.class15;

//leetcode 188

/**
 * 给定一个整数数组prices ，它的第 i 个元素prices[i] 是一支给定的股票在第 i 天的价格。
 * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 k 笔交易。
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 * 一买一卖算是一次交易
 * <p>
 * 如果 K >= N/2   .那么就等于是无限次交易。直接利用题目2的算法原型去求解
 * 因为在一个数组中。最多能有 N/2 个单调递增区间
 * 当 K < N/2 时。
 * 设置一个dp表
 * dp[i][j] 表示。只能在arr[0...i] 上做交易。交易的次数不要超过j。所获得的最大收益
 * <p>
 * dp[i][0]: 没有交易次数。都是0
 * dp[0][j]: 表示0位置上即买即卖。收益都是0
 * <p>
 * 普遍位置 dp[i][j]
 * 1) i 位置不参与交易。dp[i][j] = dp[i-1][j]
 * 2) i 位置要参与交易。【因为是在0...i位置上做交易。i位置要参与交易的话，只能是最后一次交易的卖出时机】
 * ①：i位置即买即卖。获得0的收益。dp[i][j] = dp[i][j-1] + 0
 * ②：i位置的前一个位置买。arr[i]-arr[i-1]的收益。dp[i][j] = dp[i-1][j-1] + (arr[i]-arr[i-1])
 * ③：i位置的前两个个位置买。arr[i]-arr[i-2]的收益。dp[i][j] = dp[i-2][j-1] + (arr[i]-arr[i-2])
 * ...
 * i位置的前面，任何一次都可能是买入的时机
 * 最后再这些 i 种可能中，求一个最大的。就是 dp[i][j]
 *
 *
 *
 * 有枚举行为。考虑斜率优化
 * 从左往右，从上到下填。dp[i][j] 可以加速 dp[i+1][j]
 * 见图 img_Code04.img
 *
 *
 * @since 2022-03-17 01:36:52
 */
public class Code04_BestTimeToBuyAndSellStockIV {

    public static int maxProfit(int K, int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int N = prices.length;
        if (K >= N / 2) {
            return allTrans(prices);
        }
        int[][] dp = new int[K + 1][N];
        int ans = 0;
        for (int tran = 1; tran <= K; tran++) {
            int pre = dp[tran][0];
            int best = pre - prices[0];
            for (int index = 1; index < N; index++) {
                pre = dp[tran - 1][index];
                dp[tran][index] = Math.max(dp[tran][index - 1], prices[index] + best);
                best = Math.max(best, pre - prices[index]);
                ans = Math.max(dp[tran][index], ans);
            }
        }
        return ans;
    }

    public static int allTrans(int[] prices) {
        int ans = 0;
        for (int i = 1; i < prices.length; i++) {
            ans += Math.max(prices[i] - prices[i - 1], 0);
        }
        return ans;
    }

    // 课上写的版本，对了
    public static int maxProfit2(int K, int[] arr) {
        if (arr == null || arr.length == 0 || K < 1) {
            return 0;
        }
        int N = arr.length;
        if (K >= N / 2) {
            return allTrans(arr);
        }
        int[][] dp = new int[N][K + 1];

        // 0行和第0列 都是0 。直接跳过
        // dp[...][0] = 0
        // dp[0][...] = arr[0.0] 0

        // 一列一列的填。同一列从上到下
        for (int j = 1; j <= K; j++) {

            // dp[1][j]
            int p1 = dp[0][j];
            // int p2 = dp[1][j-1] + arr[1]-arr[1];
            // int p3 = dp[0][j-1] + arr[1]-arr[0];
            // 为了方便后面的优化。将 加的位置 先剔除来

            // 这个best 就是加之前的
            int best = Math.max(dp[1][j - 1] - arr[1], dp[0][j - 1] - arr[0]);

            dp[1][j] = Math.max(p1, best + arr[1]);

            // dp[1][j] 准备好一些枚举，接下来准备好的枚举
            for (int i = 2; i < N; i++) {

                // 当前i位置不参与
                p1 = dp[i - 1][j];

                int newP = dp[i][j - 1] - arr[i];
                // 更新best
                best = Math.max(newP, best);

                dp[i][j] = Math.max(p1, best + arr[i]);
            }

        }
        return dp[N - 1][K];
    }

}
