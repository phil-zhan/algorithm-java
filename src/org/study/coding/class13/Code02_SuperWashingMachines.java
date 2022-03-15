package org.study.coding.class13;

/**
 * 本题测试链接 : https://leetcode.com/problems/super-washing-machines/
 *
 * 假设有 n台超级洗衣机放在同一排上。开始的时候，每台洗衣机内可能有一定量的衣服，也可能是空的
 * 在每一步操作中，你可以选择任意 m（1 ≤ m ≤ n）台洗衣机，与此同时将每台洗衣机的一件衣服送到相邻的一台洗衣机
 * 给定一个非负整数数组代表从左至右每台洗衣机中的衣物数量，请给出能让所有洗衣机中剩下的衣物的数量相等的最少的操作步数
 * 如果不能使每台洗衣机中衣物的数量相等，则返回-1
 * machines[i] : 表示第 i 台洗衣机中的衣服数量
 *
 * 超级洗衣机问题
 *
 *
 *
 * @since 2022-03-14 10:46:40
 */
public class Code02_SuperWashingMachines {

	public static int findMinMoves(int[] machines) {
		if (machines == null || machines.length == 0) {
			return 0;
		}
		int size = machines.length;
		int sum = 0;
		for (int i = 0; i < size; i++) {
			sum += machines[i];
		}

		// 要求要平均，搞不定
		if (sum % size != 0) {
			return -1;
		}
		int avg = sum / size;
		int leftSum = 0;
		int ans = 0;
		for (int i = 0; i < machines.length; i++) {
			int leftRest = leftSum - i * avg;
			int rightRest = (sum - leftSum - machines[i]) - (size - i - 1) * avg;
			if (leftRest < 0 && rightRest < 0) {
				ans = Math.max(ans, Math.abs(leftRest) + Math.abs(rightRest));
			} else {
				ans = Math.max(ans, Math.max(Math.abs(leftRest), Math.abs(rightRest)));
			}
			leftSum += machines[i];
		}
		return ans;
	}

}
