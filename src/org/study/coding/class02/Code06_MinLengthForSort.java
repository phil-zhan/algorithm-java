package org.study.coding.class02;

// 本题测试链接 : https://leetcode.com/problems/shortest-unsorted-continuous-subarray/
public class Code06_MinLengthForSort {

	/**
	 * 从左往右遍历
	 * 准备一个max指针，表示当前位置左边的最大值的位置
	 * 如果左边位置的最大值大于当前数 就将当前位置标记为 `×`     【违反了单调递增规律】
	 * 如果左边位置的最大值小于等于当前数 就将当前位置标记为 `√`  【满足单调递增规律】
	 *
	 * 从右往左遍历
	 * 准备一个min指针，表示当前位置右边的最小值的位置
	 * 如果右边位置的最小值大于当前数 就将当前位置标记为 `√`     【满足单调递减规律】
	 * 如果右边位置的最小值小于等于当前数 就将当前位置标记为 `×`  【违反了单调递减规律】
	 *
	 *
	 * 从右往左的最后个 `×` 的位置 和 从左往右的最后 `×` 的位置。  中间就是需要排序的最短长度
	 *
	 * 从左往右。最后一个画 × 的，表示其后面的数都不需要给前面的数让位置。从右往左也是一样的
	 * 确定了右边不需要排序的位置，也确定了左边不需要排序的位置。中间就是需要排序的
	 *
	 * @since 2022-03-01 11:11:51
	 */
	public static int findUnsortedSubarray(int[] nums) {
		if (nums == null || nums.length < 2) {
			return 0;
		}
		int N = nums.length;
		int right = -1;
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < N; i++) {
			if (max > nums[i]) {
				right = i;
			}
			max = Math.max(max, nums[i]);
		}
		int min = Integer.MAX_VALUE;
		int left = N;
		for (int i = N - 1; i >= 0; i--) {
			if (min < nums[i]) {
				left = i;
			}
			min = Math.min(min, nums[i]);
		}
		return Math.max(0, right - left + 1);
	}

}
