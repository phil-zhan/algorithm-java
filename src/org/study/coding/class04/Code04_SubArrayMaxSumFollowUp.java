package org.study.coding.class04;

public class Code04_SubArrayMaxSumFollowUp {

	/**
	 * <a href="https://leetcode-cn.com/problems/house-robber/">https://leetcode-cn.com/problems/house-robber/</a>
	 *
	 * 考虑 [0...i]范围上，不能相邻的情况下，最大累加和
	 *
	 * 1）不要当前位置的数，直接去前一个位置的答案
	 * 2）要当前位置的数，往前推两位去取答案【也就是不要去前一位的答案】
	 * 3）要当前位置的数，且只要当前位置的数
	 *
	 * 空间压缩，有限个变量向后滚
	 *
	 * @since 2022-03-03 10:51:06
	 */
	public static int subSqeMaxSumNoNext(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		if (arr.length == 1) {
			return arr[0];
		}
		int[] dp = new int[arr.length];
		// dp[i] : arr[0..i]挑选，满足不相邻设定的情况下，随意挑选，最大的累加和
		dp[0] = arr[0];
		dp[1] = Math.max(arr[0], arr[1]);
		for (int i = 2; i < arr.length; i++) {
			int p1 = dp[i - 1];
			int p2 = arr[i] + Math.max(dp[i - 2], 0);
			dp[i] = Math.max(p1, p2);
		}
		return dp[arr.length - 1];
	}

	// 给定一个数组arr，在不能取相邻数的情况下，返回所有组合中的最大累加和
	// 思路：
	// 定义dp[i] : 表示arr[0...i]范围上，在不能取相邻数的情况下，返回所有组合中的最大累加和
	// 在arr[0...i]范围上，在不能取相邻数的情况下，得到的最大累加和，可能性分类：
	// 可能性 1) 选出的组合，不包含arr[i]。那么dp[i] = dp[i-1]
	// 比如，arr[0...i] = {3,4,-4}，最大累加和是不包含i位置数的时候
	//
	// 可能性 2) 选出的组合，只包含arr[i]。那么dp[i] = arr[i]
	// 比如，arr[0...i] = {-3,-4,4}，最大累加和是只包含i位置数的时候
	//
	// 可能性 3) 选出的组合，包含arr[i], 且包含arr[0...i-2]范围上的累加和。那么dp[i] = arr[i] + dp[i-2]
	// 比如，arr[0...i] = {3,1,4}，最大累加和是3和4组成的7，因为相邻不能选，所以i-1位置的数要跳过
	//
	// 综上所述：dp[i] = Max { dp[i-1], arr[i] , arr[i] + dp[i-2] }
	public static int maxSum(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;
		if (N == 1) {
			return arr[0];
		}
		if (N == 2) {
			return Math.max(arr[0], arr[1]);
		}
		int[] dp = new int[N];
		dp[0] = arr[0];
		dp[1] = Math.max(arr[0], arr[1]);
		for (int i = 2; i < N; i++) {

			// 三种可能性，抓最大值
			dp[i] = Math.max(Math.max(dp[i - 1], arr[i]), arr[i] + dp[i - 2]);
		}
		return dp[N - 1];
	}

}
