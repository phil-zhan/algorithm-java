package org.study.system.class22;

/**
 * @author phil
 * @date 2021/8/10 18:43
 */
public class MainTest01 {
    public static double right(int N, int M, int K) {
        if (N < 1 || M < 1 || K < 1) {
            return 0;
        }

        double all = Math.pow(M + 1, K);
        double kill = process(K, N, M);

        return kill / all;

    }

    public static double process(int restTimes, int restHp, int M) {
        if (restTimes == 0) {
            return restHp <= 0 ? 1 : 0;
        }
        if (restHp <= 0) {
            return (long) Math.pow(M + 1, restTimes);
        }
        long ways = 0;
        for (int i = 0; i <= M; i++) {
            ways += process(restTimes - 1, restHp - i, M);
        }

        return ways;
    }

    public static double dp1(int N, int M, int K) {
        if (N < 1 || M < 1 || K < 1) {
            return 0;
        }
        long[][] dp = new long[K + 1][N + 1];

        dp[0][0] = 1;

        for (int restTimes = 1; restTimes <= K; restTimes++) {
            for (int restHp = 1; restHp <= N; restHp++) {
                long ways = 0;
                for (int i = 0; i <= M; i++) {
                    ways += (restHp - i) <= 0 ? Math.pow(M + 1, restTimes - 1) : dp[restTimes - 1][restHp - i];
                }

                dp[restTimes][restHp] = ways;
            }
        }
        return dp[K][N] / Math.pow(M + 1, K);
    }

    public static double dp2(int N, int M, int K) {
        if (N < 1 || M < 1 || K < 1) {
            return 0;
        }
        long[][] dp = new long[K + 1][N + 1];
        dp[0][0] = 1;

        for (int restTimes = 1; restTimes <= K; restTimes++) {

            // 保证取当前第一列的前一个位置时，已经算好了
            dp[restTimes][0] = (long) Math.pow(M + 1, restTimes);
            for (int restHp = 1; restHp <= N; restHp++) {
                dp[restTimes][restHp] = dp[restTimes][restHp - 1] + dp[restTimes - 1][restHp];
                dp[restTimes][restHp] -= (restHp - M - 1) <= 0 ? Math.pow(M + 1, restTimes - 1) : dp[restTimes - 1][restHp - M - 1];
            }
        }
        return dp[K][N] / Math.pow(M + 1, K);
    }

    public static void main(String[] args) {
        int NMax = 10;
        int MMax = 10;
        int KMax = 10;
        int testTime = 200;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * NMax);
            int M = (int) (Math.random() * MMax);
            int K = (int) (Math.random() * KMax);
            double ans1 = right(N, M, K);
            double ans2 = dp1(N, M, K);
            double ans3 = dp2(N, M, K);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }
}
