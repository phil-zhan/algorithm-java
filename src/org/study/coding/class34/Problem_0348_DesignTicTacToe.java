package org.study.coding.class34;

/**
 * 这是一个付费题
 * 在一个n*n 的棋盘上。
 * 两个人下棋（五子棋）
 * A在棋盘上画圈
 * B在棋盘上画叉
 *
 * A只要能在棋盘上某行、某列或某对角线上，全部画上圈，则A就赢了
 * B只要能在棋盘上某行、某列或某对角线上，全部画上叉，则B就赢了
 *
 * 游戏规则。
 * 写一个函数
 * move(row,col,who)  // 当前在某行、某列，由谁落子（画圈或画叉）。
 * 返回0，表示下了这一步之后，没有人赢。
 * 返回1：表示下完这一步之后，A赢了。
 * 返回2：表示下完这一步之后，B赢了。
 *
 * so easy
 *
 * 在构造方法里面，会输入一个n。表示棋盘的大小
 * 建立一张表
 * rows[][] : rows[i][j] 【第i行，j号小人收集了几个旗子】
 * cols[][] : rows[i][j] 【第i列，j号小人收集了几个旗子】
 *
 * leftUp[] : leftUp[j] 左对角线上。j号小人收集了几个旗子
 * rightUp[] : rightUp[j] 右对角线上。j号小人收集了几个旗子
 *
 * @since 2022-04-23 15:40:53
 */
public class Problem_0348_DesignTicTacToe {

	class TicTacToe {
		private int[][] rows;
		private int[][] cols;
		private int[] leftUp;
		private int[] rightUp;
		private boolean[][] matrix;
		private int N;

		public TicTacToe(int n) {
			// rows[a][1] : 1这个人，在a行上，下了几个
			// rows[b][2] : 2这个人，在b行上，下了几个
			rows = new int[n][3]; //0 1 2
			cols = new int[n][3];


			// leftUp[2] = 7 : 2这个人，在左对角线上，下了7个
			leftUp = new int[3];
			// rightUp[1] = 9 : 1这个人，在右对角线上，下了9个
			rightUp = new int[3];


			// 记录 i、j 位置是否被落过子了
			matrix = new boolean[n][n];


			N = n;
		}

		public int move(int row, int col, int player) {

			// 该位置被下过了
			if (matrix[row][col]) {
				return 0;
			}
			matrix[row][col] = true;

			// 对应的位置，对应的玩家收集旗子
			rows[row][player]++;
			cols[col][player]++;

			// 是否落在左对角线
			if (row == col) {
				leftUp[player]++;
			}

			// 是否落在右对角线
			if (row + col == N - 1) {
				rightUp[player]++;
			}

			// 当前玩家，不管是行、列、还是左右对角线。收集满n个。他就赢了
			if (rows[row][player] == N || cols[col][player] == N || leftUp[player] == N || rightUp[player] == N) {
				return player;
			}
			return 0;
		}

	}

}
