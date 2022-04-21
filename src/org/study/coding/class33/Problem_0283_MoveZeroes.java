package org.study.coding.class33;

/**
 * 283. 移动零
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 *
 * 请注意 ，必须在不复制数组的情况下原地对数组进行操作。
 *
 *
 *
 * 示例 1:
 *
 * 输入: nums = [0,1,0,3,12]
 * 输出: [1,3,12,0,0]
 * 示例 2:
 *
 * 输入: nums = [0]
 * 输出: [0]
 *
 *  *
 *  * 解法：在左边设置一个有效区。初始化的有效区的右指针在-1位置
 *  * 遍历数组组。如果是0就跳过。不是0就将该数换到有效区的下一个位置。有效区加加
 *
 * @since 2022-04-21 21:06:40
 */
public class Problem_0283_MoveZeroes {

	public static void moveZeroes(int[] nums) {
		int to = 0;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] != 0) {
				swap(nums, to++, i);
			}
		}
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

}
