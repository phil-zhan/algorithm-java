package org.study.coding.class04;

// 本题测试链接 : https://leetcode.com/problems/interleaving-string/
public class Code07_InterleavingString {

    /**
     * 动态规划【样本对应】
     * s1只拿前 i 个字符
     * s2只拿前 j 个字符
     * 能否交错组成s3的前（i + j）
     *
     * 如果s3 的最后一个字符来自 s1.那么s1的前面 i-1 个字符和 s2 的j个字符就要能组成s3的前面 i+j-1
     * 如果s3 的最后一个字符来自 s2.那么s2的前面 j-1 个字符和 s1 的i个字符就要能组成s3的前面 i+j-1
     * 两种情况取 或  。就是任意一个位置的情况。
     *
     * 样本对应模型【体现在二维表上，就是一个样本是行，另外一个样本是列】
     * 也就是两个样本都可变
     * @since 2022-03-04 07:43:13
     */
    public static boolean isInterleave(String s1, String s2, String s3) {
        if (s1 == null || s2 == null || s3 == null) {
            return false;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        char[] str3 = s3.toCharArray();
        if (str3.length != str1.length + str2.length) {
            return false;
        }
        boolean[][] dp = new boolean[str1.length + 1][str2.length + 1];
        dp[0][0] = true;

        // 第 0 行和第0列单独考虑
        // 其含义是，不用s1或者不用s2，能否搞定s3
        // 只要其中一个字符对不上，后面的就都不会对上
        for (int i = 1; i <= str1.length; i++) {
            if (str1[i - 1] != str3[i - 1]) {
                break;
            }
            dp[i][0] = true;
        }
        for (int j = 1; j <= str2.length; j++) {
            if (str2[j - 1] != str3[j - 1]) {
                break;
            }
            dp[0][j] = true;
        }
        for (int i = 1; i <= str1.length; i++) {
            for (int j = 1; j <= str2.length; j++) {

                //     * 如果s3 的最后一个字符来自 s1.那么s1的前面 i-1 个字符和 s2 的j个字符就要能组成s3的前面 i+j-1
                //     * 如果s3 的最后一个字符来自 s2.那么s2的前面 j-1 个字符和 s1 的i个字符就要能组成s3的前面 i+j-1
                //     * 两种情况取 或  。就是任意一个位置的情况。
                if ((str1[i - 1] == str3[i + j - 1] && dp[i - 1][j]) || (str2[j - 1] == str3[i + j - 1] && dp[i][j - 1])) {

                    dp[i][j] = true;
                }
            }
        }
        return dp[str1.length][str2.length];
    }

}
