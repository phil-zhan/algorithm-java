package org.study.system.class42;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/allocate-mailboxes/
 * @since 2022-02-27 09:56:42
 */
public class Code01_PostOfficeProblem {

	public static int min1(int[] arr, int num) {
		if (arr == null || num < 1 || arr.length < num) {
			return 0;
		}
		int N = arr.length;
		int[][] w = new int[N + 1][N + 1];

		// [L...R]范围  只考虑上半区
		for (int L = 0; L < N; L++) {
			for (int R = L + 1; R < N; R++) {

				// w[L][R] :[L...R]上建一个邮局的最小代价
				// w[L][R - 1] 是之前的邮局代价
				// arr[R] 是当前的居名点

				// 相当于只之前的邮局的最下代价 加上当前位置的最小代价【当前位置的坐标减去当前认为的最佳位置的坐标】
				w[L][R] = w[L][R - 1] + arr[R] - arr[(L + R) >> 1];
			}
		}
		int[][] dp = new int[N][num + 1];

		// 第一列表示只建一个邮局，直接从 w 里面拿值就OK
		for (int i = 0; i < N; i++) {
			dp[i][1] = w[0][i];
		}

		// dp[i][j]  [0...i] 共i+1个居名点
		// j是邮局个数
		// 当邮局个数大于居名点个数的时候。代价是不需要求的。肯定是0
		// num是最大的邮局个数
		for (int i = 1; i < N; i++) {

			// j是邮局个数。不要超过居名点数。也不要超过给定的最大居名点数【也就是列不要越界】
			for (int j = 2; j <= Math.min(i, num); j++) {
				int ans = Integer.MAX_VALUE;

				// [0...k] 有前面的j-1个邮局负责。 k+1 个居名点（也就是剩下的居名点由最后一个邮局负责）
				// 当k=i时。表示所有居名点由前面的j-1个邮局负责。最后一个邮局不负责。这也就是前面 w 表多准备一个位置的原因。
				// w[k + 1][i]. k=i时。 w[k + 1][i]表示的是行大于列。也就是 L大于R。没有意义。在w表里面取到的也就是0【下三角位置】
				for (int k = 0; k <= i; k++) {
					ans = Math.min(ans, dp[k][j - 1] + w[k + 1][i]);
				}
				dp[i][j] = ans;
			}
		}
		return dp[N - 1][num];
	}

	public static int min2(int[] arr, int num) {
		if (arr == null || num < 1 || arr.length < num) {
			return 0;
		}
		int N = arr.length;
		int[][] w = new int[N + 1][N + 1];
		for (int L = 0; L < N; L++) {
			for (int R = L + 1; R < N; R++) {
				w[L][R] = w[L][R - 1] + arr[R] - arr[(L + R) >> 1];
			}
		}
		int[][] dp = new int[N][num + 1];
		int[][] best = new int[N][num + 1];
		for (int i = 0; i < N; i++) {
			dp[i][1] = w[0][i];
			best[i][1] = -1;
		}
		for (int j = 2; j <= num; j++) {
			for (int i = N - 1; i >= j; i--) {
				int down = best[i][j - 1];
				int up = i == N - 1 ? N - 1 : best[i + 1][j];
				int ans = Integer.MAX_VALUE;
				int bestChoose = -1;
				for (int leftEnd = down; leftEnd <= up; leftEnd++) {
					int leftCost = leftEnd == -1 ? 0 : dp[leftEnd][j - 1];
					int rightCost = leftEnd == i ? 0 : w[leftEnd + 1][i];
					int cur = leftCost + rightCost;
					if (cur <= ans) {
						ans = cur;
						bestChoose = leftEnd;
					}
				}
				dp[i][j] = ans;
				best[i][j] = bestChoose;
			}
		}
		return dp[N - 1][num];
	}

	// for test
	public static int[] randomSortedArray(int len, int range) {
		int[] arr = new int[len];
		for (int i = 0; i != len; i++) {
			arr[i] = (int) (Math.random() * range);
		}
		Arrays.sort(arr);
		return arr;
	}

	// for test
	public static void printArray(int[] arr) {
		for (int i = 0; i != arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// for test
	public static void main(String[] args) {
		int N = 300;
		int maxValue = 10000;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * N) + 1;
			int[] arr = randomSortedArray(len, maxValue);
			int num = (int) (Math.random() * N) + 1;
			int ans1 = min1(arr, num);
			int ans2 = min2(arr, num);
			if (ans1 != ans2) {
				printArray(arr);
				System.out.println(num);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println("Oops!");
			}
		}
		System.out.println("测试结束");

	}

}
