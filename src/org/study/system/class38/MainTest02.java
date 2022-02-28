package org.study.system.class38;

/**
 * @author phil
 * @date 2021/10/15 15:13
 */
public class MainTest02 {

    public static int func1(int[] d, int[] p) {
        return process1(d, p, 0, 0);
    }

    public static int process1(int[] d, int[] p, int ability, int index) {

        if (index == d.length) {
            return 0;
        }
        if (ability < d[index]) {
            return p[index] + process1(d, p, ability + d[index], index + 1);
        } else {
            return Math.min(
                    p[index] + process1(d, p, ability + d[index], index + 1),
                    process1(d, p, ability, index + 1)
            );
        }
    }

    public static long func2(int[] d, int[] p) {
        int sum = 0;
        for (int j : d) {
            sum += j;
        }

        long[][] dp = new long[d.length + 1][sum + 1];

        for (int i = 0; i <= sum; i++) {
            dp[0][i] = 0;
        }


        for (int cur = d.length - 1; cur >= 0; cur--) {


            for (int hp = 0; hp <= sum; hp++) {

                if (hp + d[cur] > sum) {
                    continue;
                }
                if (hp < d[cur]) {
                    dp[cur][hp] = p[cur] + dp[cur + 1][hp + d[cur]];
                } else {
                    dp[cur][hp] = Math.min(p[cur] + dp[cur + 1][hp + d[cur]], dp[cur + 1][hp]);
                }
            }
        }

        return dp[0][0];
    }


    public static int[][] generateTwoRandomArray(int len, int value) {
        int size = (int) (Math.random() * len) + 1;
        int[][] arrs = new int[2][size];
        for (int i = 0; i < size; i++) {
            arrs[0][i] = (int) (Math.random() * value) + 1;
            arrs[1][i] = (int) (Math.random() * value) + 1;
        }
        return arrs;
    }

    public static void main(String[] args) {
        int len = 10;
        int value = 20;
        int testTimes = 10000;
        for (int i = 0; i < testTimes; i++) {
            int[][] arrs = generateTwoRandomArray(len, value);
            int[] d = arrs[0];
            int[] p = arrs[1];
            long ans1 = func1(d, p);
            long ans2 = func2(d, p);
            long ans3 = Code04_MoneyProblem.func3(d, p);
            long ans4 = Code04_MoneyProblem.minMoney2(d, p);
            if (ans1 != ans2 || ans2 != ans3 || ans1 != ans4) {
                System.out.println("oops!");
            }
        }

    }
}
