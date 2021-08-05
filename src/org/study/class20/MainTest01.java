package org.study.class20;

/**
 * @author phil
 * @date 2021/8/4 16:27
 */
public class MainTest01 {

    private static int lpsl1(String str) {
        if (null == str || str.length() == 0) {
            return 0;
        }

        return process(str.toCharArray(), 0, str.length() - 1);
    }

    private static int process(char[] chars, int left, int right) {

        if (left == right) {
            return 1;
        }

        if (left == right - 1) {
            return chars[left] != chars[right] ? 0 : 2;
        }

        // 包含左边,不包含右边
        int p1 = process(chars, left, right - 1);

        // 包含左边，包含右边
        int p2 = chars[left] == chars[right] ? 2 + process(chars, left + 1, right - 1) : 0;

        // 不包含左边,不包含右边
        int p3 = process(chars, left + 1, right - 1);

        // 不包含左边，包含右边
        int p4 = process(chars, left + 1, right);

        return Math.max(Math.max(p1, p2), Math.max(p3, p4));
    }


    public static int lpsl2(String str) {
        if (null == str || str.length() == 0) {
            return 0;
        }

        int n = str.length();
        char[] chars = str.toCharArray();
        int[][] dp = new int[n][n];

        dp[n - 1][n - 1] = 1;
        for (int i = 0; i < n - 1; i++) {
            dp[i][i] = 1;
            dp[i][i + 1] = chars[i] == chars[i + 1] ? 2 : 1;
        }

        for (int L = n - 3; L >= 0; L--) {
            for (int R = L + 2; R < n; R++) {
                // 优化后
                // 情况二和情况三 PK
                dp[L][R] = Math.max(dp[L][R - 1], dp[L + 1][R]);
                if (chars[L] == chars[R]) {
                    // 可能有 情况四
                    dp[L][R] = Math.max(dp[L][R], 2 + dp[L + 1][R - 1]);

                }
				/*
				// 情况一：最长回文子序列，既不以 L 开头 ，也不以R 结尾
				int p1 = dp[L + 1][ R - 1];

				// 情况二：最长回文子序列，以 L 开头 ，但不以R 结尾
				int p2 = dp[ L][R - 1];

				// 情况三：最长回文子序列，不以 L 开头 ，但以R 结尾
				int p3 = dp[L + 1][ R];

				// 情况四：最长回文子序列，既以 L 开头 ，也以R 结尾
				int p4 = str[L] != str[R] ? 0 : (2 + dp[L + 1][R - 1]);
				dp[L][R] = Math.max(Math.max(p1, p2), Math.max(p3, p4));
				*/
            }
        }


        return dp[0][n - 1];
    }
}
