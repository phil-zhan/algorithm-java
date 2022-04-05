package org.study.coding.class24;

import java.util.ArrayList;
import java.util.List;

/**
 * 4. N * M的棋盘（N和M是输入参数），每种颜色的格子数必须相同的，上下左右的格子算相邻，相邻格子染的颜色必须不同，所有格子必须染色，返回至少多少种颜色可以完成任务
 * <p>
 * 解法：
 * 就是写暴力方法，观察规律，然后求出来解。
 * <p>
 * 暴力解：
 * 去试1种颜色能不能完成
 * 去试2种颜色能不能完成
 * 去试3种颜色能不能完成
 * ...
 * 去试x种颜色能不能完成
 * <p>
 * 试的时候。[i][j] 位置填[1...x]中的一种颜色。然后递归去试下一个位置。纯暴力
 * <p>
 * 试完之后，发现答案。n和m相乘之后，最小的质数因子就是答案。
 *
 * @since 2022-04-05 21:00:09
 */
public class Code04_Painting {
    // N * M的棋盘
    // 每种颜色的格子数必须相同的
    // 相邻格子染的颜色必须不同
    // 所有格子必须染色
    // 返回至少多少种颜色可以完成任务

    public static int minColors(int N, int M) {

        // 颜色数量是i
        for (int i = 2; i < N * M; i++) {

            int[][] matrix = new int[N][M];
            // 下面这一句可知，需要的最少颜色数i，一定是N*M的某个因子
            // (N * M) % i == 0  【要求每种颜色的格子数量一样多】【i一定是N*M的一个因子】
            if ((N * M) % i == 0 && can(matrix, N, M, i)) {
                return i;
            }
        }
        return N * M;
    }

    // 在matrix上染色，返回只用pNum种颜色是否可以做到要求
    public static boolean can(int[][] matrix, int N, int M, int pNum) {
        int all = N * M;
        int every = all / pNum;
        ArrayList<Integer> rest = new ArrayList<>();
        rest.add(0);
        for (int i = 1; i <= pNum; i++) {
            rest.add(every);
        }
        return process(matrix, N, M, pNum, 0, 0, rest);
    }

    public static boolean process(int[][] matrix, int N, int M, int pNum, int row, int col, List<Integer> rest) {
        if (row == N) {
            return true;
        }
        if (col == M) {
            return process(matrix, N, M, pNum, row + 1, 0, rest);
        }
        int left = col == 0 ? 0 : matrix[row][col - 1];
        int up = row == 0 ? 0 : matrix[row - 1][col];
        for (int color = 1; color <= pNum; color++) {
            if (color != left && color != up && rest.get(color) > 0) {
                int count = rest.get(color);
                rest.set(color, count - 1);
                matrix[row][col] = color;
                if (process(matrix, N, M, pNum, row, col + 1, rest)) {
                    return true;
                }
                rest.set(color, count);
                matrix[row][col] = 0;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        // 根据代码16行的提示，打印出答案，看看是答案是哪个因子
        for (int N = 2; N < 10; N++) {
            for (int M = 2; M < 10; M++) {
                System.out.println("N   = " + N);
                System.out.println("M   = " + M);
                System.out.println("ans = " + minColors(N, M));
                System.out.println("===========");
            }
        }
        // 打印答案，分析可知，是N*M最小的质数因子，原因不明，也不重要
        // 反正打表法猜出来了
    }

}
