package org.study.coding.class23;

/**
 * 2. 给定一个数组arr，长度为N > 1，从中间切一刀，保证左部分和右部分都有数字，一共有N-1种切法
 *    如此多的切法中，每一种都有:绝对值(左部分最大值 – 右部分最大值)，返回最大的绝对值是多少
 *
 * 暴力解：O(N^2)
 * 枚举每一个切分位置。在每一个位置都去遍历左右两边，拿到最大值。O(N^2) . 面试的时候没分
 *
 * 半暴力：O(N)
 * 利用辅助数组
 * 设计一个辅助数组left
 * left[i] : 表示原数组0...i 范围上的最大值。
 * left[i] = max(left[i-1],arr[i])
 * 同样可以设计一个right数组
 * right[i] : 表示原数组 i...n-1 的最大值
 * 再去枚举切分位置，这样左、右的最大值就都不用去遍历了。直接从辅助数组拿值
 *
 * 最优解：
 * 遍历一遍，找到全局最大值。
 * 然后0位置的数和n-1位置的数，谁小减谁。就是最终答案。
 * 因为要保证，不管怎么切分。左右两个数组都要有数。
 * 1）：max可能被划分进了左边
 *  那么max减去右边部分的最大值。怎么可能尽量大。
 *  这样一来，就是考虑这一刀怎么划，能让右边部分的最大值尽可能的小。
 *  最右边的那个数肯定是在右边数组中的
 *  就直接让右部分只剩下一个数的时候。因为右边部分的最大值肯定是大于等于最右边的那个数的。
 *
 * 2）：max可能被划分进了右边
 *  那么max减去左边部分的最大值。怎么可能尽量大。
 *  这样一来，就是考虑这一刀怎么划，能让左边部分的最大值尽可能的小。
 *  最左边的那个数肯定是在左边数组中的
 *  就直接让左部分只剩下一个数的时候。因为左边部分的最大值肯定是大于等于最左边的那个数的。
 *
 *
 * @author Phil
 * @since 2022-03-28 08:01:59
 */
public class Code02_MaxABSBetweenLeftAndRight {

	/**
	 * 暴力解
	 * @since 2022-03-28 20:42:16
	 */
	public static int maxABS1(int[] arr) {
		int res = Integer.MIN_VALUE;
		int maxLeft = 0;
		int maxRight = 0;
		for (int i = 0; i != arr.length - 1; i++) {
			maxLeft = Integer.MIN_VALUE;
			for (int j = 0; j != i + 1; j++) {
				maxLeft = Math.max(arr[j], maxLeft);
			}
			maxRight = Integer.MIN_VALUE;
			for (int j = i + 1; j != arr.length; j++) {
				maxRight = Math.max(arr[j], maxRight);
			}
			res = Math.max(Math.abs(maxLeft - maxRight), res);
		}
		return res;
	}

	/**
	 * 半暴力解
	 * @since 2022-03-28 20:42:28
	 */
	public static int maxABS2(int[] arr) {
		int[] lArr = new int[arr.length];
		int[] rArr = new int[arr.length];
		lArr[0] = arr[0];
		rArr[arr.length - 1] = arr[arr.length - 1];
		for (int i = 1; i < arr.length; i++) {
			lArr[i] = Math.max(lArr[i - 1], arr[i]);
		}
		for (int i = arr.length - 2; i > -1; i--) {
			rArr[i] = Math.max(rArr[i + 1], arr[i]);
		}
		int max = 0;
		for (int i = 0; i < arr.length - 1; i++) {
			max = Math.max(max, Math.abs(lArr[i] - rArr[i + 1]));
		}
		return max;
	}

	/**
	 * 最优解
	 * @since 2022-03-28 20:42:40
	 */
	public static int maxABS3(int[] arr) {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < arr.length; i++) {
			max = Math.max(arr[i], max);
		}
		return max - Math.min(arr[0], arr[arr.length - 1]);
	}

	public static int[] generateRandomArray(int length) {
		int[] arr = new int[length];
		for (int i = 0; i != arr.length; i++) {
			arr[i] = (int) (Math.random() * 1000) - 499;
		}
		return arr;
	}

	public static void main(String[] args) {
		int[] arr = generateRandomArray(200);
		System.out.println(maxABS1(arr));
		System.out.println(maxABS2(arr));
		System.out.println(maxABS3(arr));
	}
}
