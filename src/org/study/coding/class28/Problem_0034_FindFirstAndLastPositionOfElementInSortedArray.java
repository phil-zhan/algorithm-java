package org.study.coding.class28;

/**
 * 在排序数组中查找元素的第一个和最后一个位置
 *
 * 解法：有序数组中进行二分
 *
 *
 * @since 2022-04-12 07:25:44
 */
public class Problem_0034_FindFirstAndLastPositionOfElementInSortedArray {

	public static int[] searchRange(int[] nums, int target) {
		if (nums == null || nums.length == 0) {
			return new int[] { -1, -1 };
		}
		int L = lessMostRight(nums, target) + 1;
		if (L == nums.length || nums[L] != target) {
			return new int[] { -1, -1 };
		}
		return new int[] { L, lessMostRight(nums, target + 1) };
	}

	public static int lessMostRight(int[] arr, int num) {
		int L = 0;
		int R = arr.length - 1;
		int M = 0;
		int ans = -1;
		while (L <= R) {
			M = L + ((R - L) >> 1);
			if (arr[M] < num) {
				ans = M;
				L = M + 1;
			} else {
				R = M - 1;
			}
		}
		return ans;
	}

}
