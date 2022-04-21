package org.study.coding.class33;

/**
 * 这是一个收费题
 *
 * 题意：
 * 就是给一个二维数组。做出一个二维数组的迭代器.这个二维数组可能有长有短。不一定是矩阵
 *
 * 需要实现三个方法
 * 1）构造函数。入参数 int[][] 二维数组。返回对应的迭代器
 * 2）boolean hasNext()
 * 3) int next()
 *
 * 本题的难点是多次调用hasNext(). 游标是不能往下走的
 * 另外就是hasNext() 和 next() 的配合问题
 *
 *
 * @since 2022-04-20 22:20:06
 */
public class Problem_0251_Flatten2DVector {

	public static class Vector2D {

		// 原始数组
		private int[][] matrix;

		// 当前的指针停在 row行 col列
		private int row;
		private int col;

		// row行 col列 这个元素是否被使用过
		// 如果当前元素被使用过，那么指针就往下走。row不动，col++ 。如果col没有了。就row++。  col归0
		private boolean curUse;

		public Vector2D(int[][] v) {
			matrix = v;

			// 初始化指针在 0行0列的前面
			row = 0;
			col = -1;


			curUse = true;
			hasNext();
		}

		/**
		 * next的时候，直接返回当前游标所致的位置的数
		 * 然后调一下hasNext，让游标下移
		 *
		 * @since 2022-04-20 23:13:50
		 */
		public int next() {
			int ans = matrix[row][col];
			curUse = true;
			hasNext();
			return ans;
		}

		/**
		 * 第一次调hasNext(),会将游标下移。第二次或多次调的时候，就不会移了【直到当前数被next使用之前都不会再移动游标了。会直接返回true】
		 * @since 2022-04-20 23:14:34
		 */
		public boolean hasNext() {

			// 没有数了。【总共只有row-1行】
			if (row == matrix.length) {
				return false;
			}

			// 在next的时候，会将对应的数改成true。这里就进不去
			// 第一次调用hasNext的时候，会将当前游标所指的位置改成false。返回调用的时候，就会将进入这里面，直接返回true。游标就不会往下走了
			if (!curUse) {
				return true;
			}

			// (row，col)用过了
			// 当前行的col没有用完，col++
			if (col < matrix[row].length - 1) {
				col++;
			} else {

				// 到了最后一列。row++
				// 可能会是下一行没数，或者下一行越界
				col = 0;
				do {
					row++;
				} while (row < matrix.length && matrix[row].length == 0);
			}

			// row 没越界。要将当前的数标位未使用，再返回。【方便被多次调hasNext】
			// 新的(row，col)
			if (row != matrix.length) {
				curUse = false;
				return true;
			} else {

				// 越界了。直接返回false
				return false;
			}
		}

	}

}
