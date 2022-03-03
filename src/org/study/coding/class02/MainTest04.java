package org.study.coding.class02;

/**
 * @author phil
 * @since 2022-0303 21:13:41
 */
public class MainTest04 {

    public int maxMoney1(int[][] income) {
        if (income == null || income.length < 2 || (income.length & 1) != 0) {
            return 0;
        }

        return process(income, 0, income.length >> 1);
    }

    public int process(int[][] income, int index, int rest) {
        if (index == income.length) {
            return 0;
        }

        if (rest == 0) {
            // 剩下的全去 B
            return income[index][1] + process(income, index + 1, rest);
        }

        if (income.length - index == rest) {
            // 剩下的全去 A
            return income[index][0] + process(income, index + 1, rest - 1);
        }

        // 可A可B
        int p1 = income[index][0] + process(income, index + 1, rest - 1);
        int p2 = income[index][1] + process(income, index + 1, rest);

        return Math.max(p1, p2);
    }

    public int maxMoney2(int[][] income) {
        int len = income.length;
        int[][] dp = new int[len + 1][(len >> 1) + 1];


        for (int index = len - 1; index >= 0; index--) {
            for (int rest = 0; rest <= (len >> 1); rest++) {

                if (rest == 0) {
                    // 剩下的全去 B
                    dp[index][rest] = income[index][1] + dp[index + 1][rest];
                }

                if (income.length - index == rest) {
                    // 剩下的全去 A
                    dp[index][rest] = income[index][0] + dp[index + 1][rest - 1];
                }

                // 可A可B
                int p1 = income[index][0] + process(income, index + 1, rest - 1);
                int p2 = income[index][1] + process(income, index + 1, rest);

                dp[index][rest] = Math.max(p1, p2);
            }
        }

        return dp[0][len >> 1];
    }


    public static void main(String[] args) {
        int N = 10;
        int value = 100;
        int testTime = 500;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N) + 1;
            int[][] matrix = Code04_Drive.randomMatrix(len, value);
            int ans1 = new MainTest04().maxMoney1(matrix);
            int ans2 = new MainTest04().maxMoney2(matrix);
            int ans3 = Code04_Drive.maxMoney3(matrix);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }
}
