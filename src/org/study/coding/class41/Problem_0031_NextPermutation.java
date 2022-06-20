package org.study.coding.class41;

public class Problem_0031_NextPermutation {

	/**
	 * 从右往左遍历
	 * 这个过程中，如果一直升序。毫无疑问，这是最大的排列。直接反转一下，也就是返回最小的排列
	 * 如果遇到了左边小于右边的情况
	 * 第一次遇到的时候。此时这个小的数的左边是不需要动的，排完序后，其左边肯定和当前数组是一样的。
	 * 要考虑的只是当前这个小的数及其右边
	 * 此时这个小的数的左边不动，这个小的数也不动。其右边又是降序。也就意味着这个小的数的潜力已经耗尽的。
	 * 下一个数组就是在右边的数中，找一个刚刚比当前数大的数字来代替这个数
	 * 【也就是在右边的降序数组中，找一个比4大的数来代替4】【如图 img_0031_NextPermutation.png】
	 * 替换掉这个4之后【也就是替换掉这个第一次降序的小的数之后】【替换之后，右边还是降序的】
	 * 再将升序的数进行反转处理【使其从小到大排列】
	 *
	 * @since 2022-06-15 21:21:46
	 */
	public static void nextPermutation(int[] nums) {
		int N = nums.length;
		// 从右往左第一次降序的位置
		int firstLess = -1;
		for (int i = N - 2; i >= 0; i--) {
			if (nums[i] < nums[i + 1]) {
				firstLess = i;
				break;
			}
		}
		if (firstLess < 0) {
			reverse(nums, 0, N - 1);
		} else {
			int rightClosestMore = -1;
			// 找最靠右的、同时比nums[firstLess]大的数，位置在哪
			// 这里其实也可以用二分优化，但是这种优化无关紧要了
			for (int i = N - 1; i > firstLess; i--) {
				if (nums[i] > nums[firstLess]) {
					rightClosestMore = i;
					break;
				}
			}
			swap(nums, firstLess, rightClosestMore);
			reverse(nums, firstLess + 1, N - 1);
		}
	}

	public static void reverse(int[] nums, int L, int R) {
		while (L < R) {
			swap(nums, L++, R--);
		}
	}

	public static void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}

}
