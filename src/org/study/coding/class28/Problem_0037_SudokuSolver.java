package org.study.coding.class28;

/**
 * 解数独
 * 编写一个程序，通过填充空格来解决数独问题。
 *
 * 数独的解法需 遵循如下规则：
 *
 * 数字1-9在每一行只能出现一次。
 * 数字1-9在每一列只能出现一次。
 * 数字1-9在每一个以粗实线分隔的3x3宫内只能出现一次。（请参考示例图）
 * 数独部分空格内已填入了数字，空白格用'.'表示。
 *
 * 题意：
 * 题目数据 保证 输入数独仅有一个解
 *
 * 解法：
 * 用三个二维数组
 * row[i][j]：第i行，j这个数字有没有出现过
 * col[i][j]：第i列，j这个数字有没有出现过
 * bucket[i][j]：第i个方格，j这个数字有没有出现过
 *
 * 先生成这三个辅助数组
 * 遍历二维数组
 * 来到一个位置，该位置有数字就跳过
 * 没有数字就去尝试
 * 可以尝试1...9
 * 看看当前位置是否冲突，当前位置不冲突，且后续也都不冲突，那么该位置的数字有效
 * 否则就去尝试下一个数字
 *
 *
 * @since 2022-04-12 07:52:33
 */
public class Problem_0037_SudokuSolver {

	public static void solveSudoku(char[][] board) {
		boolean[][] row = new boolean[9][10];
		boolean[][] col = new boolean[9][10];
		boolean[][] bucket = new boolean[9][10];

		// 初始化三个辅助数组
		initMaps(board, row, col, bucket);

		process(board, 0, 0, row, col, bucket);
	}

	public static void initMaps(char[][] board, boolean[][] row, boolean[][] col, boolean[][] bucket) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int bid = 3 * (i / 3) + (j / 3);
				if (board[i][j] != '.') {
					int num = board[i][j] - '0';
					row[i][num] = true;
					col[j][num] = true;
					bucket[bid][num] = true;
				}
			}
		}
	}

	/**
	 * 当前来到(i,j)这个位置，如果已经有数字，跳到下一个位置上
	 * 如果没有数字，尝试1~9，不能和row、col、bucket冲突
	 * @since 2022-04-12 08:08:47
	 */
	public static boolean process(char[][] board, int i, int j, boolean[][] row, boolean[][] col, boolean[][] bucket) {
		if (i == 9) {
			return true;
		}
		// 当离开(i，j)，应该去哪？(nexti, nextj)
		int nextI = j != 8 ? i : i + 1;
		int nextJ = j != 8 ? j + 1 : 0;
		if (board[i][j] != '.') {
			// 当前的位置有数，直接去下一个位置

			return process(board, nextI, nextJ, row, col, bucket);
		} else {
			// 可以尝试1~9
			int bid = 3 * (i / 3) + (j / 3);

			// 尝试每一个数字1~9
			for (int num = 1; num <= 9; num++) {
				if ((!row[i][num]) && (!col[j][num]) && (!bucket[bid][num])) {
					// 可以尝试num
					row[i][num] = true;
					col[j][num] = true;
					bucket[bid][num] = true;
					board[i][j] = (char) (num + '0');

					// 后续不冲突，就返回true
					if (process(board, nextI, nextJ, row, col, bucket)) {
						return true;
					}

					// 后续有冲突，要尝试下一个数字，这里需要恢复现场

					row[i][num] = false;
					col[j][num] = false;
					bucket[bid][num] = false;
					board[i][j] = '.';
				}
			}
			return false;
		}
	}

}
