package org.study.coding.class30;

/**
 * 79. 单词搜索
 * 给定一个m x n 二维字符网格board 和一个字符串单词word 。如果word 存在于网格中，返回 true ；否则，返回 false 。
 * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
 *
 * 考虑从b[i][j]出发，能不能搞定word[k....] true false
 * 不要走回头路。来当某个位置的时候，先把当前位置的字符改为 ‘0’
 * 去尝试上下左右能不能搞定剩下的。
 * 尝试完后，将当前位置的字符改回去
 *
 * @since 2022-04-14 21:23:58
 */
public class Problem_0079_WordSearch {

	public static boolean exist(char[][] board, String word) {
		char[] w = word.toCharArray();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (f(board, i, j, w, 0)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 目前到达了b[i][j]，word[k....]
	 * 从b[i][j]出发，能不能搞定word[k....] true false
	 *
	 *
	 * @since 2022-04-14 21:27:41
	 */
	public static boolean f(char[][] b, int i, int j, char[] w, int k) {
		if (k == w.length) {
			return true;
		}
		// word[k.....] 有字符
		// 如果(i,j)越界，返回false
		if (i < 0 || i == b.length || j < 0 || j == b[0].length) {
			return false;
		}
		if (b[i][j] != w[k]) {
			return false;
		}
		char tmp = b[i][j];
		b[i][j] = 0;
		boolean ans = f(b, i - 1, j, w, k + 1) 
				|| f(b, i + 1, j, w, k + 1) 
				|| f(b, i, j - 1, w, k + 1)
				|| f(b, i, j + 1, w, k + 1);
		b[i][j] = tmp;
		return ans;
	}

}
