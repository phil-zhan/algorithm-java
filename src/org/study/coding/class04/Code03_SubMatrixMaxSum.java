package org.study.coding.class04;

public class Code03_SubMatrixMaxSum {

	/**
	 * 每次都考虑 i行到j行的最大值
	 * i行到i行
	 * i行到i+1行
	 * i行到i+2行
	 * i行到i+3行
	 * 。。。。
	 * 只有一行时，直接按照单个数组球最大累加和方法来求
	 * 多行时，求用从上到下累加的方式弄出一个一维数组。再按照一维数组求最大累加和的方式来做
	 *
	 * @since 2022-03-03 10:24:53
	 */
	public static int maxSum(int[][] m) {
		if (m == null || m.length == 0 || m[0].length == 0) {
			return 0;
		}
		// O(N^2 * M)
		int N = m.length;
		int M = m[0].length;
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < N; i++) {
			// i~j
			int[] s = new int[M];

			for (int j = i; j < N; j++) {

				for (int k = 0; k < M; k++) {
					s[k] += m[j][k];
				}
				max = Math.max(max, maxSubArray(s));
			}
		}
		return max;
	}
	
	public static int maxSubArray(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int max = Integer.MIN_VALUE;
		int cur = 0;
		for (int i = 0; i < arr.length; i++) {
			cur += arr[i];
			max = Math.max(max, cur);
			cur = cur < 0 ? 0 : cur;
		}
		return max;
	}

	// 本题测试链接 : https://leetcode-cn.com/problems/max-submatrix-lcci/
	public static int[] getMaxMatrix(int[][] m) {
		int N = m.length;
		int M = m[0].length;
		int max = Integer.MIN_VALUE;
		int cur = 0;
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;
		for (int i = 0; i < N; i++) {
			int[] s = new int[M];
			for (int j = i; j < N; j++) {
				cur = 0;
				int begin = 0;
				for (int k = 0; k < M; k++) {
					s[k] += m[j][k];
					cur += s[k];
					if (max < cur) {
						max = cur;
						a = i;
						b = begin;
						c = j;
						d = k;
					}
					if (cur < 0) {
						cur = 0;
						begin = k + 1;
					}
				}
			}
		}
		return new int[] { a, b, c, d };
	}

}
