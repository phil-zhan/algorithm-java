package org.study.coding.class03;

// 本题测试链接 : https://leetcode.com/problems/largest-1-bordered-square/
public class Code03_Largest1BorderedSquare {

	/**
	 * 枚举所有的正方形【任意的一个点加一个边长，就能组成一个正方形】
	 * 矩形范围内，枚举所有的点是 O(N^2)
	 * 枚举所有的边长是 O(N)
	 * 所有枚举所有的正方形是 O(N^3)
	 * 对应每一个正方形，考察其边长上是否都是1
	 * 做出一个辅助结构
	 * 先算出从每个位置`i` 开始，
	 * 往右延长（包含自己），最多能有多少个 1，
	 * 往下延长（包含自己），最多能有多少个 1
	 *
	 * 当枚举到某个正方形时，考察以其左上角点（往有、往下），左下角（往右），右上角（往下），对应的点，在对应的方向上，最多能延展的1是否够边长的长度
	 * 若够，该正方形成立，反之则不成立
	 *
	 * 注意，枚举边时，其往右和往下的最大延伸长度只能取二者的最小值。【因为是正方形】
	 *
	 *
	 * @since 2022-03-02 08:47:37
	 */
	public static int largest1BorderedSquare(int[][] grid) {
		int[][] right = new int[grid.length][grid[0].length];
		int[][] down = new int[grid.length][grid[0].length];
		setBorderMap(grid, right, down);
		for (int size = Math.min(grid.length, grid[0].length); size != 0; size--) {
			if (hasSizeOfBorder(size, right, down)) {
				return size * size;
			}
		}
		return 0;
	}

	public static void setBorderMap(int[][] m, int[][] right, int[][] down) {
		int r = m.length;
		int c = m[0].length;
		if (m[r - 1][c - 1] == 1) {
			right[r - 1][c - 1] = 1;
			down[r - 1][c - 1] = 1;
		}
		for (int i = r - 2; i != -1; i--) {
			if (m[i][c - 1] == 1) {
				right[i][c - 1] = 1;
				down[i][c - 1] = down[i + 1][c - 1] + 1;
			}
		}
		for (int i = c - 2; i != -1; i--) {
			if (m[r - 1][i] == 1) {
				right[r - 1][i] = right[r - 1][i + 1] + 1;
				down[r - 1][i] = 1;
			}
		}
		for (int i = r - 2; i != -1; i--) {
			for (int j = c - 2; j != -1; j--) {
				if (m[i][j] == 1) {
					right[i][j] = right[i][j + 1] + 1;
					down[i][j] = down[i + 1][j] + 1;
				}
			}
		}
	}

	public static boolean hasSizeOfBorder(int size, int[][] right, int[][] down) {
		for (int i = 0; i != right.length - size + 1; i++) {
			for (int j = 0; j != right[0].length - size + 1; j++) {
				if (right[i][j] >= size && down[i][j] >= size && right[i + size - 1][j] >= size
						&& down[i][j + size - 1] >= size) {
					return true;
				}
			}
		}
		return false;
	}

}
