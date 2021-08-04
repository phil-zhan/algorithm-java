package org.study.class19;

/**
 *
 * 背包问题
 *
 * @author phil
 * @date 2021/7/28 18:53
 */
public class MainTest01 {

    private static int maxValue(int[] weights, int[] values, int bag) {
        if (null == weights
                || weights.length == 0
                || null == values
                || weights.length != values.length
                || bag <= 0
        ) {
            return 0;
        }

        return process(weights, values, 0, bag);
    }

    private static int process(int[] weights, int[] values, int curIndex, int rest) {
        if (rest < 0) {
            return -1;
        }

        if (curIndex == values.length) {
            return 0;
        }

        // 不要当前货物
        int p1 = process(weights, values, curIndex + 1, rest);

        // 要当前货物
        int p2 = 0;
        int next = process(weights, values, curIndex + 1, rest - weights[curIndex]);

        if (next != -1) {
            p2 = values[curIndex] + next;
        }

        return Math.max(p1, p2);
    }

    private static int dp(int[] weights, int[] values, int bag) {
        if (null == weights
                || null == values
                || weights.length == 0
                || values.length != weights.length
                || bag <= 0
        ) {
            return 0;
        }
        int n = weights.length;

        int[][] dp = new int[n + 1][bag + 1];

        for (int index = n - 1; index >= 0; index--) {
            for (int rest = 0; rest <= bag; rest++) {

                int p1 = dp[index + 1][rest];
                int p2 = 0;
                int next = rest - weights[index] < 0 ? -1 : dp[index + 1][rest - weights[index]];
                if (next != -1) {
                    p2 = values[index] + next;
                }

                dp[index][rest] = Math.max(p1, p2);

            }
        }

        return dp[0][bag];
    }

    public static void main(String[] args) {
        int[] weights = {3, 2, 4, 7, 3, 1, 7};
        int[] values = {5, 6, 3, 19, 12, 4, 2};
        int bag = 15;
        System.out.println(maxValue(weights, values, bag));
        System.out.println(dp(weights, values, bag));
    }
}
