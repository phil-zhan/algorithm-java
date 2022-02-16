package org.study.class25;

/**
 * 测试链接：https://leetcode.com/problems/count-submatrices-with-all-ones
 * 讨论以每行为底的子矩阵（内部全是1）
 * 最后统计个数
 *
 */
public class Code05_CountSubmatricesWithAllOnes {

	public static int numSubmat(int[][] mat) {
		if (mat == null || mat.length == 0 || mat[0].length == 0) {
			return 0;
		}
		int nums = 0;
		int[] height = new int[mat[0].length];
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				height[j] = mat[i][j] == 0 ? 0 : height[j] + 1;
			}
			nums += countFromBottom(height);
		}
		return nums;

	}

	public static int countFromBottom(int[] height) {
		if (height == null || height.length == 0) {
			return 0;
		}
		int nums = 0;

		//数组模拟栈结构
		int[] stack = new int[height.length];
		int si = -1;
		for (int i = 0; i < height.length; i++) {
			while (si != -1 && height[stack[si]] >= height[i]) {
				int cur = stack[si--];

				// 相等的情况下不要计算（直接把之前的出栈就好），等到最后一个出现的时候，一起算
				// 基于单调栈的原理。两个相同的数相遇时，他们中间的数肯定都是大于他们的，
				// 如果是小于的话，与到小于数的时候，就会将前面一个数弹出去
				if (height[cur] > height[i]) {

					int left = si == -1 ? -1 : stack[si];

					// 以当前高度为高的矩形的宽度
					int n = i - left - 1;

					// 左边到不了的高度和右边到不了的高度  取一个最大值
					int down = Math.max(left == -1 ? 0 : height[left], height[i]);

					// 计算数量。是一个等差数列。注意差值之间的高度（当前高度和左右两边到不了的位置的高度）
					nums += (height[cur] - down) * num(n);
				}

			}
			// 当前位置压栈
			stack[++si] = i;
		}

		// 右边没数了，清理剩余的栈
		while (si != -1) {
			int cur = stack[si--];
			int left = si == -1  ? -1 : stack[si];
			int n = height.length - left - 1;
			int down = left == -1 ? 0 : height[left];
			nums += (height[cur] - down) * num(n);
		}
		return nums;
	}

	public static int num(int n) {
		return ((n * (1 + n)) >> 1);
	}

}
