package org.study.coding.class17;

public class MainTest04 {

    public static void main(String[] args) {
        System.out.println(new MainTest04().numDistinct1("1122", "12"));
        System.out.println(new MainTest04().numDistinct2("1122", "12"));
    }

    public int numDistinct1(String s1, String t1) {
        char[] str1 = s1.toCharArray();
        char[] str2 = t1.toCharArray();

        return process(str1, str2, str1.length, str2.length);
    }

    public int process(char[] str1, char[] str2, int index1, int index2) {
        if (index2 == 0) {
            return 1;
        }

        if (index1 == 0) {
            return 0;
        }

        int res = process(str1, str2, index1 - 1, index2);
        if (str1[index1 - 1] == str2[index2 - 1]) {
            res += process(str1, str2, index1 - 1, index2 - 1);
        }

        return res;
    }

    public int numDistinct2(String s1, String t1) {
        char[] str1 = s1.toCharArray();
        char[] str2 = t1.toCharArray();
        int len1 = str1.length;
        int len2 = str2.length;
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int index1 = 0; index1 <= len1; index1++) {
            dp[index1][0] = 1;
        }

        for (int index1 = 1; index1 <= len1; index1++) {
            for (int index2 = 1; index2 <= len2; index2++) {
                dp[index1][index2] = dp[index1 - 1][index2];
                if (str1[index1 - 1] == str2[index2 - 1]) {
                    dp[index1][index2] += dp[index1 - 1][index2 - 1];
                }
            }
        }

        return dp[len1][len2];
    }
}
