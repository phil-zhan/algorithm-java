package org.study.coding.class32;

/**
 * 189. 轮转数组
 * 给你一个数组，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
 *
 *
 * 示例 1:
 * 输入: nums = [1,2,3,4,5,6,7], k = 3
 * 输出: [5,6,7,1,2,3,4]
 * 解释:
 * 向右轮转 1 步: [7,1,2,3,4,5,6]
 * 向右轮转 2 步: [6,7,1,2,3,4,5]
 * 向右轮转 3 步: [5,6,7,1,2,3,4]
 *
 *
 * 示例 2:
 * 输入：nums = [-1,-100,3,99], k = 2
 * 输出：[3,99,-1,-100]
 * 解释:
 * 向右轮转 1 步: [99,-1,-100,3]
 * 向右轮转 2 步: [3,99,-1,-100]
 *
 * 解法：
 * 也就是旋转
 * 在L...R范围上考虑逆序【原地逆序】【L和 R 交换】【交换之后L++。R--.直到L和 R相遇】
 *
 * 左边自己逆序，右边也自己逆序
 * 最后再整体逆序
 * 搞定【不用管为什么】
 *
 *
 *
 * @since 2022-04-19 07:43:32
 */
public class Problem_0189_RotateArray {

	/**
	 * 数组的左部分k个数
	 * 在不适用额外的辅助空间的情况下。如何让左部分彻底到右边去。右部分彻底到左边来。左右部分内部的相对次序还不要变化
	 * 也就是旋转
	 *
	 * @since 2022-04-19 07:45:51
	 */
	public void rotate1(int[] nums, int k) {
		int N = nums.length;
		k = k % N;
		reverse(nums, 0, N - k - 1);
		reverse(nums, N - k, N - 1);
		reverse(nums, 0, N - 1);
	}

	public static void reverse(int[] nums, int L, int R) {
		while (L < R) {
			int tmp = nums[L];
			nums[L++] = nums[R];
			nums[R--] = tmp;
		}
	}

	public static void rotate2(int[] nums, int k) {
		int N = nums.length;
		k = k % N;
		if (k == 0) {
			return;
		}
		int L = 0;
		int R = N - 1;
		int lpart = N - k;
		int rpart = k;
		int same = Math.min(lpart, rpart);
		int diff = lpart - rpart;
		exchange(nums, L, R, same);
		while (diff != 0) {
			if (diff > 0) {
				L += same;
				lpart = diff;
			} else {
				R -= same;
				rpart = -diff;
			}
			same = Math.min(lpart, rpart);
			diff = lpart - rpart;
			exchange(nums, L, R, same);
		}
	}

	public static void exchange(int[] nums, int start, int end, int size) {
		int i = end - size + 1;
		int tmp = 0;
		while (size-- != 0) {
			tmp = nums[start];
			nums[start] = nums[i];
			nums[i] = tmp;
			start++;
			i++;
		}
	}

}
