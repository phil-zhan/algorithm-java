package org.study.coding.class36;

public class MainTest10 {

    public static void main(String[] args) {
        System.out.println(new MainTest10().winnerSquareGame1(16));
        System.out.println(new MainTest10().winnerSquareGame2(16));
        System.out.println(new MainTest10().winnerSquareGame3(16));
    }


    public boolean winnerSquareGame1(int n) {
        if (n == 0) {
            return false;
        }

        for (int i = 1; i * i <= n; i++) {
            if (!winnerSquareGame1(n - i * i)) {
                return true;
            }
        }
        return false;
    }

    public boolean winnerSquareGame2(int n) {

        return process(n, new int[n + 1]);
    }

    public boolean process(int n, int[] dp) {
        if (dp[n] != 0) {
            return dp[n] == 1;
        }
        if (n == 0) {
            dp[n] = -1;
        }

        for (int i = 1; i * i <= n; i++) {
            if (!process(n - i * i, dp)) {
                dp[n] = 1;
                break;
            }
        }
        return dp[n] == 1;
    }

    public boolean winnerSquareGame3(int n) {
        boolean[] dp = new boolean[n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j * j <= i; j++) {
                if (!dp[i - j * j]) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[n];
    }
}
