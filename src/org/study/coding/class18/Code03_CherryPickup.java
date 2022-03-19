package org.study.coding.class18;


import java.util.Scanner;

/**
 * // 牛客的测试链接：
 * // https://www.nowcoder.com/questionTerminal/8ecfe02124674e908b2aae65aad4efdf
 * // 把如下的全部代码拷贝进java编辑器
 * // 把文件大类名字改成Main，可以直接通过
 * <p>
 * 给定一个矩阵matrix，先从左上角开始，每一步只能往右或者往下走，走到右下角。
 * 然后从右下角出发，每一步只能往上或者往左走，再回到左上角。任何一个位置的数字，只能获得一遍。
 * 返回最大路径和。
 * <p>
 * 问题可以转化为。A、B两个人一起走。每一次都只迈出一步，可以有一样的选择，也可以有不同的选择。
 * 当A、B所在的位置重合时，只获得一份收益。否则，收益是二者之和
 *
 * @since 2022-03-19 08:15:11
 */
public class Code03_CherryPickup {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int[][] matrix = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }
        int ans = cherryPickup(matrix);
        System.out.println(ans);
        sc.close();
    }

    public static int cherryPickup(int[][] grid) {
        int N = grid.length;
        int M = grid[0].length;
        int[][][] dp = new int[N][M][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                for (int k = 0; k < N; k++) {
                    dp[i][j][k] = Integer.MIN_VALUE;
                }
            }
        }
        int ans = process(grid, 0, 0, 0, dp);
        return Math.max(ans, 0);
    }

    /**
     * A从(x1,y1)开始，走到右下角
     * B从(x2,y2)开始，走到右下角
     * 共同走到右下角
     * <p>
     * 能获得的最大收益是多大。当掉在同一个格子的时候，只能获得一份收益。否则，获得的收益是二者之和
     * <p>
     * 对于同一个位置，如果A来过，B也来过。那么他们肯定是同步到的。不可能是谁先谁后。
     * 因为是同步走的。都只能向右或向下。从左上角到当前这个位置。步数是固定的
     * <p>
     * A来到(x1,y1)
     * B来到(x2,y2)
     * 有一个条件 x1+y1 = x2+y2 {因为是同时走的}
     * 就可以省掉一个参数
     *
     * @since 2022-03-19 08:28:30
     */
    public static int process(int[][] grid, int x1, int y1, int x2, int[][][] dp) {
        if (x1 == grid.length || y1 == grid[0].length || x2 == grid.length || x1 + y1 - x2 == grid[0].length) {

            // 越界了
            return Integer.MIN_VALUE;
        }

        if (dp[x1][y1][x2] != Integer.MIN_VALUE) {
            // 之前算过了

            return dp[x1][y1][x2];
        }
        if (x1 == grid.length - 1 && y1 == grid[0].length - 1) {
            // 来到了终点

            dp[x1][y1][x2] = grid[x1][y1];
            return dp[x1][y1][x2];
        }

        // A下B下
        // A下B右
        // A上B下
        // A下B右

        // 看看哪种选择可以推高答案
        int next = Integer.MIN_VALUE;
        next = Math.max(next, process(grid, x1 + 1, y1, x2 + 1, dp));
        next = Math.max(next, process(grid, x1 + 1, y1, x2, dp));
        next = Math.max(next, process(grid, x1, y1 + 1, x2, dp));
        next = Math.max(next, process(grid, x1, y1 + 1, x2 + 1, dp));

//        if (grid[x1][y1] == -1 || grid[x2][x1 + y1 - x2] == -1 || next == -1) {
//            // 当前选择无效
//
//            dp[x1][y1][x2] = -1;
//            return dp[x1][y1][x2];
//        }
        if (x1 == x2) {
            // 掉在了同一个位置

            dp[x1][y1][x2] = grid[x1][y1] + next;
            return dp[x1][y1][x2];
        }
        // 加缓存
        dp[x1][y1][x2] = grid[x1][y1] + grid[x2][x1 + y1 - x2] + next;
        return dp[x1][y1][x2];
    }

}