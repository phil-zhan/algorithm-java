package org.study.class25;

import java.util.Stack;

public class Code02_AllTimesMinToMax {


	/**
	 * 暴力解
	 * @date 2021-08-27 10:55:55
	 */
	public static int max1(int[] arr) {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < arr.length; i++) {
			for (int j = i; j < arr.length; j++) {
				int minNum = Integer.MAX_VALUE;
				int sum = 0;
				for (int k = i; k <= j; k++) {
					sum += arr[k];
					minNum = Math.min(minNum, arr[k]);
				}
				max = Math.max(max, minNum * sum);
			}
		}
		return max;
	}

	public static int max2(int[] arr) {
		int size = arr.length;
		int[] sums = new int[size];
		sums[0] = arr[0];

		// 求前缀和
		for (int i = 1; i < size; i++) {
			sums[i] = sums[i - 1] + arr[i];
		}

		// 单调栈求每个数的左右最小值
		// 数组会出现重复值，但是这里没用链表。做了改进
		// 在遇到相等的时候，也让栈顶元素出栈
		// 可能会出现的结果是，在值相同的情况下，前面位置的是算错的，但是最后一个肯定是能算对的。
		// 这不影响结果。因为本题要求的是   以某个数为最小值的左右的一个区间范围的总和 * 当前数  这个指标的最大值
		// 值相同的情况下，前面的数算错，只是区间算小了。最后一个值会把左边的都扩进来，又是求最大值，本题的前提又都是正数，
		// 最后一个数对应的区间是包含了前面数的区间的，区间变大，对应的指标就变大，最后在求所有的最大，前面算错的肯定会被 ko 掉
		int max = Integer.MIN_VALUE;
		Stack<Integer> stack = new Stack<>();
		for (int i = 0; i < size; i++) {
			while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
				int j = stack.pop();
				max = Math.max(max, (stack.isEmpty() ? sums[i - 1] : (sums[i - 1] - sums[stack.peek()])) * arr[j]);
			}
			stack.push(i);
		}
		while (!stack.isEmpty()) {
			int j = stack.pop();
			max = Math.max(max, (stack.isEmpty() ? sums[size - 1] : (sums[size - 1] - sums[stack.peek()])) * arr[j]);
		}
		return max;
	}

	public static int[] gerenareRondomArray() {
		int[] arr = new int[(int) (Math.random() * 20) + 10];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * 101);
		}
		return arr;
	}

	public static void main(String[] args) {
		int testTimes = 2000000;
		System.out.println("test begin");
		for (int i = 0; i < testTimes; i++) {
			int[] arr = gerenareRondomArray();
			if (max1(arr) != max2(arr)) {
				System.out.println("FUCK!");
				break;
			}
		}
		System.out.println("test finish");
	}

}
