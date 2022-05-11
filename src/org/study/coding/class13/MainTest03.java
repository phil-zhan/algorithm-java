package org.study.coding.class13;

import java.util.HashMap;

/**
 * @author phil
 * @since 2022-05-03 20:48:11
 */
public class MainTest03 {
    public static void main(String[] args) {
        System.out.println(new MainTest03().isScramble0("great", "rgeat"));
        System.out.println(new MainTest03().isScramble0("abcde", "caebd"));
        System.out.println(new MainTest03().isScramble0("a", "a"));
        System.out.println(new MainTest03().isScramble1("great", "rgeat"));
        System.out.println(new MainTest03().isScramble1("abcde", "caebd"));
        System.out.println(new MainTest03().isScramble1("a", "a"));
        System.out.println(new MainTest03().isScramble2("great", "rgeat"));
        System.out.println(new MainTest03().isScramble2("abcde", "caebd"));
        System.out.println(new MainTest03().isScramble2("a", "a"));
        System.out.println(new MainTest03().isScramble3("great", "rgeat"));
        System.out.println(new MainTest03().isScramble3("abcde", "caebd"));
        System.out.println(new MainTest03().isScramble3("a", "a"));
    }

    public boolean isScramble0(String s1, String s2) {
        if (s1 == null && s2 == null) {
            return true;
        }

        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }
        if (s1.equals(s2)) {
            return true;
        }

        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();

        if (!sameTypeSameNumber(str1, str2)) {
            return false;
        }

        return process0(str1, 0, str1.length - 1, str2, 0, str2.length - 1);

    }

    public boolean process0(char[] str1, int start1, int end1, char[] str2, int start2, int end2) {
        if (start1 == end1) {
            return str1[start1] == str2[start2];
        }

        for (int leftEnd = start1; leftEnd < end1; leftEnd++) {
            boolean p1 = process0(str1, start1, leftEnd, str2, start2, start2 + leftEnd - start1) &&
                    process0(str1, leftEnd + 1, end1, str2, start2 + leftEnd - start1 + 1, end2);

            boolean p2 = process0(str1, start1, leftEnd, str2, end2 - (leftEnd - start1), end2) &&
                    process0(str1, leftEnd + 1, end1, str2, start2, end2 - (leftEnd - start1) - 1);

            if (p1 || p2) {
                return true;
            }
        }

        return false;

    }

    public boolean sameTypeSameNumber(char[] str1, char[] str2) {
        if (str1.length != str2.length) {
            return false;
        }
        int[] map = new int[256];

        for (char c : str1) {
            map[c]++;
        }

        for (char c : str2) {
            if (--map[c] < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 记忆化搜索
     *
     * @since 2022-05-03 21:25:51
     */
    public boolean isScramble1(String s1, String s2) {
        if (s1 == null && s2 == null) {
            return true;
        }

        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }
        if (s1.equals(s2)) {
            return true;
        }

        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();

        if (!sameTypeSameNumber(str1, str2)) {
            return false;
        }

        return process1(str1, 0, str1.length - 1, str2, 0, str2.length - 1, new HashMap<>());
    }

    public boolean process1(char[] str1, int start1, int end1, char[] str2, int start2, int end2, HashMap<String, Boolean> dp) {
        if (dp.get(start1 + "_" + end1 + "_" + start2 + "_" + end2) != null) {
            return dp.get(start1 + "_" + end1 + "_" + start2 + "_" + end2);
        }

        boolean ans = false;

        if (start1 == end1) {
            ans = str1[start1] == str2[start2];
        } else {
            for (int leftEnd = start1; leftEnd < end1; leftEnd++) {
                boolean p1 = process1(str1, start1, leftEnd, str2, start2, start2 + leftEnd - start1, dp) &&
                        process1(str1, leftEnd + 1, end1, str2, start2 + leftEnd - start1 + 1, end2, dp);

                boolean p2 = process1(str1, start1, leftEnd, str2, end2 - (leftEnd - start1), end2, dp) &&
                        process1(str1, leftEnd + 1, end1, str2, start2, end2 - (leftEnd - start1) - 1, dp);

                if (p1 || p2) {
                    ans = true;
                    break;
                }
            }
        }

        dp.put(start1 + "_" + end1 + "_" + start2 + "_" + end2, ans);
        return ans;
    }

    public boolean isScramble2(String s1, String s2) {
        if (s1 == null && s2 == null) {
            return true;
        }

        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }
        if (s1.equals(s2)) {
            return true;
        }

        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();

        if (!sameTypeSameNumber(str1, str2)) {
            return false;
        }

        return process2(str1, str2, 0, 0, str1.length);
    }

    public boolean process2(char[] str1, char[] str2, int start1, int start2, int size) {
        if (size == 1) {
            return str1[start1] == str2[start2];
        }

        for (int leftEnd = start1; leftEnd < start1 + size - 1; leftEnd++) {

            boolean p1 = process2(str1, str2, start1, start2, leftEnd - start1 + 1) &&
                    process2(str1, str2, leftEnd + 1, start2 + (leftEnd - start1 + 1), size - (leftEnd - start1 + 1));

            boolean p2 = process2(str1, str2, start1, (start2 + size - 1) - (leftEnd - start1), (leftEnd - start1 + 1)) &&
                    process2(str1, str2, leftEnd + 1, start2, size - (leftEnd - start1 + 1));

            if (p1 || p2) {
                return true;
            }
        }
        return false;
    }

    public boolean isScramble3(String s1, String s2) {
        if (s1 == null && s2 == null) {
            return true;
        }

        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }
        if (s1.equals(s2)) {
            return true;
        }

        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();

        if (!sameTypeSameNumber(str1, str2)) {
            return false;
        }
        int[][][] dp = new int[str1.length + 1][str1.length + 1][str1.length + 1];
        for (int i = 0; i <= str1.length; i++) {
            for (int j = 0; j <= str1.length; j++) {
                for (int k = 0; k <= str1.length; k++) {
                    dp[i][j][k] = -1;
                }
            }
        }

        return process3(str1, str2, 0, 0, str1.length, dp) == 1;
    }

    public int process3(char[] str1, char[] str2, int start1, int start2, int size, int[][][] dp) {
        if (dp[size][start1][start2] != -1) {
            return dp[size][start1][start2];
        }

        int ans = 0;
        if (size == 1) {
            ans = str1[start1] == str2[start2] ? 1 : 0;
        } else {
            for (int leftEnd = start1; leftEnd < start1 + size - 1; leftEnd++) {

                boolean p1 = process3(str1, str2, start1, start2, leftEnd - start1 + 1, dp) == 1 &&
                        process3(str1, str2, leftEnd + 1, start2 + (leftEnd - start1 + 1), size - (leftEnd - start1 + 1), dp) == 1;

                boolean p2 = process3(str1, str2, start1, (start2 + size - 1) - (leftEnd - start1), (leftEnd - start1 + 1), dp) == 1 &&
                        process3(str1, str2, leftEnd + 1, start2, size - (leftEnd - start1 + 1), dp) == 1;

                if (p1 || p2) {
                    ans = 1;
                    break;
                }
            }
        }
        dp[size][start1][start2] = ans;
        return ans;
    }
}
