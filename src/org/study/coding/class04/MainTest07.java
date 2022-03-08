package org.study.coding.class04;

public class MainTest07 {

    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1 == null || s2 == null || s3 == null) {
            return false;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        char[] str3 = s3.toCharArray();

        if (str1.length + str2.length != str3.length) {
            return false;
        }

        // init
        boolean[][] dp = new boolean[str1.length + 1][str2.length + 1];
        dp[0][0] = true;

        // col 1
        for (int row = 1; row <= str1.length; row++) {
            if (str1[row - 1] != str3[row - 1]) {
                break;
            }
            dp[row][0] = true;
        }

        // row 1
        for (int col = 1; col <= str2.length; col++) {
            if (str2[col - 1] != str3[col - 1]) {
                break;
            }
            dp[0][col] = true;
        }

        // common
        for (int row = 1; row <= str1.length; row++) {
            for (int col = 1; col <= str2.length; col++) {
                if ((str1[row - 1] == str3[row + col - 1] && dp[row - 1][col]) || (str2[col - 1] == str3[row + col - 1] && dp[row][col - 1])) {
                    dp[row][col] = true;
                }
            }
        }

        return dp[str1.length][str2.length];
    }
}
