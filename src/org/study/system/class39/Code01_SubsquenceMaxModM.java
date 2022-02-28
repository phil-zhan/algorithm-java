package org.study.system.class39;

import java.util.HashSet;
import java.util.TreeSet;

// 给定一个非负数组arr，和一个正数m。 返回arr的所有子序列中累加和%m之后的最大值。
public class Code01_SubsquenceMaxModM {

	/**
	 * 先求出所有可能的累加和情况
	 * 从左到右循环。当前数要或不要
	 *
	 * 所有的累加和对 m 取余，找出最大值【暴力解】
	 * @date 2021-10-15 17:13:59
	 */
	public static int max1(int[] arr, int m) {
		HashSet<Integer> set = new HashSet<>();
		process(arr, 0, 0, set);
		int max = 0;
		for (Integer sum : set) {
			max = Math.max(max, sum % m);
		}
		return max;
	}

	public static void process(int[] arr, int index, int sum, HashSet<Integer> set) {
		if (index == arr.length) {
			set.add(sum);
		} else {
			process(arr, index + 1, sum, set);
			process(arr, index + 1, sum + arr[index], set);
		}
	}


	/**
	 * 	// dp[i][j] :表示原数组中 0到i位置的数任意选，能否加出 j  【】
	 * @date 2021-10-15 16:04:38
	 */
	public static int max2(int[] arr, int m) {
		int sum = 0;
		int N = arr.length;
		for (int k : arr) {
			sum += k;
		}
		boolean[][] dp = new boolean[N][sum + 1];
		for (int i = 0; i < N; i++) {
			dp[i][0] = true;
		}
		dp[0][arr[0]] = true;
		for (int i = 1; i < N; i++) {
			for (int j = 1; j <= sum; j++) {
				dp[i][j] = dp[i - 1][j];
				if (j - arr[i] >= 0) {
					dp[i][j] |= dp[i - 1][j - arr[i]];
				}
			}
		}
		int ans = 0;
		for (int j = 0; j <= sum; j++) {
			if (dp[N - 1][j]) {
				ans = Math.max(ans, j % m);
			}
		}
		return ans;
	}


	/**
	 * dp[i][j] :表示原数组中 0到i位置的数任意选，所加出来的累加和中，模数有没有 j
	 * @date 2021-10-15 15:56:20
	 */
	public static int max3(int[] arr, int m) {
		int N = arr.length;
		// 0...m-1
		boolean[][] dp = new boolean[N][m];
		for (int i = 0; i < N; i++) {
			dp[i][0] = true;
		}
		dp[0][arr[0] % m] = true;
		for (int i = 1; i < N; i++) {
			for (int j = 1; j < m; j++) {
				// dp[i][j] T or F
				dp[i][j] = dp[i - 1][j];
				int cur = arr[i] % m;
				if (cur <= j) {

					// 当前的arr[i]对 m 取余之后，达不到等于 j 的要求，需要前面凑出 j-cur 【能凑出就是true，不能凑出就是false】
					dp[i][j] |= dp[i - 1][j - cur];
				} else {

					// 当前的arr[i]对 m 取余之后，超过了等于 j 的要求，
					// 需要考虑前面的余数 j-cur 和当前的余数  [前面就需要弄出 m + j - cur 个余数出来]
					dp[i][j] |= dp[i - 1][m + j - cur];
				}
			}
		}

		// 最后一行，表示 原数组中 0到 n-1的位置任意选，能否加出 j
		int ans = 0;
		for (int i = 0; i < m; i++) {

			// 右对应模数的情况下，取最大的j列，也就是最大的余数，就是答案
			if (dp[N - 1][i]) {
				ans = i;
			}
		}
		return ans;
	}

	/**
	 * // 如果arr的累加和很大，m也很大
	 * // 但是arr的长度相对不大【也就是arr里面的元素比较大】
	 * // 这时，采用分治思想【将原数组分成左右两半、三半。。。】
	 *
	 * // 假设分成两半。取模的数是 m。这里假设为 8
	 * 有三种情况
	 * 情况1：最大的余数在左侧，找出左侧最大的数
	 * 情况2：最大的余数在右侧，找出右侧最大的数
	 * 情况3：最大的余数在左右两侧的交集【在右侧找出左侧的补数。他们的和就是这种情况的最大值。如：左侧是1，那么右侧找到小于 7，且离7最近的数。 】
	 *
	 * 对三种情况求最大值就是我们要的答案
	 * @date 2021-10-15 16:36:20
	 */
	public static int max4(int[] arr, int m) {
		if (arr.length == 1) {
			return arr[0] % m;
		}
		int mid = (arr.length - 1) / 2;
		TreeSet<Integer> sortSet1 = new TreeSet<>();
		process4(arr, 0, 0, mid, m, sortSet1);
		TreeSet<Integer> sortSet2 = new TreeSet<>();
		process4(arr, mid + 1, 0, arr.length - 1, m, sortSet2);
		int ans = 0;
		for (Integer leftMod : sortSet1) {
			ans = Math.max(ans, leftMod + sortSet2.floor(m - 1 - leftMod));
		}
		return ans;
	}

	// 从index出发，最后有边界是end+1，arr[index...end]
	public static void process4(int[] arr, int index, int sum, int end, int m, TreeSet<Integer> sortSet) {
		if (index == end + 1) {
			sortSet.add(sum % m);
		} else {
			process4(arr, index + 1, sum, end, m, sortSet);
			process4(arr, index + 1, sum + arr[index], end, m, sortSet);
		}
	}

	public static int[] generateRandomArray(int len, int value) {
		int[] ans = new int[(int) (Math.random() * len) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = (int) (Math.random() * value);
		}
		return ans;
	}

	public static void main(String[] args) {
		int len = 10;
		int value = 100;
		int m = 76;
		int testTime = 500000;
		System.out.println("test begin");
		for (int i = 0; i < testTime; i++) {
			int[] arr = generateRandomArray(len, value);
			int ans1 = max1(arr, m);
			int ans2 = max2(arr, m);
			int ans3 = max3(arr, m);
			int ans4 = max4(arr, m);
			if (ans1 != ans2 || ans2 != ans3 || ans3 != ans4) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test finish!");

	}

}
