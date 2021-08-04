package org.study.class19;

/**
 * @author phil
 * @date 2021/7/30 15:26
 */
public class MainTest04 {

    public static int longestCommonSubsequence1(String s1, String s2) {
        if (null == s1 || null == s2 || s1.length() == 0 || s2.length() == 0) {
            return 0;
        }

        char[] chr1 = s1.toCharArray();
        char[] chr2 = s2.toCharArray();

        return process1(chr1, chr2, chr1.length - 1, chr2.length - 1);
    }

    private static int process1(char[] chr1, char[] chr2, int i, int j) {
        if (i == 0 && j == 0) {
            return chr1[i] == chr2[j] ? 1 : 0;
        } else if (i == 0) {
            if(chr1[i] == chr2[j]){
                return 1;
            }else{
                return process1(chr1,chr2,i,j-1);
            }
        } else if (j == 0) {
            if(chr1[i] == chr2[j]){
                return 1;
            }else{
                return process1(chr1,chr2,i-1,j);
            }
        }else{
            // 考虑该子序列以 i 结尾
            int p1 = process1(chr1, chr2, i - 1, j);

            // 考虑该子序列以 j 结尾
            int p2 = process1(chr1, chr2, i, j - 1);

            // 绝对以 i 也同时以 j 结尾
            int p3 = chr1[i] == chr2[j] ? (1 + process1(chr1, chr2, i - 1, j - 1)) : 0;
            return Math.max(p1, Math.max(p2, p3));
        }
    }

    public static int longestCommonSubsequence2(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int N = str1.length;
        int M = str2.length;
        int[][] dp = new int[N][M];
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;
        for (int j = 1; j < M; j++) {
            dp[0][j] = str1[0] == str2[j] ? 1 : dp[0][j - 1];
        }
        for (int i = 1; i < N; i++) {
            dp[i][0] = str1[i] == str2[0] ? 1 : dp[i - 1][0];
        }
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < M; j++) {
                int p1 = dp[i - 1][j];
                int p2 = dp[i][j - 1];
                int p3 = str1[i] == str2[j] ? (1 + dp[i - 1][j - 1]) : 0;
                dp[i][j] = Math.max(p1, Math.max(p2, p3));
            }
        }
        return dp[N - 1][M - 1];
    }
}
