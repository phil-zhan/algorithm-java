package org.study.coding.class13;

// 本题测试链接 : https://leetcode.com/problems/super-washing-machines/
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
