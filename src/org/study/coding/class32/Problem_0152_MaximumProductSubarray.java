package org.study.coding.class32;

import java.util.Stack;

/**
 * 152. 乘积最大子数组
 * 给你一个整数数组 nums，请你找出数组中乘积最大的非空连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。
 * 测试用例的答案是一个32-位 整数。
 * 子数组 是数组的连续子序列。
 *
 * 
 *
 * 示例 1:
 * 输入: nums = [2,3,-2,4]
 * 输出: 6
 * 解释:子数组 [2,3] 有最大乘积 6。
 *
 * 示例 2:
 * 输入: nums = [-2,0,-1]
 * 输出: 0
 * 解释:结果不能为 2, 因为 [-2,-1] 不是子数组。
 *
 * 解法：
 * 考虑以某个位置结尾的情况会怎么样。
 * 必须以 i 位置结尾的情况下，往左推多远能求出一个最大值来。
 * 【子数组肯定会以某个值结尾】
 * 1）不往左推
 * 2）往左推【当前位置的值乘以前一个位置能推出来的最大值】
 * 		当前的数乘以前面一个位置能推出来的最大值
 * 		当前的数乘以前面一个位置能推出来的最小值【负负得正】【这是乘法和加法的不一样】
 *
 * 三种情况取最大值
 *
 * 注意：
 * 当前的数可能是一个负数，前一个位置的最小也可能是一个负数。这个时候就需要当前的数乘以前面的最小值才能去到当前位置的最大值
 *
 *
 * 
 * @since 2022-04-17 16:49:57
 */
public class Problem_0152_MaximumProductSubarray {

	
	public static double max(double[] arr) {
		if(arr == null || arr.length == 0) {

			// 报错！
			return 0;
		}
		int n = arr.length;
		// 上一步的最大
		double premax = arr[0];
		// 上一步的最小
		double premin = arr[0];
		double ans = arr[0];
		for(int i = 1; i < n; i++) {
			double p1 = arr[i];
			double p2 = arr[i] * premax;
			double p3 = arr[i] * premin;
			double curmax = Math.max(Math.max(p1, p2), p3);
			double curmin = Math.min(Math.min(p1, p2), p3);
			ans = Math.max(ans, curmax);
			premax = curmax;
			premin = curmin;
		}
		return ans;
	}	
	
	
	
	public static int maxProduct(int[] nums) {
		int ans = nums[0];
		int min = nums[0];
		int max = nums[0];
		for (int i = 1; i < nums.length; i++) {
			int curmin = Math.min(nums[i], Math.min(min * nums[i], max * nums[i]));
			int curmax = Math.max(nums[i], Math.max(min * nums[i], max * nums[i]));
			min = curmin;
			max = curmax;
			ans  = Math.max(ans, max);
		}
		return ans;
	}

}
