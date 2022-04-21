package org.study.coding.class33;

/**
 * 213. 打家劫舍 II
 * 你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都 围成一圈 ，
 * 这意味着第一个房屋和最后一个房屋是紧挨着的。同时，相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警 。
 * 给定一个代表每个房屋存放金额的非负整数数组，计算你 在不触动警报装置的情况下 ，今晚能够偷窃到的最高金额。
 *
 *
 *
 * 示例 1：
 *
 * 输入：nums = [2,3,2]
 * 输出：3
 * 解释：你不能先偷窃 1 号房屋（金额 = 2），然后偷窃 3 号房屋（金额 = 2）, 因为他们是相邻的。
 * 示例 2：
 *
 * 输入：nums = [1,2,3,1]
 * 输出：4
 * 解释：你可以先偷窃 1 号房屋（金额 = 1），然后偷窃 3 号房屋（金额 = 3）。
 *      偷窃到的最高金额 = 1 + 3 = 4 。
 * 示例 3：
 *
 * 输入：nums = [1,2,3]
 * 输出：3
 *
 * 题意：
 * 对于一个数组，在不取相邻数的情况下，子序列的最大累加和是多少。【这是打家劫舍I 的问题】
 * 【本题是在打家劫舍I 的情况下。假设数组是一个环。最大累加和是多少】
 *
 * 打家劫舍I ：
 * 考虑 [0...i]范围上，不能相邻的情况下，最大累加和
 * 1）不要当前位置的数，直接去前一个位置的答案
 * 2）要当前位置的数，往前推两位去取答案【也就是不要去前一位的答案】
 * 3）要当前位置的数，且只要当前位置的数
 * 空间压缩，有限个变量向后滚
 *
 *
 * 打家劫舍II ：
 * 是在打家劫舍I问题的升级版
 * 1）从0开始，推到n-2
 * 2）不选0，推到n-1
 * 两种情况PK一个最大值
 *
 *
 * @since 2022-04-20 21:05:43
 */
public class Problem_0213_HouseRobberII {

	/**
	 * arr 长度大于等于1
	 * 打家劫舍I
	 *
	 *
	 * @since 2022-04-20 21:21:12
	 */
	public static int pickMaxSum(int[] arr) {
		int n = arr.length;
		// dp[i] : arr[0..i]范围上，随意选择，但是，任何两数不能相邻。得到的最大累加和是多少？
		int[] dp = new int[n];
		dp[0] = arr[0];
		dp[1] = Math.max(arr[0], arr[1]);
		for (int i = 2; i < n; i++) {
			int p1 = arr[i];
			int p2 = dp[i - 1];
			int p3 = arr[i] + dp[i - 2];
			dp[i] = Math.max(p1, Math.max(p2, p3));
		}
		return dp[n - 1];
	}

	/**
	 * 打家劫舍II
	 * 是在打家劫舍I问题的升级版
	 * 1）从0开始，推到n-2
	 * 2）不选0，推到n-1
	 * 两种情况PK一个最大值
	 *
	 * 这里用了空间压缩个变量复用
	 *
	 * @since 2022-04-20 21:24:58
	 */
	public static int rob(int[] nums) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		if (nums.length == 1) {
			return nums[0];
		}
		if (nums.length == 2) {
			return Math.max(nums[0], nums[1]);
		}
		int pre2 = nums[0];
		int pre1 = Math.max(nums[0], nums[1]);
		for (int i = 2; i < nums.length - 1; i++) {
			int tmp = Math.max(pre1, nums[i] + pre2);
			pre2 = pre1;
			pre1 = tmp;
		}
		int ans1 = pre1;
		pre2 = nums[1];
		pre1 = Math.max(nums[1], nums[2]);
		for (int i = 3; i < nums.length; i++) {
			int tmp = Math.max(pre1, nums[i] + pre2);
			pre2 = pre1;
			pre1 = tmp;
		}
		int ans2 = pre1;
		return Math.max(ans1, ans2);
	}

}
