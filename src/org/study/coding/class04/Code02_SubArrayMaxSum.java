package org.study.coding.class04;

// 本题测试链接 : https://leetcode.com/problems/maximum-subarray/
public class Code02_SubArrayMaxSum {

	/**
	 * 考虑以 index 位置结尾，最多能往左推多远
	 * index位置，有两种选择
	 * 要么往左推	（当前位置的数，加上 前面一个数结尾的子数组的最大累加和）
	 * 要么不往左推
	 * 两种情况抓一个最大值
	 * @since 2022-03-03 10:27:29
	 */
	public static int maxSubArray(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int max = Integer.MIN_VALUE;
		int cur = 0;
		for (int j : arr) {
			cur += j;
			max = Math.max(max, cur);
			cur = Math.max(cur, 0);
		}
		return max;
	}

	public static int maxSubArray2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		// 上一步，dp的值
		// dp[0]
		int pre = arr[0];
		int max = arr[0];
		for (int i = 1; i < arr.length; i++) {
			pre = Math.max(arr[i], arr[i] + pre);
			max =  Math.max(max, pre);
		}
		return max;
	}

}
