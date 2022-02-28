package org.study.system.class20;

/**
 * @author phil
 * @date 2021/8/5 16:00
 */
public class MainTest02 {

    /**
     * 棋盘：10 * 9
     * a:纵向的y轴 10
     * b:横向的x轴 9
     *
     * @date 2021-08-05 16:12:07
     */
    public static int jump(int a, int b, int k) {
        if (a > 9 || b > 8 || a < 0 || b < 0) {
            // 目标不在棋盘上
            return 0;
        }


        return process(0, 0, a, b, k);
    }

    private static int process(int curX, int curY, int targetX, int targetY, int rest) {
        if (curX > 9 || curY > 8 || curX < 0 || curY < 0) {
            return 0;
        }
        if (rest == 0) {
            return (curX == targetX && curY == targetY) ? 1 : 0;
        }
        int ways = process(curX + 1, curY - 2, targetX, targetY, rest - 1);
        ways += process(curX + 2, curY - 1, targetX, targetY, rest - 1);

        ways += process(curX + 2, curY + 1, targetX, targetY, rest - 1);
        ways += process(curX + 1, curY + 2, targetX, targetY, rest - 1);

        ways += process(curX - 1, curY + 2, targetX, targetY, rest - 1);
        ways += process(curX - 2, curY + 1, targetX, targetY, rest - 1);

        ways += process(curX - 2, curY - 1, targetX, targetY, rest - 1);
        ways += process(curX - 1, curY - 2, targetX, targetY, rest - 1);

        return ways;
    }

    public static void main(String[] args) {
        int x = 7;
        int y = 7;
        int step = 10;
        /*System.out.println(ways(x, y, step));*/
        System.out.println(dp(x, y, step));

        System.out.println(jump(x, y, step));
    }

    public static int dp(int a, int b, int k) {
        if (a > 9 || b > 8 || a < 0 || b < 0) {
            // 目标不在棋盘上
            return 0;
        }


        int[][][] dp = new int[10][9][k + 1];
        dp[a][b][0] = 1;

        for (int rest = 1; rest <= k; rest++) {
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 9; y++) {

                    int ways = pick(dp, x + 1, y - 2, rest - 1);
                    ways += pick(dp, x + 2, y - 1, rest - 1);

                    ways += pick(dp, x + 2, y + 1, rest - 1);
                    ways += pick(dp, x + 1, y + 2, rest - 1);

                    ways += pick(dp, x - 1, y + 2, rest - 1);
                    ways += pick(dp, x - 2, y + 1, rest - 1);

                    ways += pick(dp, x - 2, y - 1, rest - 1);
                    ways += pick(dp, x - 1, y - 2, rest - 1);

                    dp[x][y][rest] = ways;
                }
            }
        }

        return dp[0][0][k];
    }

    private static int pick(int[][][] dp, int x, int y, int rest) {

        if (x > 9 || y > 8 || x < 0 || y < 0) {
            return 0;
        }

        return dp[x][y][rest];
    }
}
