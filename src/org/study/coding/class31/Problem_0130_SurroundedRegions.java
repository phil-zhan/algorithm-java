package org.study.coding.class31;

/**
 * 130. 被围绕的区域
 * 给你一个 m x n 的矩阵 board ，由若干字符 'X' 和 'O' ，找到所有被 'X' 围绕的区域，并将这些区域里所有的 'O' 用 'X' 填充。
 *
 * 解法：
 * 先考虑二维矩阵的四边。将所有 'O' 字符以及与之连城一片的字符，都感染成 'Y' 字符。
 * 然后剩下的没被感染到的 'O' 就是要变成 ‘X’ 的。遍历一遍，将剩下的 'O' 都改成 'X'
 * 最后再将所有的 'Y' 都改成 'O'
 * 时间复杂度 O(N*M)
 *
 * solve2() 更优雅
 *
 *
 * @since 2022-04-17 11:25:36
 */
public class Problem_0130_SurroundedRegions {

//	// m -> 二维数组， 不是0就是1
//	// 实现感染过程【将连城一片的 1 都改成 2】【对于二维数组的话，每个位置都可能是起点。都要去试试】
//	public static void infect(int[][] m, int i, int j) {
//		if (i < 0 || i == m.length || j < 0 || j == m[0].length || m[i][j] != 1) {
//			return;
//		}
//		// m[i][j] == 1
//		m[i][j] = 2;
//		infect(m, i - 1, j);
//		infect(m, i + 1, j);
//		infect(m, i, j - 1);
//		infect(m, i, j + 1);
//	}


	/**
	 * solve2 更优雅。这个就别看了。没意思
	 * @since 2022-04-17 11:42:15
	 */
	public static void solve1(char[][] board) {
		boolean[] ans = new boolean[1];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == 'O') {
					ans[0] = true;
					can(board, i, j, ans);
					board[i][j] = ans[0] ? 'T' : 'F';
				}
			}
		}
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				char can = board[i][j];
				if (can == 'T' || can == 'F') {
					board[i][j] = '.';
					change(board, i, j, can);
				}
			}
		}

	}

	public static void can(char[][] board, int i, int j, boolean[] ans) {
		if (i < 0 || i == board.length || j < 0 || j == board[0].length) {
			ans[0] = false;
			return;
		}
		if (board[i][j] == 'O') {
			board[i][j] = '.';
			can(board, i - 1, j, ans);
			can(board, i + 1, j, ans);
			can(board, i, j - 1, ans);
			can(board, i, j + 1, ans);
		}
	}

	public static void change(char[][] board, int i, int j, char can) {
		if (i < 0 || i == board.length || j < 0 || j == board[0].length) {
			return;
		}
		if (board[i][j] == '.') {
			board[i][j] = can == 'T' ? 'X' : 'O';
			change(board, i - 1, j, can);
			change(board, i + 1, j, can);
			change(board, i, j - 1, can);
			change(board, i, j + 1, can);
		}
	}

	/**
	 * 从边界开始感染的方法，比第一种方法更好
	 *
	 * @since 2022-04-17 11:38:50
	 */
	public static void solve2(char[][] board) {
		if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) {
			return;
		}
		int N = board.length;
		int M = board[0].length;

		// 感染第 0行和第 n-1 行
		for (int j = 0; j < M; j++) {
			if (board[0][j] == 'O') {
				free(board, 0, j);
			}
			if (board[N - 1][j] == 'O') {
				free(board, N - 1, j);
			}
		}

		// 感染第 0列 和第 m-1 列
		for (int i = 1; i < N - 1; i++) {
			if (board[i][0] == 'O') {
				free(board, i, 0);
			}
			if (board[i][M - 1] == 'O') {
				free(board, i, M - 1);
			}
		}

		// 最后的调整。如果还是 'O' 。改成 'X'
		// 最后的调整。如果已经是 'F' 。改成 'O' 【之前被感染了】
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (board[i][j] == 'O') {
					board[i][j] = 'X';
				}
				if (board[i][j] == 'F') {
					board[i][j] = 'O';
				}
			}
		}
	}

	public static void free(char[][] board, int i, int j) {
		if (i < 0 || i == board.length || j < 0 || j == board[0].length || board[i][j] != 'O') {
			return;
		}
		board[i][j] = 'F';
		free(board, i + 1, j);
		free(board, i - 1, j);
		free(board, i, j + 1);
		free(board, i, j - 1);
	}

}
