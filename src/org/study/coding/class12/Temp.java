package org.study.coding.class12;

/**
 * @author phil
 * @since 2022-04-18 22:14:22
 */
public class Temp {

    public static void main(String[] args) {

        System.out.println(new Temp().isMatch("aa", "*"));


        // 考虑 0...i  能不能被 0...j 搞定
    }

    public boolean isMatch(String str, String exp) {

        //
        if (str == null || exp == null) {
            return false;
        }
        char[] s = str.toCharArray();
        char[] e = exp.toCharArray();
        int[][] dp = new int[s.length+1][e.length+1];

        return process(s, e, 0, 0,dp) == 1;
    }

    public int process(char[] s, char[] e, int si, int ei, int[][] dp) {
        if (dp[si][ei] != 0) {
            return dp[si][ei];
        }
        if (ei == e.length) {
            dp[si][ei] = si == s.length ? 1 : -1;
            return dp[si][ei];
        }

        // 当前能搞定
        if (si != s.length && (e[ei] == s[si] || e[ei] == '?')) {

            dp[si][ei] = process(s, e, si + 1, ei + 1, dp);
            return dp[si][ei];
        }
        if (e[ei] == '*') {
            while (si != s.length + 1) {

                if (process(s, e, si, ei + 1, dp) == 1) {
                    dp[si][ei] =1;
                    return dp[si][ei];
                }
                si++;
            }
        }

        dp[si][ei] =-1;
        return dp[si][ei];
    }

    public static boolean isValid(char[] s, char[] e) {
        for (char c : s) {
            if (c == '*' || c == '?') {
                return false;
            }
        }
        for (int i = 0; i < e.length; i++) {
            if (e[i] == '*' && (i == 0 || e[i - 1] == '*')) {
                return false;
            }
        }
        return true;
    }
}
