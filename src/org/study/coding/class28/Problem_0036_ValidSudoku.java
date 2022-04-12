package org.study.coding.class28;

/**
 * 有效的数独
 * 请你判断一个9 x 9 的数独是否有效。只需要 根据以下规则 ，验证已经填入的数字是否有效即可。
 *
 * 数字1-9在每一行只能出现一次。
 * 数字1-9在每一列只能出现一次。
 * 数字1-9在每一个以粗实线分隔的3x3宫内只能出现一次。（请参考示例图）
 *
 *
 * 注意：
 *
 * 一个有效的数独（部分已被填充）不一定是可解的。
 * 只需要根据以上规则，验证已经填入的数字是否有效即可。
 * 空白格用'.'表示。
 *
 * 解法：
 * 用三个二维数组
 * row[i][j]：第i行，j这个数字有没有出现过
 * col[i][j]：第i列，j这个数字有没有出现过
 * bucket[i][j]：第i个方格，j这个数字有没有出现过
 *
 *
 * @since 2022-04-12 07:48:54
 */
public class Problem_0036_ValidSudoku {

	public static boolean isValidSudoku(char[][] board) {
		boolean[][] row = new boolean[9][10];
		boolean[][] col = new boolean[9][10];
		boolean[][] bucket = new boolean[9][10];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				// 桶号
				int bid = 3 * (i / 3) + (j / 3);
				if (board[i][j] != '.') {
					int num = board[i][j] - '0';

					// 有一个桶违反，直接返回false
					if (row[i][num] || col[j][num] || bucket[bid][num]) {
						return false;
					}
					row[i][num] = true;
					col[j][num] = true;
					bucket[bid][num] = true;
				}
			}
		}
		return true;
	}

}
