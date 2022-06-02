package org.study.coding.class37;

/**
 * 在一个0和1组成的二维矩阵中。找到全是1的正方形。返回最大正方形的边长
 *
 * 解法：
 * 动态规划：
 * 考虑以每个位置 [i,j] 为正方形的右下角，能推出多大的正方形。最后在所有的正方形中，抓一个最大的正方形就行
 *
 *
 * 来到一个位置 [i,j] .如果要该位置推出一个 4*4 的正方形。
 * 那么其左上角的位置 [i-1,j-1] 至少要有一个 3*3 的正方形
 * 那么其左边的位置 [i,j-1] 至少要有一个 3*3 的正方形
 * 那么其上边的位置 [i-1,j] 至少要有一个 3*3 的正方形
 * 【因为除了边上的1.其他都是重合部分】
 *
 * 最后， [i,j] 位置也必须是 1
 *
 * @since 2022-05-30 23:46:41
 */
public class Problem_0221_MaximalSquare {

	public static int maximalSquare(char[][] m) {
		if (m == null || m.length == 0 || m[0].length == 0) {
			return 0;
		}
		int N = m.length;
		int M = m[0].length;
		int[][] dp = new int[N + 1][M + 1];
		int max = 0;
		for (int i = 0; i < N; i++) {
			if (m[i][0] == '1') {
				dp[i][0] = 1;
				max = 1;
			}
		}
		for (int j = 1; j < M; j++) {
			if (m[0][j] == '1') {
				dp[0][j] = 1;
				max = 1;
			}
		}
		for (int i = 1; i < N; i++) {
			for (int j = 1; j < M; j++) {
				if (m[i][j] == '1') {

					// 三个位置的正方形，取最小的，再加1 就本当前位置的答案【左上、左边、上边】
					// 见图 img_Problem_0221.png
					// 也可以自己画画。就是三个位置，取瓶颈，再加1
					dp[i][j] = Math.min(
							Math.min(dp[i - 1][j],
									dp[i][j - 1]), 
							dp[i - 1][j - 1]) 
							+ 1;

					max = Math.max(max, dp[i][j]);
				}
			}
		}
		return max * max;
	}

}
